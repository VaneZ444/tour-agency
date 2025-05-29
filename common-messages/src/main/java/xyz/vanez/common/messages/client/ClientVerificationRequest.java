package xyz.vanez.common.messages.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Getter
@NoArgsConstructor
public class ClientVerificationRequest {
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("requestId")
    private String requestId;
}
