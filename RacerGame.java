package com.javarush.games.racer;

import com.javarush.engine.cell.*;

/** Главный класс.*/
public class RacerGame extends Game {

    // ширина
    public static final int WIDTH = 64;
    // высота
    public static final int HEIGHT = 64;
    // разделительная полоса
    public static final int CENTER_X = WIDTH / 2;
    // обочина
    public static final int ROADSIDE_WIDTH = 14;

    // установка начального состояния игры (точка входа)
    @Override
    public void initialize() {
        // задает размер игрового поля
        setScreenSize(WIDTH, HEIGHT);
        // отображение сетки игрового поля
        showGrid(false);
        createGame();
    }

    // для старта новой игры
    // здесь будем создавать все эл-ты игры
    private void createGame() {
        drawScene();
    }

    // отрисовка всех игровых объектов
    private void drawScene() {
        drawField();
    }

    // отрисовка фона игрового поля
    private void drawField() {

    }

}
