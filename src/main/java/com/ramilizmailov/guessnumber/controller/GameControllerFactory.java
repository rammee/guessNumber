package com.ramilizmailov.guessnumber.controller;

import com.ramilizmailov.guessnumber.datastorage.PlayerDAO;
import com.ramilizmailov.guessnumber.model.GameModel;
import com.ramilizmailov.guessnumber.model.levels.LevelFactory;
import com.ramilizmailov.guessnumber.view.ConsoleGameView;
import com.ramilizmailov.guessnumber.view.DialogGameView;

/**
 * Created by RAMSES on 28.02.2016.
 */
public class GameControllerFactory {

    public GameController createGameController(ControllerType type, LevelFactory levelFactory, PlayerDAO playerDAO) {
        GameModel gameModel = new GameModel(levelFactory, playerDAO);

        switch (type) {
            case GUI:
                return new DialogGameController(gameModel, new DialogGameView(gameModel));
            case CONSOLE:
                return new ConsoleGameController(gameModel, new ConsoleGameView(gameModel));
            default:
                throw new IllegalArgumentException();
        }
    }
}
