package ru.praktikum_sevices.qa_scooter.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_sevices.qa_scooter.model.courier.Courier;
import ru.praktikum_sevices.qa_scooter.model.courier.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseRestClient{

    /**
     * Класс "клиент" курьера. Содержит методы создания, удаления, логина курьера в системе
     */

    private static final String COURIER_PATH = "api/v1/courier/";


    @Step("Авторизация курьера в системе {credentials}")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpecRequest())
                .log().all()
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .log().all();
    }

    @Step("Создание курьера с параметрами {courier}")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpecRequest())
                .log().all()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .log().all();
    }

    @Step("Удаление курьера с id {courierId}")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpecRequest())
                .log().all()
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .log().all();
    }
    
}
