package xyz.vanez.client.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import xyz.vanez.common.messages.client.ClientVerificationRequest;
import xyz.vanez.common.messages.client.ClientVerificationResponse;
import xyz.vanez.client.service.ClientService;

@Slf4j
@Service
public class ClientVerificationListener {

    private final RabbitTemplate rabbitTemplate;
    private final ClientService clientService;

    public ClientVerificationListener(RabbitTemplate rabbitTemplate, ClientService clientService) {
        this.rabbitTemplate = rabbitTemplate;
        this.clientService = clientService;
    }

    @RabbitListener(queues = "client.verify.queue")
    public void handleVerificationRequest(ClientVerificationRequest request) {
        log.info("Received verification request for client: {}", request.getClientId());
        boolean isVerified = clientService.verifyClient(request.getClientId());

        ClientVerificationResponse response = new ClientVerificationResponse(request.getRequestId(), isVerified, request.getClientId());
        rabbitTemplate.convertAndSend("orchestrator.exchange", "client.verified", response);
        log.info("Sent verification response for client: {}", request.getClientId());
    }
}
