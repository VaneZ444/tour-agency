package xyz.vanez.orchestrator.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.vanez.common.messages.booking.BookingCreatedEvent;
import xyz.vanez.orchestrator.service.OrchestrationService;
import xyz.vanez.orchestrator.state.BookingEvent;

@Component
public class BookingCreationListener {

    private final OrchestrationService orchestrationService;

    public BookingCreationListener(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @RabbitListener(queues = "orchestrator.booking.created.queue")
    public void handleBookingCreated(BookingCreatedEvent event) {
        if (event.isSuccess()) {
            orchestrationService.processBookingCreated(event);
        } else {
            orchestrationService.handleBookingCreationFailed(event);
        }
    }
}
