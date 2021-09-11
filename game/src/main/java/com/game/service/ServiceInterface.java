package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ServiceInterface {

    List<Player> getPlayersList(Specification specification);
    Page<Player> getFilteredList(Specification specification, Pageable pageable);
    Player getPlayer(Long id);
    Long getPlayersCount(Specification specification);
    Player createPlayer(Player player);
    Player updatePlayer(Player player);
    void deletePlayer(Long id);
}
