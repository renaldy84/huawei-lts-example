package id.sonar.experiment.lts_example.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
public class LtsConfig {
   
}
