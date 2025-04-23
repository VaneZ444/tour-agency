package xyz.vanez.orchestrator.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.vanez.common.messages.client.ClientVerificationResponse;
import xyz.vanez.orchestrator.service.OrchestrationService;
import xyz.vanez.orchestrator.state.BookingEvent;

@Component
public class ClientVerificationListener {

    private final OrchestrationService orchestrationService;

    public ClientVerificationListener(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @RabbitListener(queues = "orchestrator.client.verified.queue")
    public void handleVerificationResult(ClientVerificationResponse response) {
        if (response.isValid()) {
            orchestrationService.processClientVerified(response);
        } else {
            orchestrationService.handleVerificationFailed(response);
        }
    }
}
