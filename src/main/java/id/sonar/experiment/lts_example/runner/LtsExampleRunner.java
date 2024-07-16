package id.sonar.experiment.lts_example.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import id.sonar.experiment.lts_example.service.LtsApiService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LtsExampleRunner {

   
    @Autowired
    String authToken;

    @Autowired
    LtsApiService ltsApiService;
    /**
     * @TODO 
     * hit LTS API
     */
    @EventListener(ContextRefreshedEvent.class)
    public void run(){
        //https://100.125.4.58:8102/v2/{project_id}/lts/groups/{log_group_id}/streams/{log_stream_id}/tenant/contents
      //LOGGER.info("authToken: {}",authToken);
      ltsApiService.sendLog(authToken, "Fri Feb  1 07:48:04 UTC 2019");
    }
}
