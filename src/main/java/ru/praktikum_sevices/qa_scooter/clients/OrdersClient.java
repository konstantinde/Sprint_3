package ru.praktikum_sevices.qa_scooter.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_sevices.qa_scooter.model.courier.Courier;
import ru.praktikum_sevices.qa_scooter.model.courier.CourierCredentials;
import ru.praktikum_sevices.qa_scooter.model.orders.CreateOrderResponse;
import ru.praktikum_sevices.qa_scooter.model.orders.Order;

import static io.restassured.RestAssured.given;

public class OrdersClient extends BaseRestClient{

    /**
     * Класс "клиент" заказов. Содержит методы создания, отмены, получения списка заказов
     */

    private static final String ORDERS_PATH = "/api/v1/orders/";


    @Step("Создание заказа с параметрами {order}")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseSpecRequest())
                .log().all()
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then()
                .log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList() {
        return given()
                .spec(getBaseSpecRequest())
                .log().all()
                .when()
                .get(ORDERS_PATH)
                .then()
                .log().all();
    }
    
}
