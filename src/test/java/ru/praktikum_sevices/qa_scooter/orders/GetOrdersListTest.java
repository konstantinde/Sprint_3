package ru.praktikum_sevices.qa_scooter.orders;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_sevices.qa_scooter.clients.CourierClient;
import ru.praktikum_sevices.qa_scooter.clients.OrdersClient;
import ru.praktikum_sevices.qa_scooter.helpers.CourierGenerator;
import ru.praktikum_sevices.qa_scooter.model.courier.Courier;
import ru.praktikum_sevices.qa_scooter.model.courier.CourierCredentials;
import ru.praktikum_sevices.qa_scooter.model.orders.CreateOrderResponse;
import ru.praktikum_sevices.qa_scooter.model.orders.Orders;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersListTest {
    OrdersClient ordersClient;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @Description("В ответе есть список заказов")
    public void shouldBeReturnOrdersListInResponseBody() {
        ValidatableResponse getOrdersListResponse = ordersClient.getOrdersList();
        Orders orders = getOrdersListResponse.extract().as(Orders.class);

        assertThat("Статус код не 200", getOrdersListResponse.extract().statusCode(), equalTo(SC_OK));
        assertThat("В ответе нет списка заказов", orders.getOrders(), is(notNullValue()));
    }
}
