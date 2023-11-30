package ru.moysklad.remap_1_2.utils;

public class MathUtils {

    public MathUtils() {
    }

    public static double round4(double value) {
        return (double)Math.round(value * 10000.0) / 10000.0;
    }
}
