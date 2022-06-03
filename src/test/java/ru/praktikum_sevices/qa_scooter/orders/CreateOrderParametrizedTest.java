package ru.praktikum_sevices.qa_scooter.orders;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_sevices.qa_scooter.clients.CourierClient;
import ru.praktikum_sevices.qa_scooter.clients.OrdersClient;
import ru.praktikum_sevices.qa_scooter.helpers.CourierGenerator;
import ru.praktikum_sevices.qa_scooter.model.courier.Courier;
import ru.praktikum_sevices.qa_scooter.model.courier.CourierCredentials;
import ru.praktikum_sevices.qa_scooter.model.courier.CreateCourierResponse;
import ru.praktikum_sevices.qa_scooter.model.orders.CreateOrderResponse;
import ru.praktikum_sevices.qa_scooter.model.orders.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {

    OrdersClient ordersClient;
    Order order = new Order();
    private final List<String> listColors;

    public CreateOrderParametrizedTest(List<String> listColors) {
        this.listColors = listColors;
    }
    @Parameterized.Parameters
    public static Object[][] getSumData() {
        return new Object[][] {
                {List.of()},
                {List.of("BLACK")},
                {List.of("GRAY")},
                {List.of("BLACK", "GRAY")}
        };
    }

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
        // получаем дефолтный набор полей для создания заказа
        order = order.getCreateOrderDefault();
    }

    @Test
    @Description("После успешного создания заказа тело ответа содержит 'track' не зависимо от выбранного цвета")
    public void shouldBeTrackInResponseBody() {
        order.setColor(listColors);
        ValidatableResponse orderCreateResponse = ordersClient.createOrder(order);
        CreateOrderResponse createOrderResponse = orderCreateResponse.extract().as(CreateOrderResponse.class);

        assertThat("Статус код не 201", orderCreateResponse.extract().statusCode(), equalTo(SC_CREATED));
        assertThat("Тело ответа не содержит валидный 'track'", createOrderResponse.getTrack(), is(not(0)));
    }
}
