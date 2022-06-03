package ru.praktikum_sevices.qa_scooter.model.courier;

public class CourierCredentials {
    /**
     * POJO класс модели логина курьера в системе (логин/пароль)
     */

    private String login;

    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }


    public CourierCredentials() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CourierCredentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
