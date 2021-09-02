package com.game.repository;

import com.game.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

// ----- 2. Подключаем интерфейс с запросами к БД ------

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
