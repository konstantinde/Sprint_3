package ru.praktikum_sevices.qa_scooter.model.orders;

import java.util.List;

public class Orders {
    /**
     * POJO класс модели Orders (список заказов)
     */

    private List<Order> orders;
    private PageInfo pageInfo;
    private List<AvailableStations> availableStations;

    public Orders(List<Order> orders, PageInfo pageInfo, List<AvailableStations> availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    public Orders() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public List<AvailableStations> getAvailableStations() {
        return availableStations;
    }


    public Orders setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Orders setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }

    public Orders setAvailableStations(List<AvailableStations> availableStations) {
        this.availableStations = availableStations;
        return this;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orders=" + orders +
                ", pageInfo=" + pageInfo +
                ", availableStations=" + availableStations +
                '}';
    }
}
