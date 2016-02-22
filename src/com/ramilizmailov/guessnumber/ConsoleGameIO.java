package com.ramilizmailov.guessnumber;

import java.util.Scanner;

/**
 * Created by RAMSES on 22.02.2016.
 */
public class ConsoleGameIO implements GameIO{

    private GameCore gameCore;
    private Scanner scanner = new Scanner(System.in);
    private Thread gameInputThread;

    public ConsoleGameIO(GameCore gameCore) {
        this.gameCore = gameCore;

        gameInputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!gameCore.processInput(scanner.next())) {}
                gameCore.saveResultsAndShowResultsTable(scanner.next());
            }
        });
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void terminate() {
        gameInputThread.interrupt();
    }

    @Override
    public void start() {
        if (!gameInputThread.isAlive())
            gameInputThread.start();
    }
}
