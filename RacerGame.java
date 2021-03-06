package com.javarush.games.racer;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.racer.road.RoadManager;

/**
 * Главный класс.
 *
 * На будущее:
 * - сделать возможным выезд на обочину;
 * - добавить повороты трассы;
 * - добавить возможность переключаться на разные скорости;
 * - поменять систему начисления очков: например, давать очки только за ускорение;
 * - добавить игроку возможность стрелять по другим машинам;
 * - добавить больше разнообразных машин и препятствий;
 * - в случае победы — переход на следующие уровни;
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
    // количество машин препятствий
    private static final int RACE_GOAL_CARS_COUNT = 40;
    // дорожная разметка
    private RoadMarking roadMarking;
    // машина игрока
    private PlayerCar player;
    // препятствия на дороге
    private RoadManager roadManager;
    // финишная прямая
    private FinishLine finishLine;
    // панель прогресса в прохождении игры
    private ProgressBar progressBar;
    // счет игры
    private int score;
    // хранит текущее состояние игры
    private boolean isGameStopped;

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
        isGameStopped = false;
        finishLine = new FinishLine();
        progressBar = new ProgressBar(RACE_GOAL_CARS_COUNT);
        score = 3500;
        drawScene();
        setTurnTimer(40);
    }

    // отрисовка всех игровых объектов
    private void drawScene() {
        drawField();
        roadMarking.draw(this);
        player.draw(this);
        roadManager.draw(this);
        finishLine.draw(this);
        progressBar.draw(this);
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
        // "запускаем" финишную линию
        finishLine.move(player.speed);
        // "запускаем" линию прогресса
        progressBar.move(roadManager.getPassedCarsCount());
    }

    // все, что будет происходить на каждом шаге, выполняется в этом методе
    @Override
    public void onTurn(int step) {
        // проверяем пересечение игрока с препятствиями
        if (roadManager.checkCrush(player)) {
            gameOver();
            drawScene();
            return;
        }
        if (finishLine.isCrossed(player)) {
            win();
            drawScene();
            return;
        }
        // вызываем финишную черту
        if (roadManager.getPassedCarsCount() >= RACE_GOAL_CARS_COUNT) {
            finishLine.show();
        }
        // на каждом такте игры (40мс) количество очков уменьшается на 5, пока игрок не финиширует
        setScore(score -= 5);
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
        } else if (key == Key.LEFT) {
            player.setDirection(Direction.LEFT);
        } else if (key == Key.SPACE && isGameStopped) {
            // перезапуск игры
            createGame();
        } else if (key == Key.UP) {
            // увеличивает скорость
            player.speed = 2;
        }
    }

    // поведение машины при отпускании клавиш
    @Override
    public void onKeyReleased(Key key) {
        // возвращает скорости начальное значение
        if (key == Key.UP) {
            player.speed = 1;
        } else if ((key == Key.RIGHT && player.getDirection() == Direction.RIGHT)
                || (key == Key.LEFT && player.getDirection() == Direction.LEFT)) {
            player.setDirection(Direction.NONE);
        }
    }

    // все действия, которые осуществляются при проигрыше
    private void gameOver() {
        isGameStopped = true;
        // выводим сообщение на экран
        showMessageDialog(Color.BLACK, "Game Over", Color.RED, 75);
        // останавливаем таймер
        stopTurnTimer();
        // изображение взрыва
        player.stop();
    }

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "Ты выиграл!", Color.GREEN, 75);
        stopTurnTimer();
    }
}

