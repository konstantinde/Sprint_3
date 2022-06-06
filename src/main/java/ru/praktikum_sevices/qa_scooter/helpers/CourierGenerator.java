package ru.praktikum_sevices.qa_scooter.helpers;

import io.qameta.allure.Step;
import ru.praktikum_sevices.qa_scooter.model.courier.Courier;

import java.security.SecureRandom;
import java.util.Random;

public class CourierGenerator {
    /**
     * Генератор случайных курьеров для создания
     */

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static Courier getRandomCourier() {
        String login = randomString(new Random().nextInt(5) + 5);
        String password = randomString(new Random().nextInt(5) + 5);
        String firstName = randomString(new Random().nextInt(5) + 5);
        return new Courier(login, password, firstName);
    }

    static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static String reverseString(String str, int index) {
        // метод для отзеркаливания строки
        if(index == 0){
            return str.charAt(0) + "";
        }
        char letter = str.charAt(index);
        return letter + reverseString(str, index-1);
    }
}
