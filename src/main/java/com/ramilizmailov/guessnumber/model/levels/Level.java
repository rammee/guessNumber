package com.ramilizmailov.guessnumber.model.levels;

/**
 * Created by RAMSES on 27.02.2016.
 */
public class Level {
    private int level;
    private int minNumber;
    private int maxNumber;
    private int numberToGuess;

    public Level(int level, int minNumber, int maxNumber, int numberToGuess) {
        this.level = level;
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.numberToGuess = numberToGuess;
    }

    public int getNumberToGuess() {
        return numberToGuess;
    }

    public int getMinNumber() {
        return minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getLevel() {
        return level;
    }
}
