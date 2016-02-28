package com.ramilizmailov.guessnumber.model.levels;

import com.ramilizmailov.guessnumber.model.levels.Level;
import com.ramilizmailov.guessnumber.model.levels.LevelFactory;

import java.util.Random;

/**
 * Created by RAMSES on 27.02.2016.
 */
public class PowerOfTenLevelFactory extends LevelFactory {

    private Random random = new Random();

    public PowerOfTenLevelFactory(int maxLevel) {
        super(maxLevel);
    }

    private Level createLevel(int level) {
        int minNumber = 0;
        int maxNumber = (int)Math.pow(10, level) - 1;
        int numberToGuess = random.nextInt(maxNumber + 1);
        return new Level(level, minNumber, maxNumber, numberToGuess);
    }

    @Override
    public Level nextLevel() {
        return createLevel(nextLevel++);
    }
}
