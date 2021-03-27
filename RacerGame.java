package com.javarush.games.racer;

import com.javarush.engine.cell.*;

/**
 * Главный класс.
 */
public class RacerGame extends Game {

    // ширина
    public static final int WIDTH = 64;
    // высота
    public static final int HEIGHT = 64;
    // разделительная полоса
    public static final int CENTER_X = WIDTH / 2;
    // обочина
    public static final int ROADSIDE_WIDTH = 14;
    // дорожная разметка
    private RoadMarking roadMarking;

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
        roadMarking = new RoadMarking();
        drawScene();
    }

    // отрисовка всех игровых объектов
    private void drawScene() {
        drawField();
        roadMarking.draw(this);
    }

    // отрисовка фона игрового поля
    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x == CENTER_X) {
                    // устанавливает цвет ячейки
                    setCellColor(x, y, Color.WHITE);
                } else if (x >= ROADSIDE_WIDTH && x < (WIDTH - ROADSIDE_WIDTH)) {
                    setCellColor(x, y, Color.DIMGREY);
                } else {
                    setCellColor(x, y, Color.GREEN);
                }
            }
        }
    }

    // для того, чтобы избежать исключений вызова метода с координатами, лежащими за пределами игрового поля
    // переопределил метод
    // теперь он будет вызываться только в случае, если координаты находятся внутри игрового поля
    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x < WIDTH && x >= 0 && y < HEIGHT && y >= 0) {
            super.setCellColor(x, y, color);
        }
    }
}
