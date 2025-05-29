package xyz.vanez.orchestrator.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import xyz.vanez.common.messages.client.ClientVerificationResponse;
import xyz.vanez.orchestrator.service.OrchestrationService;

@Slf4j
@Component
public class ClientVerificationListener {

    private final OrchestrationService orchestrationService;

    public ClientVerificationListener(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @RabbitListener(queues = "orchestrator.client.verified.queue")
    public void handleVerificationResult(ClientVerificationResponse response) {
        log.info("Received client verification response: {}", response);
        if (response.isValid()) {
            log.info("Client verified successfully for client: {}", response.getClientId());
            orchestrationService.processClientVerified(response);
        } else {
            log.error("Client verification failed for client: {}", response.getClientId());
            orchestrationService.handleVerificationFailed(response);
        }
    }
}
