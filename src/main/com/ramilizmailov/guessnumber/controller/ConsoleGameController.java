package com.ramilizmailov.guessnumber.controller;

import com.ramilizmailov.guessnumber.core.GameCore;

import java.util.Scanner;

/**
 * Created by RAMSES on 22.02.2016.
 */
public class ConsoleGameController implements GameController {

    private GameCore gameCore;
    private Scanner scanner = new Scanner(System.in);
    private Thread gameInputThread;

    public ConsoleGameController(GameCore gameCore) {
        this.gameCore = gameCore;

        gameInputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!gameCore.processInput(scanner.nextLine())) {}
                //scanner.reset();
                gameCore.saveResultsAndShowResultsTable(scanner.nextLine());
            }
        });
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayResultsTable(String results) {
        System.out.println(results);
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
