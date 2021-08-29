package com.game.controller;

import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Controller
@RequestMapping("/rest/players")
public class PlayersController {

    //получать список всех зарегистрированных игроков;
    //получать отфильтрованный список игроков в соответствии с переданными фильтрами;
    @GetMapping()
    public String getPlayersList (@RequestParam(value = "name") String name,
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
                                  @RequestParam(value = "pageSize") Integer pageSize,
                                  Model model) {
        return null;
    }

    //создавать нового игрока;
    @GetMapping()
    public String createPlayer (Model model) {
        return null;
    }
    //редактировать характеристики существующего игрока;
    @GetMapping("/{id}")
    public String updatePlayer(@PathVariable("id") int id, Model model) {
        return null;
    }
    //удалять игрока;
    @GetMapping("/{id}")
    public String deletePlayer(@PathVariable("id") int id, Model model) {
        return null;
    }
    //получать игрока по id;
    @GetMapping("/{id}")
    public String getPlayer(@PathVariable("id") int id, Model model) {
        return null;
    }
    //получать количество игроков, которые соответствуют фильтрам;
    @GetMapping("/count")
    public String getPlayersCount (Model model) {
        return null;
    }



}
