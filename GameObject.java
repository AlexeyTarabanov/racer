package com.javarush.games.racer;

import com.javarush.engine.cell.*;

/**
 * Будет представлять игровые объекты.
 */
public class GameObject {

    // координаты верхнего левого угла объекта на игровом поле
    // координаты положения на игровом поле
    public int x;
    public int y;
    // матрица отображения игрового объекта
    // значения матрицы — порядковые номера цветов в Color
    public int[][] matrix;
    // высота и ширина
    // габаритные размеры объекта
    public int width;
    public int height;

    public GameObject(int x, int y, int[][] matrix) {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }

    // рисует объект
    public void draw(Game game) {
        // устанавливает цвет ячейки
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // цвет
                int colorIndex = matrix[i][j];
                game.setCellColor(x + j, y + i, Color.values()[colorIndex]);
            }
        }
    }
}
