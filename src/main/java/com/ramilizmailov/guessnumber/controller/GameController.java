package com.ramilizmailov.guessnumber.controller;

import com.ramilizmailov.guessnumber.model.GameModel;
import com.ramilizmailov.guessnumber.view.GameView;

public abstract class GameController {

    protected GameModel gameModel;
    protected GameView gameView;

    public GameController(GameModel gameModel, GameView gameView) {
        this.gameView = gameView;
        this.gameModel = gameModel;
        gameModel.setGameController(this);
        gameModel.addListener(gameView);
    }

    public void runGame() {
        gameModel.proceedToTheNextLevel();
        gameView.display();
    }

    public abstract void onGameOver();

    public GameModel getGameModel() {
        return gameModel;
    }

    public GameView getGameView() {
        return gameView;
    }
}
