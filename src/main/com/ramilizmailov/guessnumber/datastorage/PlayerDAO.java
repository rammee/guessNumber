package com.ramilizmailov.guessnumber.datastorage;

import com.ramilizmailov.guessnumber.model.PlayerData;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by RAMSES on 23.02.2016.
 */
public class PlayerDAO  {

	public static final String RESULTS_FILE_NAME = "guessNumberResults";
	private String fileName;

	public PlayerDAO() {
		this.fileName = RESULTS_FILE_NAME;
	}
	public PlayerDAO(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * Saves or refreshes player data.
	 * @param player
	 * @return true if new record achieved, false otherwise
     */
	public void savePlayerData(PlayerData player) {
		List<PlayerData> ratingList = getPlayerDataList();
		boolean newPlayer = true;
		for (PlayerData pr : ratingList) {
			if (pr.getName().equalsIgnoreCase(player.getName())) {
				if (player.getTrials() < pr.getTrials()) {
					pr.setTrials(player.getTrials());
				}
				newPlayer = false;
				break;
			}
		}

		if (newPlayer) {
			ratingList.add(player);
		}
		File resFile = new File(fileName);
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(resFile, false))) {
			if (!resFile.exists()) {
				resFile.createNewFile();
			}
			for (PlayerData pr : ratingList) {
				dos.writeUTF(pr.getName());
				dos.writeInt(pr.getTrials());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return all saved players' data
     */
	public List<PlayerData> getPlayersDataSorted() {
		List<PlayerData> res = getPlayerDataList();
		Collections.sort(res);
		return res;

	}

	private List<PlayerData> getPlayerDataList() {
		List<PlayerData> ratingList = new ArrayList<>();
		File resFile = new File(fileName);
		if (!resFile.exists())
			return ratingList;
		try (DataInputStream dis = new DataInputStream(new FileInputStream(resFile))) {
			while (dis.available() > 0) {
				String playerName = dis.readUTF();
				int trials = dis.readInt();
				ratingList.add(new PlayerData(playerName, trials));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		return ratingList;
	}

}
