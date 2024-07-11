package id.sonar.experiment.lts_example.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdentityDto {
    List<String> methods;
    PasswordDto password;
}