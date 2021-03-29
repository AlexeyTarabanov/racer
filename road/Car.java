package com.javarush.games.racer.road;

/**
 * Общий для всех типов машин класс.
 * Все машины будут со скоростью 1
 */
public class Car extends RoadObject {
    public Car(RoadObjectType type, int x, int y) {
        super(type, x, y);
        speed = 1;
    }
}
