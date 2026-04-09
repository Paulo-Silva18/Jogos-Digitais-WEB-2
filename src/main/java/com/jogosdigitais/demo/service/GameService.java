package com.jogosdigitais.demo.service;

import java.util.List;
import com.jogosdigitais.demo.model.Game;

public interface GameService {
    List<Game> getAllGames();
    void saveGame(Game game);
    Game getGameById(long id);
    void deleteGameById(long id);
}
