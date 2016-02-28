package com.ramilizmailov.guessnumber.controller;

import com.ramilizmailov.guessnumber.model.GameModel;
import com.ramilizmailov.guessnumber.view.GameView;

public class ConsoleGameController extends GameController {

    public ConsoleGameController(GameModel gameModel, GameView gameView) {
        super(gameModel, gameView);
        gameView.setInputProcessingStrategy(s -> gameModel.processPlayerInput(s));
    }

    @Override
    public void onGameOver() {
        gameView.showMessage("Enter your name: ");
        gameView.setInputProcessingStrategy(s -> {
            gameModel.savePlayerResults(s);
            gameView.showMessage("\n\nSCORE TABLE: \n");
            gameView.showMessage(gameModel.getSavedScoreTable());
            gameView.dispose();
        });
    }
}
