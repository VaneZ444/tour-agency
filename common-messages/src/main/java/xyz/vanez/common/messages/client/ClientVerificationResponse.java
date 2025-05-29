package xyz.vanez.common.messages.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ClientVerificationResponse {
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("isValid")
    private boolean isValid;
    @JsonProperty("clientId")
    private String clientId;
}
