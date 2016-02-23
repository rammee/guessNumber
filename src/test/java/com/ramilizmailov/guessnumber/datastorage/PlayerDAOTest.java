package com.ramilizmailov.guessnumber.datastorage;

import com.ramilizmailov.guessnumber.model.PlayerData;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

public class PlayerDAOTest {
    private static final String TEST_RESULT_FILE_NAME = "testResults.tmp";
    private File testFile = new File(TEST_RESULT_FILE_NAME);
    private PlayerDAO playerDAO = new PlayerDAO(TEST_RESULT_FILE_NAME);

    @After
    public void deleteTestFile() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void saveAndReadPlayer() {
        PlayerData playerData = new PlayerData("John Doe", 17);
        playerDAO.savePlayerData(playerData);
        List<PlayerData> playerDataList = playerDAO.getPlayersDataSorted();
        assertNotNull(playerDataList);
        assertEquals(playerDataList.size(), 1);
        PlayerData restoredPlayerData = playerDataList.get(0);
        assertEquals(playerData.getName(), restoredPlayerData.getName());
        assertEquals(playerData.getTrials(), restoredPlayerData.getTrials());
    }

    @Test
    public void shouldNotFailIfNoFile_shouldReturnEmptyList() {
        List<PlayerData> playerDataList = playerDAO.getPlayersDataSorted();
        assertEquals(playerDataList.size(), 0);
    }

    @Test
    public void shouldRefreshPlayerDataIfImproved() {
        PlayerData playerData = new PlayerData("John Doe", 17);
        playerDAO.savePlayerData(playerData);
        PlayerData newPlayerData = new PlayerData("JOHN DOE", 1);
        playerDAO.savePlayerData(newPlayerData);

        List<PlayerData> playerDataList = playerDAO.getPlayersDataSorted();
        assertEquals(playerDataList.size(), 1);
        PlayerData restoredPlayerData = playerDataList.get(0);
        assertEquals(restoredPlayerData, newPlayerData);
    }

    @Test
    public void shouldSaveNewPlayerToExistingFile_dataShouldBeSorted() {
        PlayerData playerData1 = new PlayerData("John Doe", 17);
        playerDAO.savePlayerData(playerData1);
        PlayerData playerData2 = new PlayerData("Frank", 1);
        playerDAO.savePlayerData(playerData2);
        PlayerData playerData3 = new PlayerData("Bob", 4);
        playerDAO.savePlayerData(playerData3);

        List<PlayerData> playerDataList = playerDAO.getPlayersDataSorted();
        assertEquals(playerDataList.size(), 3);
        assertEquals(playerDataList.get(0), playerData2);
        assertEquals(playerDataList.get(1), playerData3);
        assertEquals(playerDataList.get(2), playerData1);

    }
}
