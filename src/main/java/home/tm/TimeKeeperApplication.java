package home.tm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "home.tm")
@EnableJpaRepositories(basePackages = "home.tm.repositories")
@EntityScan(basePackages = "home.tm.model")
public class TimeKeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeKeeperApplication.class, args);
    }
}
