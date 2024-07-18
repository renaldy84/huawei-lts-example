/**
 * The Appender for sending the log into Huawei Log Service Tank
 * reference: https://mahfuzcse12.medium.com/sending-spring-boot-application-logs-to-aws-cloudwatch-49f5a018b983
 */
package id.sonar.experiment.lts_example.util;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import id.sonar.experiment.lts_example.service.AuthTokenApiService;
import id.sonar.experiment.lts_example.service.LtsApiService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.LogstashEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

@Slf4j
@Component
public class LTSLogAppender extends AppenderBase<ILoggingEvent> {
   

    private PatternLayoutEncoder encoder;
    private AuthTokenApiService authTokenApiService;
    private LtsApiService ltsApiService;
    
    private Queue<String> eventQueue;

    @Getter
    @Setter
    private String authEndpointString;
    @Getter
    @Setter
    private String apiEndpointString;
    @Getter
    @Setter
    private String projectId;
    @Getter
    @Setter
    private String groupId;
    @Getter
    @Setter
    private String streamId;
    @Getter
    @Setter
    private String domain;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String region;
    
    private long lastFlushTime;


    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }
    public LTSLogAppender(){
        eventQueue = new LinkedList<>();
    }
    @Override
    public void start(){
        super.start();

        System.out.println("projectId: " + projectId);

        try{
            authTokenApiService = AuthTokenApiService.builder()
                                .authUrlString(authEndpointString)
                                .domain(domain)
                                .region(region)
                                .username(username)
                                .password(password)
                                .build();
            authTokenApiService.init();
            authTokenApiService.getToken();

        }catch(Exception error){
            System.out.println(error.getMessage());
        }


        
        try {
            ltsApiService = LtsApiService.builder()
                .apiEndpointString(apiEndpointString)
                .projectId(projectId)
                .groupId( groupId)
                .streamId(streamId)
                .build();
            ltsApiService.init();
            ltsApiService.createEndPoint();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
          
            e.printStackTrace();
        }
        

    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      
       
        if (encoder != null) {
            String logMessage = encoder.getLayout().doLayout(eventObject);
            //String logMessage = encoder.encode(eventObject).toString();
            eventQueue.add(logMessage);
            
        } else {
            // Fallback in case encoder is not set
            eventQueue.add(eventObject.getFormattedMessage());
            
        }
        if (eventQueue.size() >= 10 && isFlushTimeout()) {
            flushEvents();
        }

       //dev only
        //flushEvents();
        
    }
    private void flushEvents() {

        // Batch up the next 10 events
        List<String> messageBatchStrings = new ArrayList<String>();
        while (!eventQueue.isEmpty() && messageBatchStrings.size() < 10) {
            messageBatchStrings.add(eventQueue.poll());
        }

        // Check if messageBatchStrings is empty
        if (messageBatchStrings.isEmpty()) {
            return; // Skip the API call if there are no log events
        }

        try{
            if(authTokenApiService.isTokenAvailable()){
                ltsApiService.sendLogBatch( authTokenApiService.getToken(), messageBatchStrings);
                System.out.println("sent?");
            }else{
                System.out.println("token is not available");
            }
           
        }catch(Exception error){
            System.err.println("cannot send log: " + error.getMessage());
            System.err.println(error.getCause());
        }
        

    }
    private boolean isFlushTimeout() {
        if (System.currentTimeMillis() - lastFlushTime >= 15000 && !eventQueue.isEmpty()) {
            return true;
        }
        return false;
    }
    public PatternLayoutEncoder getEncoder() {
        return encoder;
    }

    @Override
    public void stop(){
        // Flush any remaining events before stopping
        LOGGER.debug("flush remaining");
        flushEvents();
        System.out.println("appender stopped");
       
        super.stop();
    }
      
}

