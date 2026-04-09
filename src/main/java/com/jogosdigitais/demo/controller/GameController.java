package com.jogosdigitais.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jogosdigitais.demo.model.Game;
import com.jogosdigitais.demo.service.GameService;

@Controller
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/game")
    public String index(Model model) {
        model.addAttribute("gamesList", gameService.getAllGames());
        return "game/index";
    }

    @GetMapping("/game/create")
    public String create(Model model) {
        model.addAttribute("game", new Game());
        return "game/create";
    }

    @PostMapping("/game/save")
    public String postMethodName(@ModelAttribute("game") Game game) {
        gameService.saveGame(game);
        return "redirect:/game";
    }

    @GetMapping("/game/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.gameService.deleteGameById(id);
        return "redirect:/game";
    }

    @GetMapping("/game/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Game game = gameService.getGameById(id);
        model.addAttribute("game", game);
        return "game/edit";
    }
}
