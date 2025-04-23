package xyz.vanez.common.messages.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
@Getter
public class ClientVerificationRequest {
    private String clientId;
    private String requestId;
}
