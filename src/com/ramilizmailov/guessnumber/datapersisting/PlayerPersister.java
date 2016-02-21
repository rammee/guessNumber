package com.ramilizmailov.guessnumber.datapersisting;

import java.util.List;

import com.ramilizmailov.guessnumber.PlayerData;

public interface PlayerPersister {
	void savePlayerData(PlayerData player);
	List<PlayerData> getPlayersData();
}
