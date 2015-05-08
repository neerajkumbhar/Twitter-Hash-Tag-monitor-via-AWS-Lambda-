package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan
public class TwitterAwsIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterAwsIntegrationApplication.class, args);
    }
}
