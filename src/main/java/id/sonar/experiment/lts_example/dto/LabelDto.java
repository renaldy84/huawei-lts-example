package id.sonar.experiment.lts_example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabelDto{
    @JsonProperty("user_tag")
    private String userTag;
}
