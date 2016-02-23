package com.ramilizmailov.guessnumber.controller;

/**
 * Created by RAMSES on 22.02.2016.
 */
public interface GameController {
    void start();
    void terminate();
    void printMessage(String message);
    void displayResultsTable(String results);
}
