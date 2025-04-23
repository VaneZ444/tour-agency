package xyz.vanez.common.messages.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class ClientVerificationResponse {
    private String requestId;
    private boolean isValid;
    private String clientId;
}
