package com.game.service;

import com.game.entity.Player;

import java.util.Calendar;
import java.util.Date;

public class ServiceHelper {

    public boolean isValidID (Long id) {
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

    public void setCurrentLevel(Player player) {
        player.setLevel(((int) (Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100));
    }

    public void setExpToTheNextLevel(Player player) {
        player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience());
    }

}