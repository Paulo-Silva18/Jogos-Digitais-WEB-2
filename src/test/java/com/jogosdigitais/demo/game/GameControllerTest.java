package com.jogosdigitais.demo.game;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.jogosdigitais.demo.config.TestConfig;
import com.jogosdigitais.demo.controller.GameController;
import com.jogosdigitais.demo.model.Game;
import com.jogosdigitais.demo.service.GameService;

@WebMvcTest(GameController.class)
@Import(TestConfig.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameService gameService;

    @AfterEach
    void resetMocks() {
        reset(gameService);
    }

    private List<Game> testCreateGameList(){
        Game gameB = new Game();
        gameB.setId(1L);
        gameB.setDescription("Descricao");
        gameB.setTitle("Jogo B");
        gameB.setPrice(65.24f);
        gameB.setStockQuantity(121);

        return List.of(gameB);
    }

    @Test
    @DisplayName("GET /game - Listar jogos na tela index sem usuário autenticado")
    void testIndexNotAuthenticatedUser() throws Exception {
         mockMvc.perform(get("/game"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("GET /game - Listar jogos na tela index com usuário logado")
    void testIndexAuthenticatedUser() throws Exception {
        when(gameService.getAllGames()).thenReturn(testCreateGameList());

        mockMvc.perform(get("/game"))
               .andExpect(status().isOk())
               .andExpect(view().name("game/index"))
               .andExpect(model().attributeExists("gamesList"))
               .andExpect(content().string(containsString("Catálogo de Jogos")))
               .andExpect(content().string(containsString("Jogo B")));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("GET /game - Exibe link de acesso ao form de cadastro de jogo")
    void testCreateFormAuthorizedUser() throws Exception {
        when(gameService.getAllGames()).thenReturn(testCreateGameList());
        mockMvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(view().name("game/index"))
                .andExpect(content().string(containsString("Cadastrar Novo Jogo")));
    }

    @Test
    @WithMockUser(username = "aluno2@iftm.edu.br", authorities = { "Manager" })
    @DisplayName("GET /game - Verificar o link de cadastrar para um usuario não admin logado")
    void testCreateFormNotAuthorizedUser() throws Exception {
        when(gameService.getAllGames()).thenReturn(testCreateGameList());
       // Obter o HTML da página renderizada pelo controlador
       mockMvc.perform(get("/game"))
            .andExpect(status().isOk())
            .andExpect(view().name("game/index"))
            .andExpect(content().string(not(containsString("Cadastrar Novo Jogo"))));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /game/save - Falha na validação e retorna para o formulário")
    void testSaveGameValidationError() throws Exception {
        Game game = new Game(); // Jogo sem título, o que causará erro de validação

        mockMvc.perform(post("/game/save")
                        .with(csrf())
                        .flashAttr("game", game))
                .andExpect(status().isOk())
                .andExpect(view().name("game/create"))
                .andExpect(model().attributeHasErrors("game"));

        verify(gameService, never()).saveGame(any(Game.class));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("POST /game/save - Jogo válido é salvo com sucesso")
    void testSaveValidGame() throws Exception {
        Game game = new Game();
        game.setTitle("Novo Jogo");
        game.setDescription("Descrição");
        game.setPrice(100f);
        game.setStockQuantity(10);

        mockMvc.perform(post("/game/save")
                        .with(csrf())
                        .flashAttr("game", game))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/game"));

        verify(gameService).saveGame(any(Game.class));
    }

}
