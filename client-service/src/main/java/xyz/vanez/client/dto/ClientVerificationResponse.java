package xyz.vanez.client.dto;

import lombok.Data;

@Data
public class ClientVerificationResponse {
    private String requestId;
    private boolean isValid;
    private String clientId;
}
