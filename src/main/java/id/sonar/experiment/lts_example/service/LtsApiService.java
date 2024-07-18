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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtsApiService {

    LabelDto stringLabels;

    ObjectMapper objectMapper;

    RestTemplateHttpUtil restTemplateHttpUtil;
    RestTemplate restTemplate;
    String ltsAPiUrl;
    String apiEndpointString;
    String projectId;
    String groupId;
    String streamId;


    public void init() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        this.stringLabels = LabelDto.builder().userTag("string").build();
        this.restTemplateHttpUtil = new RestTemplateHttpUtil();
        this.objectMapper = new ObjectMapper();
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial((chain, authType) -> true) // Trust all certificates
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        restTemplate = new RestTemplate(factory);
    }

    public void createEndPoint() {
        this.ltsAPiUrl = String.format("%s/v2/%s/lts/groups/%s/streams/%s/tenant/contents",
                apiEndpointString,
                projectId,
                groupId,
                streamId);

        //System.out.println(ltsAPiUrl);
       // System.out.println("api endpoint: " + apiEndpointString);

    }

    public ResponseEntity<String> postJsonTemplate(String url, String body, Map<String, String> headers) {
        System.out.println(url);
        HttpHeaders header = new HttpHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            header.set(entry.getKey(), entry.getValue());
        }
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(body, header);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(url, entity, String.class);
           // LOGGER.trace(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
            LOGGER.error("HTTP Status Code: {}, Error Message: {}", e.getRawStatusCode(), e.getMessage(), e);
            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }

        return response;
    }

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
            ResponseEntity<String> response = postJsonTemplate(
                    ltsAPiUrl,
                    jsonBody,
                    headers);

            // LOGGER.info("Status:{} Body:{}",
            //         response.getStatusCode(),
            //         response.getBody());

        } catch (Exception error) {
            LOGGER.error(error.getMessage());
        }
    }

    public void sendLogBatch(String authToken, List<String> messages) {
        Instant instant = Instant.now();
        long timestampNs = instant.toEpochMilli() * 1_000_000 + instant.getNano();
        // LOGGER.info(ltsAPiUrl);
        // LOGGER.info("authToken used: {}", authToken);
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
                //LOGGER.info("Send: {}", jsonBody);
                ResponseEntity<String> response = postJsonTemplate(
                        ltsAPiUrl,
                        jsonBody,
                        headers);

                // LOGGER.info("Status:{} Body:{}",
                //         response.getStatusCode(),
                //         response.getBody());

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
