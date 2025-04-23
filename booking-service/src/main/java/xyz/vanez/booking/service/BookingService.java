package xyz.vanez.booking.service;

import xyz.vanez.common.messages.booking.BookingRequest;

public interface BookingService {
    void createBooking(BookingRequest request);
}
