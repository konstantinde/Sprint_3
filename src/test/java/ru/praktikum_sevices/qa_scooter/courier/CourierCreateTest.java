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
import ru.praktikum_sevices.qa_scooter.model.courier.CreateCourierResponse;
import ru.praktikum_sevices.qa_scooter.model.ErrorResponse;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CourierCreateTest {

    CourierClient courierClient;
    Courier courier;
    int courierId;
    public static final String NO_DATA_FOR_CREATE = "Недостаточно данных для создания учетной записи";
    public static final String THIS_LOGIN_IS_USED = "Этот логин уже используется";

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    @After
    public void tearDown() {
        // удаляем пользователя, только в тех тестах, где он был создан и есть логин и пароль для логина в системе
        try {
            if (courier.getLogin() != null && courier.getPassword() != null) {
                ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(),
                        courier.getPassword()));
                courierId = loginResponse.extract().path("id");
                courierClient.delete(courierId);
            }
        } catch (NullPointerException e) {
            System.out.println("Курьер не был создан или что-то случилось.");
        }
    }

    @Test
    @Description("Успешное создание курьера с валидными данными - логин, пароль, имя")
    public void shouldBeCreateCourierWithValidData() {
        ValidatableResponse createResponse = courierClient.create(courier);
        CreateCourierResponse createCourierResponse = createResponse.extract().as(CreateCourierResponse.class);

        assertThat("Статус код не 201", createResponse.extract().statusCode(), equalTo(SC_CREATED));
        assertTrue("Значение 'ok' в ответе не true", createCourierResponse.getOk());
    }

    @Test
    @Description("Нельзя создать курьера только с одним логином")
    public void shouldNotBeCreateCourierWithOnlyLogin() {
        courier.setPassword(null);
        courier.setFirstName(null);

        ValidatableResponse createResponse = courierClient.create(courier);
        ErrorResponse errorResponse = createResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 400", createResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(NO_DATA_FOR_CREATE));
    }

    @Test
    @Description("Нельзя создать курьера только с одним паролем")
    public void shouldNotBeCreateCourierWithOnlyPassword() {
        courier.setLogin(null);
        courier.setFirstName(null);

        ValidatableResponse createResponse = courierClient.create(courier);
        ErrorResponse errorResponse = createResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 400", createResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(NO_DATA_FOR_CREATE));
    }

    @Test
    @Description("Нельзя создать курьера только с одним именем")
    public void shouldNotBeCreateCourierWithOnlyFirstName() {
        courier.setLogin(null);
        courier.setPassword(null);

        ValidatableResponse createResponse = courierClient.create(courier);
        ErrorResponse errorResponse = createResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 400", createResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(NO_DATA_FOR_CREATE));
    }

    @Test
    @Description("Нельзя создать курьера без логина (только с паролем и именем)")
    public void shouldNotBeCreateCourierWithoutLogin() {
        courier.setLogin(null);

        ValidatableResponse createResponse = courierClient.create(courier);
        ErrorResponse errorResponse = createResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 400", createResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(NO_DATA_FOR_CREATE));
    }

    @Test
    @Description("Нельзя создать курьера без пароля (только с логином и именем)")
    public void shouldNotBeCreateCourierWithoutPassword() {
        courier.setPassword(null);

        ValidatableResponse createResponse = courierClient.create(courier);
        ErrorResponse errorResponse = createResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 400", createResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(NO_DATA_FOR_CREATE));
    }

    @Test
    @Description("Нельзя создать курьера без имени (только с логином и паролем)")
    public void shouldNotBeCreateCourierWithoutFirstName() {
        courier.setFirstName(null);

        ValidatableResponse createResponse = courierClient.create(courier);
        ErrorResponse errorResponse = createResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 400", createResponse.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(NO_DATA_FOR_CREATE));
    }

    @Test
    @Description("Нельзя создать двух одинаковых курьеров")
    public void shouldNotBeCreateDoubleCourier() {
        // Создаем курьера
        courierClient.create(courier);

        // Создаем еще одного такого же курьера
        ValidatableResponse createResponse = courierClient.create(courier);
        ErrorResponse errorResponse = createResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 409", createResponse.extract().statusCode(), equalTo(SC_CONFLICT));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(THIS_LOGIN_IS_USED));
    }

    @Test
    @Description("Нельзя создать курьера с логином, который уже есть в системе")
    public void shouldNotBeCreateCourierIfLoginIsAlready() {
        // Создаем первого курьера
        courierClient.create(courier);

        // Готовим объект второго курьера и присваиваем ему логин первого
        Courier courier2 = CourierGenerator.getRandomCourier();
        courier2.setLogin(courier.getLogin());

        // Создаем еще одного курьера с уже существующим логином, но другими остальными параметрами
        ValidatableResponse createResponse = courierClient.create(courier2);
        ErrorResponse errorResponse = createResponse.extract().as(ErrorResponse.class);

        assertThat("Статус код не 409", createResponse.extract().statusCode(), equalTo(SC_CONFLICT));
        assertThat("Ожидаемый текст не соответствует фактическому", errorResponse.getMessage(), equalTo(THIS_LOGIN_IS_USED));
    }
}
