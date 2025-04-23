package xyz.vanez.client.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import xyz.vanez.common.messages.client.ClientVerificationRequest;
import xyz.vanez.common.messages.client.ClientVerificationResponse;
import lombok.extern.slf4j.Slf4j;

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

        ClientVerificationResponse response = new ClientVerificationResponse();
        response.setRequestId(request.getRequestId());
        response.setClientId(request.getClientId());
        response.setValid(isVerified);

        rabbitTemplate.convertAndSend(
                "orchestrator.exchange",
                "client.verified",
                response
        );
    }
}