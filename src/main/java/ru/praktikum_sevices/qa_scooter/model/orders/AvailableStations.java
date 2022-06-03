package ru.praktikum_sevices.qa_scooter.model.orders;

public class AvailableStations {
    /**
     * POJO класс модели availableStations (список станций метро)
     */

    private String name;
    private String number;
    private String color;

    public AvailableStations(String name, String number, String color) {
        this.name = name;
        this.number = number;
        this.color = color;
    }

    public AvailableStations() {
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getColor() {
        return color;
    }


    public AvailableStations setName(String name) {
        this.name = name;
        return this;
    }

    public AvailableStations setNumber(String number) {
        this.number = number;
        return this;
    }

    public AvailableStations setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public String toString() {
        return "AvailableStations{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
