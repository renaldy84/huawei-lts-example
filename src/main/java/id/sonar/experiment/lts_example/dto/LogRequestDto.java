package id.sonar.experiment.lts_example.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogRequestDto {
    @JsonProperty("log_time_ns")
    private Long logTimeNs;
    @JsonProperty("contents")
    private List<String> contents;
    @JsonProperty("labels")
    private LabelDto labels;
}
