package com.game.controller;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.model.Player;
import com.game.service.PlayerService;
import com.game.service.ServiceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ---   5. Реализация контроллера   ---

@RestController
@RequestMapping("/rest/players")
public class PlayersController {

    @Autowired
    private PlayerService playerService;

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
                                        @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
                                        @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        List<Player> playerList = playerService.getFilteredList(Specification.where
                        (ServiceFilter.filterByName(name).and
                        (ServiceFilter.filterByTitle(title)).and
                        (ServiceFilter.filterByRace(race)).and
                        (ServiceFilter.filterByProfession(profession)).and
                        (ServiceFilter.filterByBirthdayMoreThan(after)).and
                        (ServiceFilter.filterByBirthdayLessThan(before)).and
                        (ServiceFilter.filterByExpMoreThan(minExperience)).and
                        (ServiceFilter.filterByExpLessThan(maxExperience)).and
                        (ServiceFilter.filterByLevelMoreThan(minLevel)).and
                        (ServiceFilter.filterByLevelLessThan(maxLevel)).and
                        (ServiceFilter.filterIsBanned(banned))), pageable).getContent();
        return playerList;
    }

    //получать игрока по id;
    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable("id") String id) {
        return playerService.getPlayer(id);
    }

    //получать количество игроков, которые соответствуют фильтрам;
    @GetMapping("/count")
    public Integer getPlayersCount (@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "race", required = false) Race race,
                                   @RequestParam(value = "profession", required = false) Profession profession,
                                   @RequestParam(value = "after", required = false) Long after,
                                   @RequestParam(value = "before", required = false) Long before,
                                   @RequestParam(value = "banned", required = false) Boolean banned,
                                   @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                   @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                   @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                   @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {
        return playerService.getPlayersCount(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
    }

    //создавать нового игрока;
    @PostMapping()
    public Player createPlayer (@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    //редактировать характеристики существующего игрока;
    @PostMapping("/{id}")
    public Player updatePlayer(@PathVariable("id") String id, @RequestBody Player player) {
        Player playerOld = playerService.getPlayer(id);
        return playerService.updatePlayer(player, playerOld);
    }

    //удалять игрока;
    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable("id") String id) {
        playerService.deletePlayer(id);
    }



}
