package id.sonar.experiment.lts_example.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.sonar.experiment.lts_example.dto.LabelDto;
import id.sonar.experiment.lts_example.dto.LogRequestDto;
import id.sonar.experiment.lts_example.util.JsonUtil;
import id.sonar.experiment.lts_example.util.RestTemplateHttpUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LtsApiService {

    LabelDto stringLabels;

    ObjectMapper objectMapper;

    @Autowired
    RestTemplateHttpUtil restTemplateHttpUtil;
  

    @Autowired
    String ltsAPiUrl;
 

    public void sendLog(String authToken, String message) {
        Instant instant = Instant.now();
        long timestampNs = instant.toEpochMilli() * 1_000_000 + instant.getNano();
        
        LogRequestDto requestDto = LogRequestDto
                .builder()
                .labels(stringLabels)
                .logTimeNs(timestampNs)
                .contents(Arrays.asList(message))
                .build();

        String jsonBody = JsonUtil.toJsonJackson(requestDto);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("content-type", "application/json; charset=UTF-8");
        headers.put("X-Auth-Token", authToken);

        try {
            LOGGER.info("Send: {}", jsonBody);
            ResponseEntity<String> response = restTemplateHttpUtil.postJsonTemplate(
                    ltsAPiUrl,
                    jsonBody,
                    headers);

             LOGGER.info("Status:{} Body:{}",
                     response.getStatusCode(),
                    response.getBody());

        } catch (Exception error) {
            LOGGER.error(error.getMessage());
        }
    }

    public void sendLogBatch(String authToken, List<String> messages) {
        Instant instant = Instant.now();
        long timestampNs = instant.toEpochMilli() * 1_000_000 + instant.getNano();
        
        if (StringUtils.isNoneBlank(authToken)) {
            LogRequestDto requestDto = LogRequestDto
                    .builder()
                    .labels(stringLabels)
                    .logTimeNs(timestampNs)
                    .contents(messages)
                    .build();

            String jsonBody = JsonUtil.toJsonJackson(requestDto);
            System.out.println("JSONBODY ---> " + jsonBody);
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("content-type", "application/json; charset=UTF-8");
            headers.put("X-Auth-Token", authToken);

            try {
                LOGGER.info("Send: {}", jsonBody);
                ResponseEntity<String> response = restTemplateHttpUtil.postJsonTemplate(
                        ltsAPiUrl,
                        jsonBody,
                        headers);

                 LOGGER.info("Status:{} Body:{}",
                         response.getStatusCode(),
                         response.getBody());

            } catch (HttpClientErrorException error) {
                LOGGER.error(error.getMessage());
            } catch (Exception error) {
                LOGGER.error(error.getMessage());
            }
        } else {
            LOGGER.info("authToken is Empty");
        }

    }
}
