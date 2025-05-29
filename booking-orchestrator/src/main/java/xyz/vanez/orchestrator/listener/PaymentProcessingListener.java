package xyz.vanez.orchestrator.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import xyz.vanez.common.messages.payment.PaymentProcessedEvent;
import xyz.vanez.orchestrator.service.OrchestrationService;

@Slf4j
@Component
public class PaymentProcessingListener {

    private final OrchestrationService orchestrationService;

    public PaymentProcessingListener(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @RabbitListener(queues = "orchestrator.payment.processed.queue")
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        log.info("Received payment processed event: {}", event);
        if (event.isSuccess()) {
            log.info("Payment processed successfully for booking: {}", event.getBookingId());
            orchestrationService.processPaymentProcessed(event);
        } else {
            log.error("Payment processing failed for booking: {}", event.getBookingId());
            orchestrationService.handlePaymentProcessingFailed(event);
        }
    }
}
