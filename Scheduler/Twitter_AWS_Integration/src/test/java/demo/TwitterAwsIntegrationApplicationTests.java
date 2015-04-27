package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TwitterAwsIntegrationApplication.class)
@WebAppConfiguration

public class TwitterAwsIntegrationApplicationTests {

	@Test
	public void contextLoads() {
	}

}
