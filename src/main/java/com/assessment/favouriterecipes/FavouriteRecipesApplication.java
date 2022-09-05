package com.assessment.favouriterecipes;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FavouriteRecipesApplication {

    @Generated
    public static void main(String[] args) {

        SpringApplication.run(FavouriteRecipesApplication.class, args);
    }

}
