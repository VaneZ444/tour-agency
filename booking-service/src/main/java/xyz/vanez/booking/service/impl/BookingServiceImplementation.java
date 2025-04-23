package xyz.vanez.booking.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import xyz.vanez.common.messages.booking.BookingRequest;
import xyz.vanez.common.messages.booking.BookingCreatedEvent;
import xyz.vanez.booking.model.Booking;
import xyz.vanez.booking.service.BookingService;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookingServiceImplementation implements BookingService {

    private final RabbitTemplate rabbitTemplate;
    private final Map<String, Booking> bookingRepository = new HashMap<>();

    public BookingServiceImplementation(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void createBooking(BookingRequest request) {
        Booking booking = new Booking(request.bookingId(), request.clientId(), request.tourId(), request.bookingDate());
        bookingRepository.put(booking.getBookingId(), booking);

        BookingCreatedEvent event = new BookingCreatedEvent(booking.getBookingId(), true);
        rabbitTemplate.convertAndSend("orchestrator.exchange", "booking.created", event);
    }
}
