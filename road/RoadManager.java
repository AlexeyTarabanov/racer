package com.javarush.games.racer.road;

import com.javarush.engine.cell.Game;
import com.javarush.games.racer.PlayerCar;
import com.javarush.games.racer.RacerGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс будет отвечать за регулировку дорожного движения.
 * Управляет всеми препятствиями на дороге.
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
    // габариты машины с запасом (дистанцией)
    private static final int PLAYER_CAR_DISTANCE = 12;
    // количество машин, с которыми разминулся игрок
    private int passedCarsCount = 0;


    // создание объектов-препятствий
    private RoadObject createRoadObject(RoadObjectType type, int x, int y) {
        if (type == RoadObjectType.THORN) {
            return new Thorn(x, y);
        } else if (type == RoadObjectType.DRUNK_CAR) {
            return new MovingCar(x, y);
        }
        return new Car(type, x, y);
    }

    public int getPassedCarsCount() {
        return passedCarsCount;
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
        if (isRoadSpaceFree(roadObject)) {
            items.add(roadObject);
        }
    }

    // отрисовка препятствий
    public void draw(Game game) {
        for (RoadObject roadObject : items) {
            roadObject.draw(game);
        }
    }

    // передвигает хранимые объекты
    public void move(int boost) {
        for (RoadObject roadObject : items) {
            roadObject.move(boost + roadObject.speed, items);
        }
        // Чтобы генерировались новые шипы,
        // старые нужно удалять из списка items после того, как они вышли за пределы экрана.
        deletePassedItems();
    }

    // проверяет существует ли Thorn
    private boolean isThornExists() {
        for (RoadObject roadObject : items) {
            if (roadObject.type == RoadObjectType.THORN) {
                return true;
            }
        }
        return false;
    }

    // проверяет, есть ли на дороге шипы,
    // если их нет — генерирует новые с вероятностью 10%.
    private void generateThorn(Game game) {
        int randomNumber = game.getRandomNumber(100);
        if (randomNumber < 10 && !isThornExists()) {
            addRoadObject(RoadObjectType.THORN, game);
        }
    }

    // генерирует новые препятствия
    public void generateNewRoadObjects(Game game) {
        // создаем шипы
        generateThorn(game);
        // создаем новые машины
        generateRegularCar(game);
        // создаем "пьяные" машины
        generateMovingCar(game);
    }

    // удаляет вставленные элементы, если они вышли за края игрового поля
    // (за пределы экрана)
    private void deletePassedItems() {
        // удаляем если у объекта-препятствия координата y больше либо равна RacerGame.HEIGHT
//        items.removeIf(item -> item.y >= RacerGame.HEIGHT);

        Iterator<RoadObject> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            RoadObject item = itemIterator.next();
            if (item.y >= RacerGame.HEIGHT) {
                itemIterator.remove();
                if (item.type != RoadObjectType.THORN) {
                    passedCarsCount++;
                }
            }
        }
    }

    // проверяет, произошла ли авария
    public boolean checkCrush(PlayerCar playerCar) {
        for (RoadObject roadObject : items) {
            // проверяем, столкнулась ли машина с каким-то объектом
            if (roadObject.isCollision(playerCar)) {
                return true;
            }
        }
        return false;
    }

    // генерация новых машин
    private void generateRegularCar(Game game) {
        // новые машины будут создаваться с вероятностью в 30%
        if (game.getRandomNumber(100) < 30) {
            // тип машины будет случайно выбран из первых четырех элементов RoadObjectType
            int carTypeNumber = game.getRandomNumber(4);
            addRoadObject(RoadObjectType.values()[carTypeNumber], game);
        }
    }

    // генерация "пьяных" машин
    private void generateMovingCar(Game game) {
        if (game.getRandomNumber(100) < 10 && !isMovingCarExists()) {
            addRoadObject(RoadObjectType.DRUNK_CAR, game);
        }
    }

    // будет проверять, есть ли достаточно свободного места на дороге для размещения новой машины
    private boolean isRoadSpaceFree(RoadObject object) {
        for (RoadObject item : items) {
            // возвращает false, если расстояние между объектами по горизонтали и вертикали больше переданной дистанции
            if (item.isCollisionWithDistance(object, PLAYER_CAR_DISTANCE)) {
                return false;
            }
        }
        return true;
    }

    // проверяет, существует ли на трассе "пьяная" машина
    private boolean isMovingCarExists() {
        for (RoadObject item : items) {
            if (item instanceof MovingCar) {
                return true;
            }
        }
        return false;
    }
}
