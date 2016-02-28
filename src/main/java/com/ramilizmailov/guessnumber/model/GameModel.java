package com.ramilizmailov.guessnumber.model;

import com.ramilizmailov.guessnumber.controller.GameController;
import com.ramilizmailov.guessnumber.datastorage.BasicPlayerDAO;
import com.ramilizmailov.guessnumber.datastorage.PlayerDAO;
import com.ramilizmailov.guessnumber.model.levels.Level;
import com.ramilizmailov.guessnumber.model.levels.LevelFactory;
import com.ramilizmailov.guessnumber.model.players.PlayerData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameModel {
    public static final String MESSAGE_PROPERTY_NAME = "message";
    private int totalEfforts;
    private int currentLevelEfforts;
    private Level currentLevel;
    private LevelFactory levelFactory;
    private String message;
    private boolean isGameOver = false;
    private GameController gameController;

    private PlayerDAO playerDAO;
    private PropertyChangeSupport propertyChangeSupport
            = new PropertyChangeSupport(this);

    public GameModel(LevelFactory levelFactory, PlayerDAO playerDAO) {
        this.levelFactory = levelFactory;
        this.playerDAO = playerDAO;
        init();
    }

    private void init() {
        totalEfforts = 0;
        currentLevelEfforts = 0;
    }

    public void proceedToTheNextLevel() {
        if (currentLevelEfforts > 0) {
            setMessage("Efforts made: " + currentLevelEfforts);
            totalEfforts += currentLevelEfforts;
            currentLevelEfforts = 0;
        }
        if (!levelFactory.hasMoreLevels()) {
            setMessage("Total efforts: " + totalEfforts);
            setGameOver(true);
            if (gameController != null)
                gameController.onGameOver();
        } else {
            currentLevel = levelFactory.nextLevel();
            setMessage(
                    "Level " + currentLevel.getLevel() + ". Guess number from 0 to " + (currentLevel.getMaxNumber()));
        }
    }

    public void processPlayerInput(String inputString) {
        if (isGameOver())
            throw new IllegalStateException("Game is over, cannot process input");
        try {
            currentLevelEfforts++;
            int input = Integer.parseInt(inputString);
            int numberToGuess = currentLevel.getNumberToGuess();

            if (numberToGuess > input) {
                setMessage("Greater!");
            } else if (numberToGuess < input) {
                setMessage("Less!");
            } else {
                setMessage("Bingo!");
                proceedToTheNextLevel();
            }
        } catch (NumberFormatException e) {
            setMessage("Please, provide a number!");
            currentLevelEfforts--;
        }
    }

    public void savePlayerResults(String playerName) {
        playerDAO.savePlayerData(new PlayerData(playerName, totalEfforts));
    }

    public String getSavedScoreTable() {
        StringBuilder sb = new StringBuilder();
        for (PlayerData pr : playerDAO.getPlayersDataSorted()) {
            sb.append(String.format("%15s %s\n", pr.getName(), pr.getEfforts()));
        }
        return sb.toString();
    }

    public void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void setMessage(String message) {
        this.message = message;
        propertyChangeSupport.firePropertyChange(MESSAGE_PROPERTY_NAME, null, message);
    }

    public String getMessage() {
        return message;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    int getTotalEfforts() {
        return totalEfforts;
    }

    int getCurrentLevelEfforts() {
        return currentLevelEfforts;
    }

    Level getCurrentLevel() { return currentLevel; }
}
