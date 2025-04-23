package xyz.vanez.common.messages.client;

import lombok.Data;

@Data
public class ClientVerificationRequest {
    private String clientId;
    private String requestId;
}