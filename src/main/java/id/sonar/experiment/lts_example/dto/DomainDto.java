package id.sonar.experiment.lts_example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DomainDto {
    private String id;
    @JsonProperty("xdomain_id")
    private String xdomainId;
    @JsonProperty("xdomain_type")
    private String xdomainType;
    private String name;
}
