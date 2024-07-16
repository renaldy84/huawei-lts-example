package id.sonar.experiment.lts_example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import id.sonar.experiment.lts_example.dto.LabelDto;

@Configuration
public class LtsConfig {
    @Value("${app.project_id}")
    String projectId;
    @Value("${app.log_group_id}")
    String groupId;
    @Value("${app.log_stream_id}")
    String streamId;
    @Value("${app.endpoints.lts}")
    String apiEndpointString;
    @Bean
    public LabelDto stringLabels(){
        return LabelDto
                .builder()
                .userTag("string")
                .build();
    }

    @Bean
    public String ltsAPiUrl(){
        return String.format("%s/v2/%s/lts/groups/%s/streams/%s/tenant/contents", 
            apiEndpointString,
            projectId, 
            groupId, 
            streamId);

    }
   
}
