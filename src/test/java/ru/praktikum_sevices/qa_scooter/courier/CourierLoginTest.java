package ru.praktikum_sevices.qa_scooter.courier;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_sevices.qa_scooter.clients.CourierClient;
import ru.praktikum_sevices.qa_scooter.helpers.CourierGenerator;
import ru.praktikum_sevices.qa_scooter.model.courier.Courier;
import ru.praktikum_sevices.qa_scooter.model.courier.CourierCredentials;
import ru.praktikum_sevices.qa_scooter.model.ErrorResponse;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static ru.praktikum_sevices.qa_scooter.helpers.CourierGenerator.reverseString;

public class CourierLoginTest {

    CourierClient courierClient;
    Courier courier;
    int courierId;
    public static final String LOGIN_WITHOUT_LOGIN_OR_PASS = "Недостаточно данных для входа";
    public static final String LOGIN_WITH_WRONG_LOGIN_OR_PASS = "Учетная запись не найдена";

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        // Если courierId == 0, т.е. не было успешного логина. Нужно залогиниться для получения id для удаления курьера
        try {
            if (courierId == 0) {
                ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
                courierId = loginResponse.extract().path("id");
                courierClient.delete(courierId);
            }
        } catch (NullPointerException e) {
            System.out.println("Курьер не был создан или что-то случилось.");
        }
    }

    @Test
    @Description("Успешная авторизация курьера с валидными логином и паролем")
    public void shouldBeSuccessLoginWithValidCredentials() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.extract().path("id");

        assertThat("Статус код не 200", loginResponse.extract().statusCode(), equalTo(SC_OK));
        assertThat("ИД курьера не валидный", courierId, is(not(0)));
    }

    @Test
    @Description("Нельзя авторизоваться без логина")
    public void shouldNotBeLoginWithoutLogin() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(null, courier.getPassword()));
        ErrorResponse errorResponse = loginResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 400", loginResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(LOGIN_WITHOUT_LOGIN_OR_PASS));
    }

    @Test
    @Description("Нельзя авторизоваться с пустым логином")
    public void shouldNotBeLoginWitEmptyLogin() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("", courier.getPassword()));
        ErrorResponse errorResponse = loginResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 400", loginResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(LOGIN_WITHOUT_LOGIN_OR_PASS));
    }

//    @Test
//    @Description("Нельзя авторизоваться без пароля")
//    public void shouldNotBeLoginWithoutPassword() {
//        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), null));
//
//        assertThat("Статус код не 400", loginResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
//        assertThat("Ожидаемый текст не соответствует фактическому", loginResponse.extract().response().body().path("message"), equalTo(LOGIN_WITHOUT_LOGIN_OR_PASS));
//    }

    @Test
    @Description("Нельзя авторизоваться с пустым паролем")
    public void shouldNotBeLoginWithEmptyPassword() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), ""));

        assertThat("Статус код не 400", loginResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", loginResponse.extract().response().body().path("message"), equalTo(LOGIN_WITHOUT_LOGIN_OR_PASS));
    }

//    @Test
//    @Description("Нельзя авторизоваться без логина и пароля")
//    public void shouldNotBeLoginWithoutLoginAndPassword() {
//        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(null, null));
//
//        assertThat("Статус код не 400", loginResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
//        assertThat("Ожидаемый текст не соответствует фактическому", loginResponse.extract().response().body().path("message"), equalTo(LOGIN_WITHOUT_LOGIN_OR_PASS));
//    }

    @Test
    @Description("Нельзя авторизоваться c пустыми логином и паролем")
    public void shouldNotBeLoginWithEmptyLoginAndPassword() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("", ""));

        assertThat("Статус код не 400", loginResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", loginResponse.extract().response().body().path("message"), equalTo(LOGIN_WITHOUT_LOGIN_OR_PASS));
    }

    @Test
    @Description("Нельзя авторизоваться с неправильным логином")
    public void shouldNotBeLoginWithWrongLogin() {
        // Для получения неправильного логина конкатенируем исходный логин с его отзеркаленной версией
        String wrongLogin = reverseString(courier.getLogin(), courier.getLogin().length() - 1);
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin() + wrongLogin, courier.getPassword()));
        ErrorResponse errorResponse = loginResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 404", loginResponse.extract().statusCode(), equalTo(SC_NOT_FOUND));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(LOGIN_WITH_WRONG_LOGIN_OR_PASS));
    }

    @Test
    @Description("Нельзя авторизоваться с неправильным паролем")
    public void shouldNotBeLoginWithWrongPassword() {
        // Для получения неправильного пароля конкатенируем исходный пароль с его отзеркаленной версией
        String wrongPassword = reverseString(courier.getLogin(), courier.getLogin().length() - 1);
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword() + wrongPassword));
        ErrorResponse errorResponse = loginResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 404", loginResponse.extract().statusCode(), equalTo(SC_NOT_FOUND));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(LOGIN_WITH_WRONG_LOGIN_OR_PASS));
    }

    @Test
    @Description("Нельзя авторизоваться под несуществующим пользователем")
    public void shouldNotBeLoginNonExistentCourier() {
        String wrongLogin = reverseString(courier.getLogin(), courier.getLogin().length() - 1);
        String wrongPassword = reverseString(courier.getLogin(), courier.getLogin().length() - 1);
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin() + wrongLogin, courier.getPassword() + wrongPassword));
        ErrorResponse errorResponse = loginResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 404", loginResponse.extract().statusCode(), equalTo(SC_NOT_FOUND));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(LOGIN_WITH_WRONG_LOGIN_OR_PASS));
    }
}
