package com.jogosdigitais.demo.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jogosdigitais.demo.model.Game;
import com.jogosdigitais.demo.repository.GameRepository;
import com.jogosdigitais.demo.service.GameService;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public void saveGame(Game game) {
        this.gameRepository.save(game);
    }

    @Override
    public Game getGameById(long id) {
        Optional<Game> optional = gameRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Game not found with id: " + id);
        }
    }

    @Override
    public void deleteGameById(long id) {
        this.gameRepository.deleteById(id);
    }
}