package com.javarush.games.racer;

/**
 * Финишная линия.
 * Финишная линия появится на трассе в конце игры.
 * На самом деле объект финишной линии создается в начале игры, но находится выше игрового поля, поэтому невидим.
 * Как только флаг, ответственный за отображение FinishLine, получит значение true,
 * финишная линия начнет движение вниз и появится на игровом поле.
 */
public class FinishLine extends GameObject {

    // виден
    private boolean isVisible = false;

    public FinishLine() {
        super(RacerGame.ROADSIDE_WIDTH, -1 * ShapeMatrix.FINISH_LINE.length, ShapeMatrix.FINISH_LINE);
    }

    public void show() {
        isVisible = true;
    }

    public void move(int boost) {
        if (isVisible) {
            y += boost;
        }
    }
}
