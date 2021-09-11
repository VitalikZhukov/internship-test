package com.game.service;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.model.Player;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class ServiceFilter {

    // фильтрация по имени
    public static Specification<Player> filterByName(String name) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
        };
    }

    //фильтрация по заголовку
    public static Specification<Player> filterByTitle(String title) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return title == null ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
            }
        };
    }

    //фильтрация по расе
    public static Specification<Player> filterByRace(Race race) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return race == null ? null : criteriaBuilder.equal(root.get("race"), "%" + race + "%");
            }
        };
    }

    //фильтрация по профессии
    public static Specification<Player> filterByProfession(Profession profession) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return profession == null ? null : criteriaBuilder.equal(root.get("profession"), "%" + profession + "%");
            }
        };
    }

    //фильтрация по др (даты, больше чем указанная)
    public static Specification<Player> filterByBirthdayMoreThan(Long time) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return time == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(time));
            }
        };
    }

    //фильтрация по др (даты, меньше чем указанная)
    public static Specification<Player> filterByBirthdayLessThan(Long time) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return time == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(time));
            }
        };
    }

    //фильтрация по опыту (опыт, больше чем указанный)
    public static Specification<Player> filterByExpMoreThan(Integer exp) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return exp == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), exp);
            }
        };
    }

    ////фильтрация по опыту (опыт, меньше чем указанный)
    public static Specification<Player> filterByExpLessThan(Integer exp) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return exp == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("experience"), exp);
            }
        };
    }

    //фильтрация по уровню (уровень, больше чем указанный)
    public static Specification<Player> filterByLevelMoreThan(Integer lvl) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return lvl == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("level"), lvl);
            }
        };
    }

    //фильтрация по уровню (уровень, меньше чем указанный)
    public static Specification<Player> filterByLevelLessThan(Integer lvl) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return lvl == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("level"), lvl);
            }
        };
    }

    //фильтрация по бану
    public static Specification<Player> filterIsBanned(Boolean ban) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return ban == null ? null : criteriaBuilder.equal(root.get("banned"), ban);
            }
        };
    }

}
