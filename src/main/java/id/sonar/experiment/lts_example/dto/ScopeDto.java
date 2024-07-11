package id.sonar.experiment.lts_example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScopeDto {
    ProjectDto project;
}
