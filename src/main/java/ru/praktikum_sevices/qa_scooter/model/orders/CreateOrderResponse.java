package ru.praktikum_sevices.qa_scooter.model.orders;

public class CreateOrderResponse {
    private int track;

    public CreateOrderResponse() {
    }

    public CreateOrderResponse(int track) {
        this.track = track;
    }

    public int getTrack() {
        return track;
    }

    @Override
    public String toString() {
        return "CreateOrderResponse{" +
                "track=" + track +
                '}';
    }

    public CreateOrderResponse withTrack(int track) {
        this.track = track;
        return this;
    }
}
