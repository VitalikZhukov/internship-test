package com.game.service;

import com.game.model.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    //получать отфильтрованный список игроков в соответствии с переданными фильтрами;
    //получать список всех зарегистрированных игроков;
    public List<Player> getPlayersList(Specification<Player> spec, Pageable pageable) {
        return playerRepository.findAll(spec, (Sort) pageable);
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
    public long getPlayersCount(Specification<Player> spec) {
        return playerRepository.count(spec);
    }
}
