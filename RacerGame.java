package com.javarush.games.racer;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.racer.road.RoadManager;

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
    // машина игрока
    private PlayerCar player;
    // препятствия на дороге
    private RoadManager roadManager;

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
        player = new PlayerCar();
        roadManager = new RoadManager();
        drawScene();
        setTurnTimer(40);
    }

    // отрисовка всех игровых объектов
    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        player.draw(this);
        roadManager.draw(this);
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

    // перемещает все подвижные игровые объекты
    private void moveAll() {
        // "запускаем" дорожную разметку со скоростью игрока
        roadMarking.move(player.speed);
        // "запускаем" машину игрока
        player.move();
        // "запускаем" препятсвия на дороге
        roadManager.move(player.speed);
    }

    // все, что будет происходить на каждом шаге, выполняется в этом методе
    @Override
    public void onTurn(int step) {
        moveAll();
        // генерируем препятствия
        roadManager.generateNewRoadObjects(this);
        // рисуем объекты
        drawScene();
    }

    // обрабатывает нажатие клавиш
    @Override
    public void onKeyPress(Key key) {
        if (key == Key.RIGHT) {
            player.setDirection(Direction.RIGHT);
        }
        if (key == Key.LEFT) {
            player.setDirection(Direction.LEFT);
        }
    }

    // поведение машины при отпускании клавиш
    @Override
    public void onKeyReleased(Key key) {
        if (key == Key.RIGHT && player.getDirection() == Direction.RIGHT) {
            player.setDirection(Direction.NONE);
        }
        if (key == Key.LEFT && player.getDirection() == Direction.LEFT) {
            player.setDirection(Direction.NONE);
        }
    }
}

