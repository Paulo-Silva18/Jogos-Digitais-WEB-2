package com.jogosdigitais.demo.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import com.jogosdigitais.demo.service.GameService;


@TestConfiguration
public class TestConfig {
 
    @Bean
    public GameService gameService() {
        return Mockito.mock(GameService.class);
    }
}
