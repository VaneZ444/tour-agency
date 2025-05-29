package xyz.vanez.client.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import xyz.vanez.common.messages.client.ClientVerificationRequest;
import xyz.vanez.common.messages.client.ClientVerificationResponse;
import xyz.vanez.client.service.ClientService;

import java.nio.charset.StandardCharsets;

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
    public void handleVerificationRequest(Message message) {
        try {
            String rawBody = new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("Raw message received: {}", rawBody);

            // Десериализация вручную для отладки
            ObjectMapper mapper = new ObjectMapper();
            ClientVerificationRequest request = mapper.readValue(rawBody, ClientVerificationRequest.class);
            log.info("Deserialized request: {}", request);

            // Обработка запроса
            boolean isVerified = clientService.verifyClient(request.getClientId());
            ClientVerificationResponse response = new ClientVerificationResponse(
                    request.getRequestId(),
                    isVerified,
                    request.getClientId()
            );

            rabbitTemplate.convertAndSend("orchestrator.exchange", "client.verified", response);
        } catch (Exception e) {
            log.error("Error processing verification request", e);
        }
    }
}
