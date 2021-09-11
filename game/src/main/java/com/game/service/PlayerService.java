package com.game.service;

import com.game.model.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

// ----- 3. Создаем методы для реализации необходимых возможностей. ------

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    //получать список всех зарегистрированных игроков;
    public List<Player> getPlayersList(Specification specification) {
        return playerRepository.findAll();
    }

    //получать игроков по фильтру, отображение в соответствии с pageable;
    public Page<Player> getFilteredList(Specification specification, Pageable pageable) {
        return playerRepository.findAll(specification, pageable);
    }

    //получать игрока по id;
    public Player getPlayer(Long id) {
        return playerRepository.findById(id).get();
    }

    //получать количество игроков, которые соответствуют фильтрам;
    public Long getPlayersCount(Specification specification) {
        return playerRepository.count(specification);
    }

    //создавать нового игрока;
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    //редактировать характеристики существующего игрока;
    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }

    //удалять игрока по id;
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
