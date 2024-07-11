package id.sonar.experiment.lts_example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    String name;
    String password;
    DomainDto domain;
    String id;
    @JsonProperty("password_expires_at")
    String passwordExpiresAt;
}
