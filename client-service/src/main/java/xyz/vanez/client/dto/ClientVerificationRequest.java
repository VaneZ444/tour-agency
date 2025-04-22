package xyz.vanez.client.dto;

import lombok.Data;

@Data
public class ClientVerificationRequest {
    private String clientId;
    private String requestId;
}