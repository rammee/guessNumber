package com.ramilizmailov.guessnumber.datapersisting;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ramilizmailov.guessnumber.PlayerData;

public class ObjectPlayerDAO implements PlayerDAO {

	private static final String PLAYER_RATING_FILE_NAME = "resultsObj.txt";

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
		File resFile = new File(PLAYER_RATING_FILE_NAME);
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(resFile, false))) {
			if (!resFile.exists()) {
				resFile.createNewFile();
			}
			for (PlayerData pr : ratingList) {
				os.writeObject(pr);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
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
		File resFile = new File(PLAYER_RATING_FILE_NAME);
		if (!resFile.exists())
			return ratingList;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(resFile))) {
			for (Object obj = ois.readObject();;obj = ois.readObject())
				ratingList.add((PlayerData)obj);
		} catch (EOFException e) {
			return ratingList;
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
