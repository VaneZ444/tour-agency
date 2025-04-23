package xyz.vanez.booking.controller;

import org.springframework.web.bind.annotation.*;
import xyz.vanez.common.messages.booking.BookingRequest;
import xyz.vanez.booking.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public void createBooking(@RequestBody BookingRequest request) {
        bookingService.createBooking(request);
    }
}
