package id.sonar.experiment.lts_example.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.sonar.experiment.lts_example.dto.AuthDto;
import id.sonar.experiment.lts_example.dto.AuthRequestDto;
import id.sonar.experiment.lts_example.dto.AuthResponseDto;
import id.sonar.experiment.lts_example.dto.DomainDto;
import id.sonar.experiment.lts_example.dto.IdentityDto;
import id.sonar.experiment.lts_example.dto.PasswordDto;
import id.sonar.experiment.lts_example.dto.ProjectDto;
import id.sonar.experiment.lts_example.dto.ScopeDto;
import id.sonar.experiment.lts_example.dto.UserDto;
import id.sonar.experiment.lts_example.util.JsonUtil;
import id.sonar.experiment.lts_example.util.RestTemplateHttpUtil;
import id.sonar.experiment.lts_example.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthTokenApiService {

    @Autowired
    private RestTemplateHttpUtil restTemplateHttpUtil;
    
    @Value("${app.endpoints.auth}")
    private String authUrlString;

    @Autowired
    private String authBody;
    
    String activeToken;


    
    //@TODO: check expiry time. 
    public String getToken() {
       
        if(Objects.isNull(activeToken)){
          
            Map<String,String> headers = new HashMap<String,String>();
            headers.put("content-type","application/json; charset=UTF-8");
            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();
            LOGGER.info("getToken from api");

           
            try{
              
                ObjectMapper objectMapper = new ObjectMapper();
                if(Objects.isNull(restTemplateHttpUtil)){
                    restTemplateHttpUtil = new RestTemplateHttpUtil();
                }
                restTemplateHttpUtil.setRestTemplate(new RestTemplate());
                ResponseEntity<String> response = restTemplateHttpUtil.postJsonTemplate(
                                                authUrlString,
                                                authBody,
                                                headers);
                HttpHeaders responseHeaders = response.getHeaders();
                String responseBodyString = response.getBody();
                
                AuthResponseDto authResponseDto = objectMapper.readValue(responseBodyString, AuthResponseDto.class);
                
                String authToken = responseHeaders.getFirst("X-Subject-Token");
               
                endTime = System.currentTimeMillis();
                // Calculate and log elapsed time in seconds
                long elapsedTime = (endTime - startTime) / 1000L;
              
                if(StringUtils.isNoneBlank(authToken))
                    activeToken = authToken;

                LOGGER.info("got token ? {} ", (StringUtils.isNoneBlank(authToken) ? "Yes" : "No"));
                return authToken;


            }catch(JsonMappingException error){
                System.out.println(error.getMessage());
                LOGGER.error(error.getMessage());
            }catch(JsonProcessingException error){
                LOGGER.error(error.getMessage());
            }
            return "";
        }else{
            
            return activeToken;
        }
    }

    public boolean isTokenAvailable(){
        return StringUtil.isNoneBlank(activeToken);
    }
}
