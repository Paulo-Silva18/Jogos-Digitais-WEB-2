package com.jogosdigitais.demo.game;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.jogosdigitais.demo.model.Game;
import com.jogosdigitais.demo.repository.GameRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa application-test.properties
@Transactional // Limpa o banco após cada teste
public class GameIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    @Test
    @WithMockUser(authorities = { "Admin" })
    void testSaveGameIntegration() throws Exception {
        
        Game gameA = new Game();
        gameA.setDescription("Descricao");
        gameA.setTitle("Game A");
        gameA.setPrice(65.24F);
        gameA.setStockQuantity(121);

        mockMvc.perform(post("/game/save")
                .with(csrf())
                .flashAttr("game", gameA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"));

        // Verifica no banco se foi salvo
        assertTrue(gameRepository.findAll()
                .stream()
                .anyMatch(g -> "Game A".equals(g.getTitle())));
    }
}