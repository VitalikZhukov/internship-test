package com.game.controller;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.model.Player;
import com.game.service.PlayerService;
import com.game.service.ServiceFilter;
import com.game.service.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// ---   5. Реализация контроллера   ---

@RestController
@RequestMapping("/rest/players")
public class PlayersController extends ServiceHelper {

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
        return playerService.getFilteredList(Specification.where
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
    }

    //получать игрока по id;
    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable Long id) {
        if (!isValidID(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            return playerService.getPlayer(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //получать количество игроков, которые соответствуют фильтрам;
    @GetMapping("/count")
    public Long getPlayersCount (@RequestParam(value = "name", required = false) String name,
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
        return playerService.getPlayersCount(Specification.where
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
                        (ServiceFilter.filterIsBanned(banned))));
    }

    //создавать нового игрока;
    @PostMapping()
    public Player createPlayer (@RequestBody Player player) {
        if (!isValidID(player.getId()) || !isValidName(player.getName()) || !isValidTitle(player.getTitle()) ||
        !isValidExp(player.getExperience()) || !isValidDate(player.getBirthday())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (player.isBanned() == null) player.setBanned(false);
        return playerService.createPlayer(player);
    }

    //редактировать характеристики существующего игрока;
    @PostMapping("/{id}")
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        Player tempPlayer = getPlayer(id);
        if (!isValidName(player.getName()) || !isValidTitle(player.getTitle()) ||
                !isValidExp(player.getExperience()) || !isValidDate(player.getBirthday())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (player.getName() == null) player.setName(tempPlayer.getName());
        if (player.getTitle() == null) player.setTitle(tempPlayer.getTitle());
        if (player.getRace() == null) player.setRace(tempPlayer.getRace());
        if (player.getProfession() == null) player.setProfession(tempPlayer.getProfession());
        if (player.getExperience() == null) player.setExperience(tempPlayer.getExperience());
        if (player.isBanned() == null) player.setBanned(tempPlayer.isBanned());
        if (player.getBirthday() == null) player.setBirthday(tempPlayer.getBirthday());
        setCurrentLevel(player);
        setExpToTheNextLevel(player);
        return playerService.updatePlayer(player);
    }

    //удалять игрока;
    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        if (!isValidID(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            playerService.deletePlayer(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
