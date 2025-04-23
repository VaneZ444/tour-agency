package xyz.vanez.booking.model;

import java.time.LocalDate;

public class Booking {

    private String bookingId;
    private String clientId;
    private String tourId;
    private LocalDate bookingDate;

    public Booking(String bookingId, String clientId, String tourId, LocalDate bookingDate) {
        this.bookingId = bookingId;
        this.clientId = clientId;
        this.tourId = tourId;
        this.bookingDate = bookingDate;
    }

    // Геттеры и сеттеры

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}
