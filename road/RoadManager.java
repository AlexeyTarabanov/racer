package com.javarush.games.racer.road;

import com.javarush.games.racer.RacerGame;

/**
 * Класс будет отвечать за регулировку дорожного движения.
 */
public class RoadManager {

    // x - координата левой границы дороги
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH;
    // x - координата правой границы дороги
    public static final int RIGHT_BORDER = RacerGame.WIDTH - LEFT_BORDER;

    // создание объектов-препятствий
    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        if (type == RoadObjectType.THORN) {
            return new Thorn(x, y);
        }
        return null;
    }
}
