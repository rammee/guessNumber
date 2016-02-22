package com.ramilizmailov.guessnumber.datapersisting;

import java.util.List;

import com.ramilizmailov.guessnumber.PlayerData;

public interface PlayerDAO {
	void savePlayerData(PlayerData player);
	List<PlayerData> getPlayersData();
}
