package com.ramilizmailov.guessnumber.datapersisting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ramilizmailov.guessnumber.PlayerData;

public class TextPlayerDAO implements PlayerDAO {

	@Override
	public void savePlayerData(PlayerData player) {
		List<PlayerData> ratingList = getRatingList();
		boolean newPlayer = true;
		for (PlayerData pr : ratingList) {
			if (pr.getName().equalsIgnoreCase(player.getName())) {
				if (player.getTrials() < pr.getTrials()) {
					pr.setTrials(player.getTrials());
					System.out.println("Новый рекорд!");
				}
				newPlayer = false;
				break;
			}
		}
		
		if (newPlayer) {
			ratingList.add(player);
		}
		File resFile = new File("results.txt");
		try (Writer writer = new FileWriter(resFile, false)) {
			if (!resFile.exists()) {
				resFile.createNewFile();
			}
			for (PlayerData pr : ratingList) {
				writer.write(pr.getName() + ":::" + pr.getTrials() + "\n");
			}
		} catch (IOException e) {
			System.out.println("Ошибка при сохранении результата");
			e.printStackTrace();
		}
		
	}

	@Override
	public List<PlayerData> getPlayersData() {
		List<PlayerData> res = getRatingList();
		Collections.sort(res);
		return res;
		
	}
	
	private List<PlayerData> getRatingList() {
		List<PlayerData> ratingList = new ArrayList<>();
		File resFile = new File("results.txt");
		if (!resFile.exists())
			return ratingList;
		try (BufferedReader reader = new BufferedReader(new  FileReader(resFile))) {
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				String[] arr = line.split(":::"); 
				ratingList.add(new PlayerData(arr[0], Integer.parseInt(arr[1])));
			}
		} catch (IOException e) {
			System.out.println("Ошибка при печати результатов");
			e.printStackTrace();
			return Collections.emptyList();
		}
		return ratingList;
	}

}
