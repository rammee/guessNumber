package com.ramilizmailov.guessnumber.core;

import com.ramilizmailov.guessnumber.controller.GameControllerFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by RAMSES on 23.02.2016.
 */
public class GameCoreTest {

    private GameCore gameCore;

    @Before
    public void setUp() {
        GameControllerFactory controllerFactory = new GameControllerFactory(false);
        gameCore = new GameCore(controllerFactory);
    }

    @Test
    public void wrongAnswer_shouldNotIncreaseLevel_shouldIncreaseTrials() {
        int theNumber = gameCore.getTheNumber();
        int expectedLevel = 1;
        int expectedTrials = gameCore.getCurrentLevelTrials() + 1;
        int expectedTotalTrials = gameCore.getTotalTrials();

        boolean isGameOver = gameCore.processInput(String.valueOf(theNumber + 1));
        int level = gameCore.getLevel();
        int trials = gameCore.getCurrentLevelTrials();
        int totalTrials = gameCore.getTotalTrials();

        assertEquals(level, expectedLevel);
        assertEquals(trials, expectedTrials);
        assertEquals(totalTrials, expectedTotalTrials);
        assertFalse(isGameOver);

    }

    @Test
    public void invalidAnswer_shouldNotIncreaseLevel_shouldNotIncreaseTrials() {
        int expectedLevel = 1;
        int expectedTrials = gameCore.getCurrentLevelTrials();
        int expectedTotalTrials = gameCore.getTotalTrials();

        boolean isGameOver = gameCore.processInput(String.valueOf("doohickey"));
        int level = gameCore.getLevel();
        int trials = gameCore.getCurrentLevelTrials();
        int totalTrials = gameCore.getTotalTrials();

        assertEquals(level, expectedLevel);
        assertEquals(trials, expectedTrials);
        assertEquals(totalTrials, expectedTotalTrials);
        assertFalse(isGameOver);
    }


    @Test
    public void rightAnswers_gameFlowTest() {
        int theNumber = gameCore.getTheNumber();
        int expectedTotalTrials = gameCore.getTotalTrials() + 1;

        for (int expectedLevel = 1; expectedLevel <= GameCore.MAX_LEVEL; expectedLevel++) {
            int level = gameCore.getLevel();
            assertEquals(level, expectedLevel);
            boolean isGameOver = gameCore.processInput(String.valueOf(theNumber));
            theNumber = gameCore.getTheNumber();
            assertEquals(isGameOver, level == GameCore.MAX_LEVEL);
            assertEquals(gameCore.getTotalTrials(), expectedTotalTrials++);
        }
    }
}
