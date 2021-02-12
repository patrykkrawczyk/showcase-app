package pro.patrykkrawczyk.showcase.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(LiquibaseProperties.class)
public class LiquibaseConfiguration {

    private final Environment env;

    @Bean
    public SpringLiquibase liquibase(LiquibaseProperties liquibaseProperties,
                                     ObjectProvider<DataSource> dataSource) {

        DataSourceClosingSpringLiquibase liquibase = new DataSourceClosingSpringLiquibase();
        liquibase.setCloseDataSourceOnceMigrated(false);
        liquibase.setDataSource(dataSource.getIfAvailable());
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());

        log.debug("Configuring Liquibase");

        return liquibase;
    }
}
