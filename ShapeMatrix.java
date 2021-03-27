package com.javarush.games.racer;

/**
 * Класс хранит числовые матрицы изображения игровых объектов.
 * Числа в матрице означают порядковый номер цвета в enum Color.
 * Благодаря числовой матрице изображения, объект будет знать, в какой цвет раскрасить каждую координату.
 */
public class ShapeMatrix {
    public static final int[][] ROAD_MARKING = new int[][]{

            // для прерывистых полос дорожной разметки
            {2},
            {2},
            {2},
            {2},
    };
}