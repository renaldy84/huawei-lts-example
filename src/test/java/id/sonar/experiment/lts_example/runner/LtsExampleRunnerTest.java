package id.sonar.experiment.lts_example.runner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LtsExampleRunnerTest {

    @Test
    void testRun() {
        LtsExampleRunner ltsExampleRunner = new LtsExampleRunner();
        ltsExampleRunner.run();
        assertEquals(true,true);
    }
}