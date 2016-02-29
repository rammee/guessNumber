package com.ramilizmailov.guessnumber.controller;

import com.ramilizmailov.guessnumber.model.GameModel;
import com.ramilizmailov.guessnumber.view.DialogGameView;

/**
 * Created by RAMSES on 28.02.2016.
 */
public class DialogGameController extends GameController {

    DialogGameView gameView;
    public DialogGameController(GameModel gameModel, DialogGameView gameView) {
        super(gameModel, gameView);
        this.gameView = gameView;
        gameView.setInputProcessingStrategy(s -> {
            gameView.showMessage(s);
            gameModel.processPlayerInput(s);
        });
    }

    @Override
    public void onGameOver() {
        gameView.getTextPane().setText("");
        gameView.showMessage("Congratulations!\n Enter your name: ");
        gameView.setInputProcessingStrategy(s -> {
            gameModel.savePlayerResults(s);
            gameView.showResults("SCORE TABLE:\n" + gameModel.getSavedScoreTable());
            gameView.dispose();
        });
    }
}
