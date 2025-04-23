package xyz.vanez.common.messages.client;

import lombok.Data;

@Data
public class ClientVerificationResponse {
    private String requestId;
    private boolean isValid;
    private String clientId;
}
