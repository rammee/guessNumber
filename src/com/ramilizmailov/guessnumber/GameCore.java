package com.ramilizmailov.guessnumber;

import java.util.Random;

import com.ramilizmailov.guessnumber.datapersisting.ObjectPlayerDAO;
import com.ramilizmailov.guessnumber.datapersisting.PlayerDAO;
import com.ramilizmailov.guessnumber.gui.MainDialog;

public class GameCore {

	private static final int MAX_LEVEL = 2;

	private Random rnd;
	private int level;
	private int maxNumber;
	private int theNumber;
	private int trials;
	private PlayerDAO playerDAO;
	private GameIO gameIO;

	public GameCore(boolean isGui) {
		rnd = new Random();
		level = 1;
		maxNumber = (int) Math.pow(10, level);
		theNumber = rnd.nextInt(maxNumber);
		trials = 0;
		playerDAO = new ObjectPlayerDAO();
		gameIO = isGui ? new MainDialog(this) : new ConsoleGameIO(this);
	}

	/**
	 * Starts next level.
	 * @return true if game is over
     */
	private boolean nextLevel() {
		if (++level > MAX_LEVEL) {
			gameIO.printMessage(String.format("Использовано попыток: %s\nВаше имя:", trials));
			return true;
		}
		maxNumber = (int) Math.pow(10, level);
		theNumber = rnd.nextInt(maxNumber);
		start();
		return false;
	}

	public void start() {
		gameIO.printMessage("Уровень " + level + ". Угадай число от 0 до " + (maxNumber - 1));
		gameIO.start();
	}

	public boolean processInput(String inputString) {
		try {
			trials++;
				int input = Integer.parseInt(inputString);

				if (theNumber > input) {
					gameIO.printMessage("Больше!");
				} else if (theNumber < input) {
					gameIO.printMessage("Меньше!");
				} else {
					gameIO.printMessage("Угадал!");
					return nextLevel();
				}
			return false;
		} catch (NumberFormatException e) {
			gameIO.printMessage("Введите число!");
			trials--;
			return false;
		} catch (Exception e){
			gameIO.terminate();
			throw e;
		}
	}

	public void saveResultsAndShowResultsTable(String playerName) {
		playerDAO.savePlayerData(new PlayerData(playerName, trials));
		gameIO.printMessage(getPlayerDataString());
	}

	private String getPlayerDataString() {
		StringBuilder sb = new StringBuilder();
		for (PlayerData pr : playerDAO.getPlayersData()) {
			sb.append(String.format("%15s %s\n", pr.getName(), pr.getTrials()));
		}
		return sb.toString();
	}
}
