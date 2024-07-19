package id.sonar.experiment.lts_example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import id.sonar.experiment.lts_example.service.LtsApiService;
import id.sonar.experiment.lts_example.util.LTSLogAppender;

@Configuration
public class LTSLogAppenderConfig {
   
    @Autowired
    LtsApiService ltsApiService;

    /*
    @Bean
    public LTSLogAppender ltsLogAppender() {
        System.out.println("ltsLogAppenderConfig nih");
        LTSLogAppender appender = new LTSLogAppender();
        appender.setLtsApiService(ltsApiService);
        appender.setAuthToken(authToken);
        
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} - %msg%n");
        encoder.start();

        appender.setEncoder(encoder);
        appender.start();
        return appender;
    }
    */
        
}
