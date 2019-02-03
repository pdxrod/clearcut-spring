package clearcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;

@SpringBootApplication(exclude = BatchAutoConfiguration.class)
public class Application {

    public static void main(String[] args) throws Exception {
      SpringApplication.run(Application.class, args);
    }
}
