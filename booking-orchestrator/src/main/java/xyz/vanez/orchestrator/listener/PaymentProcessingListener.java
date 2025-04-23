package xyz.vanez.orchestrator.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.vanez.common.messages.payment.PaymentProcessedEvent;
import xyz.vanez.orchestrator.service.OrchestrationService;

@Component
public class PaymentProcessingListener {

    private final OrchestrationService orchestrationService;

    public PaymentProcessingListener(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @RabbitListener(queues = "orchestrator.payment.processed.queue")
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        if (event.isSuccess()) {
            orchestrationService.processPaymentProcessed(event);
        } else {
            orchestrationService.handlePaymentProcessingFailed(event);
        }
    }
}
