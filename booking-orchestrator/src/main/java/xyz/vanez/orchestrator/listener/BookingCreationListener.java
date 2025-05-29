package xyz.vanez.orchestrator.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import xyz.vanez.common.messages.booking.BookingCreatedEvent;
import xyz.vanez.orchestrator.service.OrchestrationService;

@Slf4j
@Component
public class BookingCreationListener {

    private final OrchestrationService orchestrationService;

    public BookingCreationListener(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @RabbitListener(queues = "orchestrator.booking.created.queue")
    public void handleBookingCreated(BookingCreatedEvent event) {
        log.info("Received booking created event: {}", event);
        if (event.isSuccess()) {
            log.info("Booking created successfully for booking: {}", event.getBookingId());
            orchestrationService.processBookingCreated(event);
        } else {
            log.error("Booking creation failed for booking: {}", event.getBookingId());
            orchestrationService.handleBookingCreationFailed(event);
        }
    }
}
