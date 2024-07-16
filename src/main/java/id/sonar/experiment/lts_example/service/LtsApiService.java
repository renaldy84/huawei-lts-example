package id.sonar.experiment.lts_example.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.sonar.experiment.lts_example.dto.LabelDto;
import id.sonar.experiment.lts_example.dto.LogRequestDto;
import id.sonar.experiment.lts_example.util.JsonUtil;
import id.sonar.experiment.lts_example.util.RestTemplateHttpUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LtsApiService {

    @Autowired
    LabelDto stringLabels;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplateHttpUtil restTemplateHttpUtil;

    @Autowired
    String ltsAPiUrl;

    public void sendLog(String authToken, String message){
        Instant instant = Instant.now();
        long timestampNs = instant.toEpochMilli() * 1_000_000 + instant.getNano();
        LOGGER.info(ltsAPiUrl);
        LogRequestDto requestDto = LogRequestDto
        .builder()
        .labels(stringLabels)
        .logTimeNs(timestampNs)
        .contents(Arrays.asList(message))
        .build();

        String jsonBody = JsonUtil.toJsonJackson(requestDto);
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("content-type","application/json; charset=UTF-8");
        headers.put("X-Auth-Token", authToken);
        
        try{
            LOGGER.info("Send: {}", jsonBody);
            ResponseEntity<String> response = restTemplateHttpUtil.postJsonTemplate(
                    ltsAPiUrl,
                    jsonBody,
                    headers);
            
            LOGGER.info("Status:{} Body:{}",
                    response.getStatusCode(),
                    response.getBody());
            
        }catch(Exception error){
            LOGGER.error(error.getMessage());
        }
    }
}

