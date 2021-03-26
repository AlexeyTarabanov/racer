package com.javarush.games.racer;

import com.javarush.engine.cell.*;

/** Главный класс.*/
public class RacerGame extends Game {

    // ширина
    public static final int WIDTH = 64;
    // высота
    public static final int HEIGHT = 64;

    // установка начального состояния игры (точка входа)
    @Override
    public void initialize() {
        // задает размер игрового поля
        setScreenSize(WIDTH, HEIGHT);
        // отображение сетки игрового поля
        showGrid(false);
    }
}
