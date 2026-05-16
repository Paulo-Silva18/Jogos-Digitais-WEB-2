package com.jogosdigitais.demo.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.jogosdigitais.demo.controller.GameController;
import com.jogosdigitais.demo.model.Game;
import com.jogosdigitais.demo.service.GameService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(GameController.class)
@Import(br.edu.iftm.meuAppSpringJava.config.TestConfig.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameService gameService;

    @MockBean
    private com.jogosdigitais.demo.impl.UserServiceImpl userService;

    void resetMocks() {
        reset(gameService);
    }

    private List<Game> testCreateGameList(){
        Game gameB = new Game();
        gameB.setId(1L);
        gameB.setDescription("Descrição");
        gameB.setTitle("Game B");
        gameB.setPrice(65.24F);
        gameB.setStockQuantity(121);

        return List.of(gameB);
    }

    @Test
    @WithMockUser
    @DisplayName("GET /game - Listar jogos na tela Index sem usuário autenticado")
    void testIndexNotAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/game"))
                .andExpect(status().isUnauthorized()); // Correção aqui
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("GET /game - Listar jogos na tela Index com usuário logado")
    void testIndexAuthenticatedUser() throws Exception {
        when(gameService.getAllGames()).thenReturn(testCreateGameList());

        mockMvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(view().name("game/index"))
                .andExpect(model().attributeExists("gamesList"))
                .andExpect(content().string(containsString("Listagem de Jogos")))
                .andExpect(content().string(containsString("Game B")));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("GET /game/create - Exibe formulário de criação")
    void testCreateFormAuthorizedUser() throws Exception {
        mockMvc.perform(get("/game/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("game/create"))
                .andExpect(model().attributeExists("game"));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Manager" })
    @DisplayName("GET /game/create - Verificar o link de cadastrar para um usuário não admin logado")
    void testCreateFormNotAuthorizedUser() throws Exception {
        when(gameService.getAllGames()).thenReturn(testCreateGameList());

        // Obter o HTML da página renderizada pelo controlador
        mockMvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(view().name("game/create"))
                .andExpect(model().attributeExists("game"));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "admin" })
    @DisplayName("POST /game/save - Falha na validação e retorna para o formulário")
    void testSaveGameValidationError() throws Exception {
        Game game = new Game(); // Jogo sem nome, o que causará erro de validação

        mockMvc.perform(post("/game/save")
                .with(csrf())
                .flashAttr("game", game))
                .andExpect(status().isOk())
                .andExpect(view().name("game/create"))
                .andExpect(model().attributeHasErrors("game"));

        verify(gameService, never()).saveGame(any(Game.class));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "admin" })
    @DisplayName("POST /game/save - Jogo válido é salvo com sucesso")
    void testSaveValidGame() throws Exception {
        Game game = new Game();
        game.setTitle("Novo Jogo");
        game.setDescription("Descrição");
        game.setPrice(100F);
        game.setStockQuantity(10);

        mockMvc.perform(post("/game/save")
                .with(csrf())
                .flashAttr("game", game))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"));

        verify(gameService).saveGame(any(Game.class));
    }
}