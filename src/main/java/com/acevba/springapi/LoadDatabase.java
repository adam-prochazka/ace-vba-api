package com.acevba.springapi;

import com.acevba.springapi.model.Badge;
import com.acevba.springapi.model.User;
import com.acevba.springapi.repository.BadgeRepository;
import com.acevba.springapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabaseUser(UserRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new User("user1")));
            log.info("Preloading " + repository.save(new User("user2")));
            log.info("Preloading " + repository.save(new User("user3")));
        };
    }

    @Bean
    CommandLineRunner initDatabaseBadge(BadgeRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Badge("level1", "role1")));
            log.info("Preloading " + repository.save(new Badge("level2", "role1")));
            log.info("Preloading " + repository.save(new Badge("level2", "role2")));
        };
    }

}