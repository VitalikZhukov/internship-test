package com.game.controller;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.model.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Player> getPlayersList (@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "race", required = false) Race race,
                                        @RequestParam(value = "profession", required = false) Profession profession,
                                        @RequestParam(value = "after", required = false) Long after,
                                        @RequestParam(value = "before", required = false) Long before,
                                        @RequestParam(value = "banned", required = false) Boolean banned,
                                        @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                        @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                        @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                        @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                        @RequestParam(value = "order", required = false) PlayerOrder order,
                                        @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        List<Player> playerList = playerService.getPlayersList(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
        //добавляем фильтр
        List<Player> filteredList = playerService.getFilteredList(playerList, order);
        return playerService.getPageList(filteredList, pageNumber, pageSize);
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



}
