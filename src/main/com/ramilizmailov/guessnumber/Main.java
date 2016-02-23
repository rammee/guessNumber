package com.ramilizmailov.guessnumber;

import com.ramilizmailov.guessnumber.controller.GameControllerFactory;
import com.ramilizmailov.guessnumber.core.GameCore;

public class Main {
    /**
     * Use "gui" param to run game with gui. If no param specified then a console version will be run.
     * @param args
     */
    public static void main(String[] args) {
        boolean useGui = (args.length > 0 && args[0].equalsIgnoreCase("gui"));
        GameControllerFactory controllerFactory = new GameControllerFactory(useGui);
        GameCore gc = new GameCore(controllerFactory);
        gc.start();
    }
}
