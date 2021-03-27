package com.javarush.games.racer;

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

        if (direction == Direction.LEFT) {
            x -= 1;
        }
        if (direction == Direction.RIGHT) {
            x += 1;
        }
    }
}
