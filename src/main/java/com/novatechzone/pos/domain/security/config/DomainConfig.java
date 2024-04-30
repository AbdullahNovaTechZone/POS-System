package com.novatechzone.pos.domain.security.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.novatechzone.pos.domain")
@EnableJpaRepositories("com.novatechzone.pos.domain")
@EnableTransactionManagement
public class DomainConfig {
}
