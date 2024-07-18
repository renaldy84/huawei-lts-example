package id.sonar.experiment.lts_example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import id.sonar.experiment.lts_example.runner.LtsExampleRunner;

@SpringBootApplication
public class LtsExampleApplication implements CommandLineRunner {
	
	
    private final LtsExampleRunner ltsExampleRunner;

    @Autowired
    public LtsExampleApplication(LtsExampleRunner ltsExampleRunner) {
        this.ltsExampleRunner = ltsExampleRunner;
    }
	public static void main(String[] args) {
		SpringApplication.run(LtsExampleApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		ltsExampleRunner.run();
	}
	
}
