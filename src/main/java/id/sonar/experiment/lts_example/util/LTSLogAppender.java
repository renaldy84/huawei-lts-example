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
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class LTSLogAppender extends AppenderBase<ILoggingEvent> implements SmartLifecycle{
   

    private PatternLayoutEncoder encoder;
    
   
    @Autowired
    private LtsApiService ltsApiService;

    @Autowired
    private volatile AuthTokenApiService authTokenApiService;
    
    private volatile Queue<String> eventQueue;
    private long lastFlushTime;
    private boolean isRunning = false;

    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }

    public LTSLogAppender(){
        eventQueue = new LinkedList<>();
        LOGGER.debug("LTSLogAppender constructed");
    }
    
   
    @PostConstruct
    public void init(){
        LOGGER.debug("init nih");
        System.out.println("authService Available ? " + (Objects.nonNull(authTokenApiService) ? "yes" : "no"));
        authTokenApiService.getToken();
    }
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.debug("INFO: onApplicationEvent nih");
        if(Objects.nonNull(authTokenApiService)){
            LOGGER.debug("AUTHTOKENSERVICE AVAILABLE");
        
        }
    }
    @Override
    protected void append(ILoggingEvent eventObject) {
        LOGGER.debug("append called");

        if(Objects.nonNull(authTokenApiService)){
            LOGGER.debug("APPEND: AUTHTOKENSERVICE AVAILABLE");
        
        }
        LOGGER.debug("is append called when running ? {}", (isRunning ? "Yes" : "no"));
        try{
            LOGGER.debug("token is available ? {}",authTokenApiService.isTokenAvailable());
        }catch(Exception error){
            System.out.println(error.getMessage());
        }
       
        //LOGGER.debug("is Append called when running ? " + (isRunning ? "Yes" : "no") + " and token is " + authTokenApiService.isTokenAvailable());
        if (encoder != null) {
            String logMessage = encoder.getLayout().doLayout(eventObject);
            eventQueue.add(logMessage);
        } else {
            // Fallback in case encoder is not set
            eventQueue.add(eventObject.getFormattedMessage());
        }
        //only flushEvents when we have token...
        if (eventQueue.size() >= 10 && isFlushTimeout() && isRunning && Objects.nonNull(authTokenApiService)) {
            flushEvents();
        }
        LOGGER.debug("remaining#0: {}", eventQueue.size());
       //dev only
        //flushEvents();
        
    }
    private void flushEvents() {
        System.out.println("flushEvents called : and token is "+ (authTokenApiService.isTokenAvailable() ? "Available": "Empty"));

            if(authTokenApiService.isTokenAvailable()) authTokenApiService.getToken();
            
            // Batch up the next 10 events
            List<String> messageBatchStrings = new ArrayList<String>();

            synchronized (eventQueue) {
                if(eventQueue.isEmpty()) System.out.println("...and event queue is empty");
                while (!eventQueue.isEmpty() && messageBatchStrings.size() < 10) {
                    messageBatchStrings.add(eventQueue.poll());
                }
            }
    
            
            // Check if messageBatchStrings is empty
            if (messageBatchStrings.isEmpty()) {
                return; // Skip the API call if there are no log events
            }

            try{
                System.out.println("Authtoken at flushEvents: " + (authTokenApiService.isTokenAvailable() ? "Available": "Empty"));
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
        if (System.currentTimeMillis() - lastFlushTime >= 1000 && !eventQueue.isEmpty()) {
            return true;
        }
        return false;
    }
    public PatternLayoutEncoder getEncoder() {
        return encoder;
    }

   

    @Override
    public void start() {
        super.start();
        //if(Objects.nonNull(authTokenApiService)){
        isRunning = true;
       // }
        
        System.out.println("start");
        System.out.println("authService Available ? " + (Objects.nonNull(authTokenApiService) ? "yes" : "no"));
        
    }
    @Override
    public void stop(){
        try{
            LOGGER.debug("STOP");
            // Flush any remaining events before stopping
            LOGGER.debug("remaining#1: {}", eventQueue.size());
            LOGGER.debug("has Object ? {}",
                        (Objects.nonNull(authTokenApiService) ? "Yes" : "No") );
            if(Objects.nonNull(authTokenApiService)) flushEvents();
            LOGGER.debug("appender stopped");
            LOGGER.debug("remaining#2: {}", eventQueue.size());
        }catch(Exception error){
            LOGGER.debug("stop error: {} ", error.getMessage());
        }
       
        
        super.stop();
        isRunning = false;
    }
    @Override
    public void stop(Runnable callback) {
        try{
            if(Objects.nonNull(authTokenApiService)) flushEvents();
        }catch(Exception error){
            LOGGER.debug(error.getMessage());
        }
        
        stop();
        callback.run();
        System.out.println("stop runnable");
    }

    @Override
    public boolean isAutoStartup() {
        System.out.println("autostart");
        return true;
    }

    @Override
    public int getPhase() {
        // This phase value ensures the appender starts last
        System.out.println("getPhase:" + String.valueOf(Integer.MAX_VALUE).toString());
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isRunning() {
        System.out.println("isRunning: " + (isRunning ? "Yes" : "no"));
        return isRunning;
    }
      
}

