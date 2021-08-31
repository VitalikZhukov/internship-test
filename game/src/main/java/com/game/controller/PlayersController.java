package com.game.controller;

import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/players")
public class PlayersController {

    private final PlayerService playerService;

    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    //получать список всех зарегистрированных игроков;
    //получать отфильтрованный список игроков в соответствии с переданными фильтрами;
    @GetMapping()
    public String getPlayersList (Model model) {

        return null;
    }

    //получать игрока по id;
    @GetMapping("/{id}")
    public String getPlayer(@PathVariable("id") Long id, Model model) {
        return null;
    }

    //получать количество игроков, которые соответствуют фильтрам;
    @GetMapping("/count")
    public String getPlayersCount (Model model) {
        return null;
    }

    //создавать нового игрока;
    @PostMapping()
    public String createPlayer (Model model) {
        return null;
    }

    //редактировать характеристики существующего игрока;
    @PostMapping("/{id}")
    public String updatePlayer(@PathVariable("id") Long id, Model model) {
        return null;
    }

    //удалять игрока;
    @DeleteMapping("/{id}")
    public String deletePlayer(@PathVariable("id") Long id, Model model) {
        return null;
    }


    /*@RequestParam(value = "name") String name,
    @RequestParam(value = "title") String title,
    @RequestParam(value = "race") Race race,
    @RequestParam(value = "profession") Profession profession,
    @RequestParam(value = "after") Long after,
    @RequestParam(value = "before") Long before,
    @RequestParam(value = "banned") Boolean banned,
    @RequestParam(value = "minExperience") Integer minExperience,
    @RequestParam(value = "maxExperience") Integer maxExperience,
    @RequestParam(value = "minLevel") Integer minLevel,
    @RequestParam(value = "maxLevel") Integer maxLevel,
    @RequestParam(value = "order") PlayerOrder order,
    @RequestParam(value = "pageNumber") Integer pageNumber,
    @RequestParam(value = "pageSize") Integer pageSize,*/



}
