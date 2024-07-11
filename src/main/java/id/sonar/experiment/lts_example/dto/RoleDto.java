package id.sonar.experiment.lts_example.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoleDto {
    String id;
    String name;
}
