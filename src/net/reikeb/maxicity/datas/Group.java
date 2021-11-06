package net.reikeb.maxicity.datas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Group {

    TEAM_ONE(0, "team_one"),
    TEAM_TWO(1, "team_two"),
    TEAM_THREE(2, "team_three"),
    TEAM_FOUR(3, "team_four"),
    MODERATOR(4, "moderator"),
    ADMIN(5, "admin");

    private final int id;
    private final String name;

    private static final List<Group> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static Group randomTeam() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static String randomTeamId() {
        return byId(randomTeam());
    }

    public static Group byName(String name) {
        return byName(name, TEAM_ONE);
    }

    public static Group byName(String name, Group group) {
        for (Group team : values()) {
            if (team.name.equals(name)) {
                return team;
            }
        }
        return group;
    }

    public static String byId(Group group) {
        return byId(group, "team_one");
    }

    public static String byId(Group group, String id) {
        for (Group team : values()) {
            if (team.equals(group)) {
                return team.name;
            }
        }
        return id;
    }
}
