package xyz.vanez.booking.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import xyz.vanez.booking.service.BookingService;
import xyz.vanez.common.messages.booking.BookingRequest;

@Slf4j
@Component
public class BookingCreationListener {

    private final BookingService bookingService;

    public BookingCreationListener(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RabbitListener(queues = "booking.create.queue")
    public void handleBookingCreation(BookingRequest request) {
        log.info("Received booking creation request: {}", request);
        bookingService.createBooking(request);
    }
}