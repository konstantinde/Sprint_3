package ru.praktikum_sevices.qa_scooter.model.orders;

import java.util.List;

public class Order {
    /**
     * POJO класс модели заказа - Order
     */

    private int id;
    private int courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    private String track;
    private int status;
    private String createdAt;
    private String updatedAt;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public Order() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public String getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public List<String> getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public int getCourierId() {
        return courierId;
    }

    public String getTrack() {
        return track;
    }

    public int getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Order setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Order setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public Order setMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public Order setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Order setRentTime(String rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public Order setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public Order setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Order setColor(List<String> color) {
        this.color = color;
        return this;
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public Order setCourierId(int courierId) {
        this.courierId = courierId;
        return this;
    }

    public Order setTrack(String track) {
        this.track = track;
        return this;
    }

    public Order setStatus(int status) {
        this.status = status;
        return this;
    }

    public Order setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Order setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Order getCreateOrderDefault() {
        return new Order()
                .setFirstName("Naruto")
                .setLastName("Uchiha")
                .setAddress("Konoha")
                .setMetroStation("4")
                .setPhone("+7 800 555 35 35")
                .setRentTime("3")
                .setDeliveryDate("2022-06-06")
                .setComment("Saske, come back to Konoha")
                .setColor(List.of("BLACK"));
    }

    @Override
    public String toString() {
        return "Order{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", metroStation='" + metroStation + '\'' +
                ", phone='" + phone + '\'' +
                ", rentTime='" + rentTime + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", comment='" + comment + '\'' +
                ", color=" + color +
                '}';
    }
}


