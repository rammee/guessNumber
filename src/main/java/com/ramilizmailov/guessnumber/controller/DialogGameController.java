package com.ramilizmailov.guessnumber.controller;

import com.ramilizmailov.guessnumber.model.GameModel;
import com.ramilizmailov.guessnumber.view.DialogGameView;
import com.ramilizmailov.guessnumber.view.GameView;

/**
 * Created by RAMSES on 28.02.2016.
 */
public class DialogGameController extends GameController {

    DialogGameView dialogGameView;
    public DialogGameController(GameModel gameModel, DialogGameView gameView) {
        super(gameModel, gameView);
        this.dialogGameView = gameView;
        gameView.setInputProcessingStrategy(s -> gameModel.processPlayerInput(s));
    }

    @Override
    public void onGameOver() {
        gameView.showMessage("Enter your name: ");
        gameView.setInputProcessingStrategy(s -> {
            gameModel.savePlayerResults(s);
            dialogGameView.showResults("SCORE TABLE:\n" + gameModel.getSavedScoreTable());
            dialogGameView.dispose();
        });
    }
}
