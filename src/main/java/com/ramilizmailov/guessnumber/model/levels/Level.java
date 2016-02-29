package com.ramilizmailov.guessnumber.model.levels;

/**
 * Created by RAMSES on 27.02.2016.
 */
public class Level {
    private int levelNo;
    private int minNumber;
    private int maxNumber;
    private int numberToGuess;

    public Level(int level, int minNumber, int maxNumber, int numberToGuess) {
        this.levelNo = level;
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

    public int getLevelNo() {
        return levelNo;
    }

    public String getDescription() {
        return String.format("Level %s. Guess number from %d to %d", levelNo, minNumber, maxNumber);
    }
}
