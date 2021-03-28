package com.javarush.games.racer;

import com.javarush.games.racer.road.RoadManager;

/**
 * Машина игрока.
 */
public class PlayerCar extends GameObject {

    // высота автомобиля игрока
    private static int playerCarHeight = ShapeMatrix.PLAYER.length;
    // скорость
    public int speed = 1;
    // направление движения
    private Direction direction;

    public PlayerCar() {
        super(RacerGame.WIDTH / 2 + 2, RacerGame.HEIGHT - playerCarHeight - 1, ShapeMatrix.PLAYER);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // в зависимости от направления движения будет менять координаты машины по горизонтали
    public void move() {
        if (x < RoadManager.LEFT_BORDER) {
            x = RoadManager.LEFT_BORDER;
        } else if (x > RoadManager.RIGHT_BORDER - width) {
            x = RoadManager.RIGHT_BORDER - width;
        }

        if (direction == Direction.LEFT) {
            x--;
        } else if (direction == Direction.RIGHT) {
            x++;
        }
    }

    // меняем визуализацию машины
    public void stop() {
        matrix = ShapeMatrix.PLAYER_DEAD;
    }
}
