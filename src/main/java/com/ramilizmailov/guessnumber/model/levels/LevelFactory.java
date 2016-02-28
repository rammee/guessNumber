package com.ramilizmailov.guessnumber.model.levels;

import com.ramilizmailov.guessnumber.model.levels.Level;

/**
 * Created by RAMSES on 27.02.2016.
 */
public abstract class LevelFactory {

    protected int nextLevel = 1;
    protected int maxLevel;

    public LevelFactory(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public abstract Level nextLevel();

    public boolean hasMoreLevels() {
        return nextLevel <= maxLevel;
    }
}
