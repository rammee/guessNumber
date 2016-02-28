package com.ramilizmailov.guessnumber.model;

import com.ramilizmailov.guessnumber.controller.ControllerType;
import com.ramilizmailov.guessnumber.controller.GameController;
import com.ramilizmailov.guessnumber.controller.GameControllerFactory;
import com.ramilizmailov.guessnumber.datastorage.BasicPlayerDAO;
import com.ramilizmailov.guessnumber.datastorage.PlayerDAO;
import com.ramilizmailov.guessnumber.model.levels.LevelFactory;
import com.ramilizmailov.guessnumber.model.levels.PowerOfTenLevelFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by RAMSES on 23.02.2016.
 */
public class GameModelTest {

    private GameModel gameModel;
    private final int MAX_LEVEL = 3;

    @Before
    public void beforeTests() {
        LevelFactory levelFactory = new PowerOfTenLevelFactory(MAX_LEVEL);
        GameControllerFactory factory = new GameControllerFactory();
        PlayerDAO playerDAO = new BasicPlayerDAO();
        GameController gameController = factory.createGameController(ControllerType.CONSOLE, levelFactory, playerDAO);
        gameModel = gameController.getGameModel();
        gameController.runGame();
    }

    @Test(timeout = 5000)
    public void wrongAnswer_shouldNotIncreaseLevel_shouldIncreaseTrials() {
        int numberToGuess = gameModel.getCurrentLevel().getNumberToGuess();
        int expectedLevel = 1;
        int expectedTrials = gameModel.getCurrentLevelEfforts() + 1;
        int expectedTotalTrials = gameModel.getTotalEfforts();

        gameModel.processPlayerInput(String.valueOf(numberToGuess + 1));
        int level = gameModel.getCurrentLevel().getLevel();
        int trials = gameModel.getCurrentLevelEfforts();
        int totalTrials = gameModel.getTotalEfforts();

        assertEquals(level, expectedLevel);
        assertEquals(trials, expectedTrials);
        assertEquals(totalTrials, expectedTotalTrials);

    }

    @Test(timeout = 5000)
    public void invalidAnswer_shouldNotIncreaseLevel_shouldNotIncreaseTrials() {
        int expectedLevel = 1;
        int expectedTrials = gameModel.getCurrentLevelEfforts();
        int expectedTotalTrials = gameModel.getTotalEfforts();

        gameModel.processPlayerInput(String.valueOf("doohickey"));
        int level = gameModel.getCurrentLevel().getLevel();
        int trials = gameModel.getCurrentLevelEfforts();
        int totalTrials = gameModel.getTotalEfforts();

        assertEquals(level, expectedLevel);
        assertEquals(trials, expectedTrials);
        assertEquals(totalTrials, expectedTotalTrials);
    }


    @Test(timeout = 5000)
    public void rightAnswers_gameFlowTest() {
        int theNumber = gameModel.getCurrentLevel().getNumberToGuess();
        int expectedTotalTrials = gameModel.getTotalEfforts() + 1;

        for (int expectedLevel = 1; expectedLevel <= MAX_LEVEL; expectedLevel++) {
            int level = gameModel.getCurrentLevel().getLevel();
            assertEquals(level, expectedLevel);
            gameModel.processPlayerInput(String.valueOf(theNumber));
            theNumber = gameModel.getCurrentLevel().getNumberToGuess();
            assertEquals(gameModel.getTotalEfforts(), expectedTotalTrials++);
        }
        //gameModel.getGameController().disposeView();
    }
}
