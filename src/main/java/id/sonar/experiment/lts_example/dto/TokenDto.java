package id.sonar.experiment.lts_example.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    @JsonProperty("expires_at")
    String expiresAt;
    List<String> catalog;
    List<RoleDto> roles;
    @JsonProperty("project")
    ProjectDto project;
    @JsonProperty("issued_at")
    String issuedAt;
    UserDto user;
}
