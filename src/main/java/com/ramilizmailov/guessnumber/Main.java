package com.ramilizmailov.guessnumber;

import com.ramilizmailov.guessnumber.controller.*;
import com.ramilizmailov.guessnumber.datastorage.BasicPlayerDAO;
import com.ramilizmailov.guessnumber.datastorage.PlayerDAO;
import com.ramilizmailov.guessnumber.model.GameModel;
import com.ramilizmailov.guessnumber.model.levels.LevelFactory;
import com.ramilizmailov.guessnumber.model.levels.PowerOfTenLevelFactory;
import com.ramilizmailov.guessnumber.view.ConsoleGameView;
import com.ramilizmailov.guessnumber.view.DialogGameView;
import com.ramilizmailov.guessnumber.view.GameView;

public class Main {
    /**
     * Use "console" param to play via console. If no param specified then a gui version will be run.
     * @param args
     */
    public static void main(String[] args) {
        String controllerTypeName = args.length > 0 ? args[0].toUpperCase() : "";
        ControllerType type;
        try {
            type = ControllerType.valueOf(controllerTypeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            type = ControllerType.GUI;
        }
        int maxLevel = 3;
        LevelFactory levelFactory = new PowerOfTenLevelFactory(maxLevel);
        GameControllerFactory factory = new GameControllerFactory();
        PlayerDAO playerDAO = new BasicPlayerDAO();
        GameController gameController = factory.createGameController(type, levelFactory, playerDAO);
        gameController.runGame();
    }
}
