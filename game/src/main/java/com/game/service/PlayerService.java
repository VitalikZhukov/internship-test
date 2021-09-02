package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.controller.ServiceHelper;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.model.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            case LEVEL:
                playersList.sort(Comparator.comparing(Player::getLevel));
            case BIRTHDAY:
                playersList.sort(Comparator.comparing(Player::getBirthday));
            case EXPERIENCE:
                playersList.sort(Comparator.comparing(Player::getExperience));
            default:
                playersList.sort(Comparator.comparing(Player::getId)); //если параметр order не указан, нужно использовать PlayerOrder.ID
        }
        return playersList;
    }

    //создавать нового игрока;
    public Player createPlayer(Player player) {

        return playerRepository.save(player);
    }

    //редактировать характеристики существующего игрока;
    public void updatePlayer(Long id) {
        playerRepository.save(getPlayer(id));
    }

    //удалять игрока по id;
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    //получать игрока по id;
    public Player getPlayer(Long id) {
        return playerRepository.getOne(id);
    }

    //получать количество игроков, которые соответствуют фильтрам.
    public long getPlayersCount() {
        return 0;
    }
}
