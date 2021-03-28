package com.javarush.games.racer.road;

import com.javarush.engine.cell.Game;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс будет отвечать за регулировку дорожного движения.
 */
public class RoadManager {

    // x - координата левой границы дороги
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH;
    // x - координата правой границы дороги
    public static final int RIGHT_BORDER = RacerGame.WIDTH - LEFT_BORDER;
    // крайняя левая и крайняя правая позиции координат x матриц объектов-препятствий на проезжей части
    private static final int FIRST_LANE_POSITION = 16;
    private static final int FOURTH_LANE_POSITION = 44;
    // список всех текущих объектов-препятствий
    private List<RoadObject> items = new ArrayList<>();

    // создание объектов-препятствий
    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        if (type == RoadObjectType.THORN) {
            return new Thorn(x, y);
        }
        return null;
    }

    // генерирует позицию нового препятствия и добавляет его в список всех объектов-препятствий
    private void addRoadObject(RoadObjectType type, Game game) {

        // случайное число в пределах проезжей части
        int x = game.getRandomNumber(FIRST_LANE_POSITION, FOURTH_LANE_POSITION);
        // изначально объект располагается за пределами игрового поля, чтобы появиться плавно
        int y = -1 * RoadObject.getHeight(type);
        // создает объект-препятствие
        RoadObject roadObject = createRoadObject(type, x, y);
        // добавляет в список текущих объектов-препятствий
        if (roadObject != null) {
            items.add(roadObject);
        }
    }
}
