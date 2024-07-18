package id.sonar.experiment.lts_example.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenApiService {

    private RestTemplateHttpUtil restTemplateHttpUtil;
    private String authUrlString;
    private String authBody;
    private String domain;
    private String username;
    private String password;
    private String activeToken;
    private String region;
    
    public void init(){
        DomainDto domainDto = DomainDto.builder()
        .name(domain)
        .build();

        UserDto userDto = UserDto
        .builder()
        .domain(domainDto)
        .name(username)
        .password(password)
        .build();
        PasswordDto passwordDto = PasswordDto.builder().user(userDto).build();
        IdentityDto identityDto = IdentityDto
                .builder()
                .methods(Arrays.asList("password"))
                .password(passwordDto)
                .build();

        ProjectDto projectDto = ProjectDto.builder().name(region).build();
        ScopeDto scopeDto = ScopeDto.builder().project(projectDto).build();
        AuthDto authDto = AuthDto.builder().identity(identityDto).scope(scopeDto).build();
        AuthRequestDto authRequestDto = AuthRequestDto.builder().auth(authDto).build();
        this.authBody = JsonUtil.toJson(authRequestDto);
       

    }
    public ResponseEntity<String> postJsonTemplate(String url, String body, Map<String, String> headers) {
		System.out.println(url);
        HttpHeaders header = new HttpHeaders();
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			header.set(entry.getKey(), entry.getValue());
		}
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(body, header);
        RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(url, entity, String.class);
			LOGGER.trace(response.getBody());
		}  catch (HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
            LOGGER.error("HTTP Status Code: {}, Error Message: {}", e.getRawStatusCode(), e.getMessage(), e);
            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }

		return response;
	}
    //@TODO: check expiry time. 
    public String getToken() {
        System.out.println("getting token");
        if(Objects.isNull(activeToken)){
            System.out.println("getting token 2");
            Map<String,String> headers = new HashMap<String,String>();
            headers.put("content-type","application/json; charset=UTF-8");
            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();
            System.out.println("step 1");

           
            try{
                System.out.println("step 2");
                
                LOGGER.info("getting AuthToken");
                ObjectMapper objectMapper = new ObjectMapper();
                if(Objects.isNull(restTemplateHttpUtil)){
                    restTemplateHttpUtil = new RestTemplateHttpUtil();
                }
                restTemplateHttpUtil.setRestTemplate(new RestTemplate());
                ResponseEntity<String> response = this.postJsonTemplate(
                                                authUrlString,
                                                authBody,
                                                headers);
                HttpHeaders responseHeaders = response.getHeaders();
                String responseBodyString = response.getBody();
                System.out.println(responseBodyString);
                AuthResponseDto authResponseDto = objectMapper.readValue(responseBodyString, AuthResponseDto.class);
                
                String authToken = responseHeaders.getFirst("X-Subject-Token");
                System.out.println("step 3");
                LOGGER.info("retrieved token: {}",authToken);
                System.out.println("step 4");
                endTime = System.currentTimeMillis();
                // Calculate and log elapsed time in seconds
                long elapsedTime = (endTime - startTime) / 1000L;
                LOGGER.info("Elapsed Time: {} seconds", String.valueOf(elapsedTime));
                if(StringUtils.isNoneBlank())
                    activeToken = authToken;
                return authToken;

            }catch(JsonMappingException error){
                LOGGER.error(error.getMessage());
            }catch(JsonProcessingException error){
                LOGGER.error(error.getMessage());
            }
            return "";
        }else{
            System.out.println("using activetoken");
            LOGGER.info("using ActiveToken");
            return activeToken;
        }
    }

    public boolean isTokenAvailable(){
        return !Objects.isNull(activeToken);
    }
}
