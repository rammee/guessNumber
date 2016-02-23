package com.ramilizmailov.guessnumber.controller;

import com.ramilizmailov.guessnumber.core.GameCore;

/**
 * Created by RAMSES on 23.02.2016.
 */
public class GameControllerFactory {
    private boolean isGui;

    public GameControllerFactory(boolean isGui) {
        this.isGui = isGui;
    }

    public GameController createGameController(GameCore gameCore) {
        return isGui ? new DialogGameController(gameCore) : new ConsoleGameController(gameCore);
    }
}
