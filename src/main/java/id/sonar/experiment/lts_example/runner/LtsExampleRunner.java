package id.sonar.experiment.lts_example.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LtsExampleRunner {

    @Autowired
    String authToken;

    /**
     * @TODO 
     * hit LTS API
     */
    @Bean
    public void run(){
        //https://100.125.4.58:8102/v2/{project_id}/lts/groups/{log_group_id}/streams/{log_stream_id}/tenant/contents
      LOGGER.info("authToken: {}",authToken);
    }
}
