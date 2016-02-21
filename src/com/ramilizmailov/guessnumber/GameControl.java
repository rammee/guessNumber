package com.ramilizmailov.guessnumber;

import java.util.Random;
import java.util.Scanner;

import com.ramilizmailov.guessnumber.datapersisting.ObjectPlayerPersister;
import com.ramilizmailov.guessnumber.datapersisting.PlayerPersister;

public class GameControl {

	private static final int MAX_LEVEL = 1;

	private Random rnd = new Random();
	private int level = 1;
	private int maxNumber = (int) Math.pow(10, level);
	private int theNumber = rnd.nextInt(maxNumber);
	private int trials = 0;
	private Scanner scanner = new Scanner(System.in);
	private PlayerPersister persister = new ObjectPlayerPersister();

	private void nextLevel() {
		if (++level > MAX_LEVEL) {
			System.out.println(String.format("Использовано попыток: %s\nВаше имя:", trials));
			String playerName = scanner.next();
			persister.savePlayerData(new PlayerData(playerName, trials));
			printResultsTable();
			return;

		}
		maxNumber = (int) Math.pow(10, level);
		theNumber = rnd.nextInt(maxNumber);
		start();
	}

	public void start() {
		System.out.println("Уровень " + level);
		System.out.println("Угадай число от 0 до " + (maxNumber - 1));
		boolean rightAnswer = false;		
		while (!rightAnswer) {
			
			trials++;
			
			int input = Integer.parseInt(scanner.next());
			String message;
			
			if (theNumber > input) {
				message = "Больше!";
			} else if (theNumber < input) {
				message = "Меньше!";
			} else {
				message = "Угадал!";
				rightAnswer = true;
			}
			
			System.out.println(message);
		}
		nextLevel();
	}

	private void printResultsTable() {
		for (PlayerData pr : persister.getPlayersData()) {
			System.out.println(String.format("%15s %s", pr.getName(), pr.getTrials()));
		}

	}
	
}
