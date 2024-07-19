package id.sonar.experiment.lts_example.config;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AuthConfig {

    
    @Value("${app.username}")
    String usernameString;

    @Value("${app.password}")
    String passwordString;

    @Value("${app.region}")
    String regionString;

    @Value("${app.domain}")
    String domainString;

    @Value("${app.endpoints.auth}")
    private String authUrlString;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    RestTemplateHttpUtil restTemplateHttpUtil;

    String activeToken = null;

    @Bean
    public DomainDto domainDto(){
        return DomainDto.builder()
                .name(domainString)
                .build();
    }
    @Bean PasswordDto passwordDto(UserDto userDto){
        return PasswordDto.builder().user(userDto).build();
    }
    @Bean
    public UserDto userDto(DomainDto domainDto){
        return UserDto
                .builder()
                .domain(domainDto)
                .name(usernameString)
                .password(passwordString)
                .build();
    }
    @Bean
    public IdentityDto identityDto(PasswordDto passwordDto){
        return IdentityDto
                .builder()
                .methods(Arrays.asList("password"))
                .password(passwordDto)
                .build();
    }
    @Bean
    public ProjectDto projectDto(){
        return ProjectDto.builder().name(regionString).build();
    }

    @Bean
    public ScopeDto scopeDto(ProjectDto projectDto){
        return ScopeDto.builder().project(projectDto).build();
    }
    @Bean
    public AuthDto authDto(IdentityDto identityDto, ScopeDto scopeDto){
        return AuthDto.builder().identity(identityDto).scope(scopeDto).build();
    }
    @Bean
    public AuthRequestDto authRequestDto(AuthDto authDto){
        return AuthRequestDto.builder().auth(authDto).build();
    }
    @Bean
    public String authBody(AuthRequestDto authRequestDto){
        return JsonUtil.toJson(authRequestDto);
    }
    /*
    @Bean
    public String authToken(String authBody){
        if(Objects.isNull(activeToken)){
            System.out.println("getting token : " +  authBody);
            Map<String,String> headers = new HashMap<String,String>();
            headers.put("content-type","application/json; charset=UTF-8");
            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();
            
        
            try{
                LOGGER.info("getting AuthToken");
                ResponseEntity<String> response = restTemplateHttpUtil.postJsonTemplate(authUrlString,authBody,headers);
                HttpHeaders responseHeaders = response.getHeaders();
                String responseBodyString = response.getBody();
                AuthResponseDto authResponseDto = objectMapper.readValue(responseBodyString, AuthResponseDto.class);
                
                String authToken = responseHeaders.getFirst("X-Subject-Token");

                 LOGGER.info("token: {} | Expires at: {}",
                     authToken, 
                     authResponseDto.getToken().getExpiresAt()
                );

                endTime = System.currentTimeMillis();
                // Calculate and log elapsed time in seconds
                long elapsedTime = (endTime - startTime) / 1000L;
                LOGGER.info("Elapsed Time: {} seconds", String.valueOf(elapsedTime));
                return authToken;
            }catch(Exception error){
                LOGGER.error(error.getMessage());
            }
            return "";
        }else{
            LOGGER.info("using ActiveToken");
            return activeToken;
        }
    }*/
}
