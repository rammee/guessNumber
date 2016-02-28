package com.ramilizmailov.guessnumber.datastorage;

import com.ramilizmailov.guessnumber.model.players.PlayerData;

import java.util.List;

/**
 * Created by RAMSES on 28.02.2016.
 */
public interface PlayerDAO {
	void savePlayerData(PlayerData player);

	List<PlayerData> getPlayersDataSorted();
}
