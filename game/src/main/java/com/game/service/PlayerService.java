package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.controller.ServiceHelper;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.model.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

// ----- 3. Создаем методы для реализации необходимых возможностей. ------

@Service
public class PlayerService extends ServiceHelper {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    //получать список всех зарегистрированных игроков;
    public List<Player> getPlayersList(String name, String title, Race race, Profession profession, Long after,
                                       Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                       Integer minLevel, Integer maxLevel) {
        List<Player> playersList = new ArrayList<>();
        Date afterDate = after == null ? null : new Date(after);
        Date beforeDate = before == null ? null : new Date(before);
        playerRepository.findAll().forEach(player -> {
            if (name != null && !player.getName().contains(name)) return; //поиск по полю name происходит по частичному соответствию
            if (title != null && !player.getTitle().contains(title)) return; //поиск по полю title происходит по частичному соответствию
            if (race != null && (player.getRace() != race)) return;
            if (profession != null && player.getProfession() != profession) return;
            if (afterDate != null && player.getBirthday().before(afterDate)) return;
            if (beforeDate != null && player.getBirthday().after(beforeDate)) return;
            if (banned != null && player.isBanned() != banned) return;
            if (minExperience != null && player.getExperience().compareTo(minExperience) < 0) return;
            if (maxExperience != null && player.getExperience().compareTo(maxExperience) > 0) return;
            if (minLevel != null && player.getLevel().compareTo(minLevel) < 0) return;
            if (maxLevel != null && player.getLevel().compareTo(maxLevel) > 0) return;
            playersList.add(player);
        });
        return playersList;
    }

    //получать отфильтрованный список игроков в соответствии с переданными фильтрами;
    public List<Player> getFilteredList(List<Player> playersList, PlayerOrder order) {
        switch (order) {
            case NAME:
                playersList.sort(Comparator.comparing(Player::getName));
                break;
            case LEVEL:
                playersList.sort(Comparator.comparing(Player::getLevel));
                break;
            case BIRTHDAY:
                playersList.sort(Comparator.comparing(Player::getBirthday));
                break;
            case EXPERIENCE:
                playersList.sort(Comparator.comparing(Player::getExperience));
                break;
            default:
                playersList.sort(Comparator.comparing(Player::getId)); //если параметр order не указан, нужно использовать PlayerOrder.ID
        }
        return playersList;
    }

    //получать игрока по id;
    public Player getPlayer(String idS) {
        Long id = convertToLong(idS);
        if (isValidID(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (playerRepository.existsById(id)) {
            return playerRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //получать количество игроков, которые соответствуют фильтрам;
    public Integer getPlayersCount(String name, String title, Race race, Profession profession, Long after,
                                   Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                   Integer minLevel, Integer maxLevel) {
        return getPlayersList(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel).size();
    }

    //создавать нового игрока;
    public Player createPlayer(Player player) {
        if (!isValidName(player.getName()) || !isValidTitle(player.getTitle()) || !isValidExp(player.getExperience()) ||
        !isValidDate(player.getBirthday()) || player.getRace() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (player.isBanned() == null) player.setBanned(false); //если в запросе нет параметра banned, то считаем, что пришло значение false

        //  ---  параметры даты между фронтом и сервером передаются в миллисекундах Long   ---
        //                  УТОЧНИТЬ

        currentLevel(player);
        expToTheNextLevel(player);
        playerRepository.save(player);
        return player;
    }

    //редактировать характеристики существующего игрока;
    public Player updatePlayer(Player playerNew, Player playerOld) {
        if (!isValidID(playerOld.getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (playerRepository.findById(playerOld.getId()).isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        String name = playerNew.getName();
        if (isValidName(name)) {
            playerOld.setName(name);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String title = playerNew.getTitle();
        if (isValidTitle(title)) {
            playerOld.setTitle(title);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Race race = playerNew.getRace();
        if (race != null) {
            playerOld.setRace(race);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Profession profession = playerNew.getProfession();
        if (profession != null) {
            playerOld.setProfession(profession);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //  ---  параметры даты между фронтом и сервером передаются в миллисекундах Long   ---
        //                  УТОЧНИТЬ
        Date birthday = playerNew.getBirthday();
        if (birthday != null) {
            playerOld.setBirthday(birthday);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //если в запросе нет параметра banned, то считаем, что пришло значение false
        //   ---   скорее всего не действует на этот пункт   ---
        Boolean banned = playerNew.isBanned();
        if (banned != null) {
            playerOld.setBanned(banned);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Integer exp = playerNew.getExperience();
        if (exp != null) {
            playerOld.setExperience(exp);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        currentLevel(playerOld);
        expToTheNextLevel(playerOld);
        playerRepository.save(playerOld);
        return playerOld;
    }

    //удалять игрока по id;
    public void deletePlayer(String idS) {
        Long id = convertToLong(idS);
        if (!playerRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (isValidID(id)) {
            playerRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
