package com.game.service;

import com.game.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

// ---   4. Реализация функционала   ---

public class ServiceHelper {

    



    public List<Player> getPageList (List<Player> playersList, Integer pageNumber, Integer pageSize) {
        //количество страниц
        if (pageNumber == null) pageNumber = 0; //если параметр pageNumber не указан, нужно использовать 0
        //количество результатов на странице
        if (pageSize == null) pageSize = 3;     //если параметр pageSize не указан, нужно использовать 3

        int from = pageNumber * pageSize;
        int to = from + pageSize;
        if (to > from) to = playersList.size();

        return playersList.subList(from, to);
    }

    public boolean isValidID (long id) {
        return id > 0;
    }

    public boolean isValidName (String name) {
        return name != null && name.length() <= 12 && !name.isEmpty();
    }

    public boolean isValidTitle (String title) {
        return title != null && title.length() <= 30;
    }

    public boolean isValidExp (int exp) {
        return exp >= 0 && exp <= 10_000_000;
    }

    public boolean isValidDate (Date date) {
        if (date == null) return false;
        if (date.getTime() < 0) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.set(1999, Calendar.DECEMBER, 31);
        Date from = calendar.getTime();
        calendar.set(3000, Calendar.DECEMBER, 31);
        Date to = calendar.getTime();
        return date.after(from) && date.before(to);
    }

    public void currentLevel(Player player) {
        player.setLevel(((int) (Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100));
    }

    public void expToTheNextLevel(Player player) {
        player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience());
    }

    public Long convertToLong (String idString) {
        if (idString == null) return null;
        try {
            return Long.parseLong(idString);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}



    /*String name, String title, Race race, Profession profession, Long after,
    Long before, Boolean banned, Integer minExperience, Integer maxExperience,
    Integer minLevel, Integer maxLevel*/
