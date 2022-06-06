package ru.praktikum_sevices.qa_scooter.clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.config.RequestConfig;

import static io.restassured.http.ContentType.ANY;
import static io.restassured.http.ContentType.JSON;

public class BaseRestClient {
    /**
     * Базовый класс для работы "клиентов". Содержит эндпоинты и спецификации для запросов/ответов.
     */

    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    protected RequestSpecification getBaseSpecRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setContentType(JSON)
                .setAccept(ANY)
                .setBaseUri(BASE_URL)
                .build();
    }

     RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .build();

}
