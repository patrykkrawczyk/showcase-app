package pro.patrykkrawczyk.showcase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("pro.patrykkrawczyk.showcase.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

}
