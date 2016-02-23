package com.ramilizmailov.guessnumber.core;

import java.util.Random;

import com.ramilizmailov.guessnumber.controller.GameControllerFactory;
import com.ramilizmailov.guessnumber.datastorage.PlayerDAO;
import com.ramilizmailov.guessnumber.controller.GameController;
import com.ramilizmailov.guessnumber.model.PlayerData;

public class GameCore {

	static final int MAX_LEVEL = 3;

	private Random rnd;
	private int level;
	private int maxNumber;
	/** the number to guess */
	private int theNumber;
	/** overall number of totalTrials made during game. eventually saved as final score */
	private int totalTrials;
	/** number of totalTrials which player made during current level*/
	private int currentLevelTrials;
	private PlayerDAO playerDAO;
	private GameController gameController;

	public GameCore(GameControllerFactory controllerFactory) {
		rnd = new Random();
		playerDAO = new PlayerDAO();
		gameController = controllerFactory.createGameController(this);
		init();
	}

	private void init() {
		level = 1;
		maxNumber = (int) Math.pow(10, level);
		theNumber = rnd.nextInt(maxNumber);
		totalTrials = 0;
		currentLevelTrials = 0;
	}

	/**
	 * Starts next level.
	 * @return true if game is over
     */
	private boolean nextLevel() {
		gameController.printMessage("Trials made: " + currentLevelTrials);
		totalTrials += currentLevelTrials;
		currentLevelTrials = 0;
		if (++level > MAX_LEVEL) {
			gameController.printMessage("Total trials: " + totalTrials);
			gameController.printMessage("Your name:");
			return true;
		}

		maxNumber = (int) Math.pow(10, level);
		theNumber = rnd.nextInt(maxNumber);
		start();
		return false;
	}

	public void start() {
		gameController.printMessage("Level " + level + ". Guess number from 0 to " + (maxNumber - 1));
		gameController.start();
	}

	/**
	 * Processes player's input
	 * @param inputString
	 * @return true if the game is over;
     */
	public boolean processInput(String inputString) {
		try {
			currentLevelTrials++;
				int input = Integer.parseInt(inputString);

				if (theNumber > input) {
					gameController.printMessage("Greater!");
				} else if (theNumber < input) {
					gameController.printMessage("Less!");
				} else {
					gameController.printMessage("Bingo!");
					return nextLevel();
				}
			return false;
		} catch (NumberFormatException e) {
			gameController.printMessage("Please, provide a number!");
			currentLevelTrials--;
			return false;
		} catch (Exception e){
			gameController.terminate();
			throw e;
		}
	}

	public void saveResultsAndShowResultsTable(String playerName) {
		playerDAO.savePlayerData(new PlayerData(playerName, totalTrials));
		gameController.displayResultsTable(getPlayerDataString());
	}

	private String getPlayerDataString() {
		StringBuilder sb = new StringBuilder("Score table:\n");
		for (PlayerData pr : playerDAO.getPlayersDataSorted()) {
			sb.append(String.format("%15s %s\n", pr.getName(), pr.getTrials()));
		}
		return sb.toString();
	}

	int getLevel() {
		return level;
	}

	int getTotalTrials() {
		return totalTrials;
	}

	int getCurrentLevelTrials() {
		return currentLevelTrials;
	}

	int getTheNumber() {
		return theNumber;
	}
}
