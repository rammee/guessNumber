package com.ramilizmailov.guessnumber.view;

import com.ramilizmailov.guessnumber.model.GameModel;
import com.ramilizmailov.guessnumber.model.levels.Level;

import java.beans.PropertyChangeEvent;
import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleGameView implements GameView {

    private InputProcessor inputProcessor;

    public ConsoleGameView() {
        inputProcessor = new InputProcessor();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
        if (GameModel.MESSAGE_PROPERTY_NAME.equals(propName)) {
            showMessage((String)evt.getNewValue());
        } else if (GameModel.CURRENT_LEVEL_PROPERTY_NAME.equals(propName)) {
            Level level = (Level)evt.getNewValue();
            showMessage(level.getDescription());
        } else if (GameModel.TOTAL_EFFORTS_PROPERTY_NAME.equals(propName)) {
            showMessage("Bingo! Efforts made: " + evt.getNewValue());
        }
    }

    private class InputProcessor implements Runnable {

        private Scanner scanner = new Scanner(System.in);
        private boolean isRunning = true;
        private Consumer<String> strategy;

        @Override
        public void run() {
            while (isRunning) strategy.accept(scanner.nextLine());
        }

        public void kill() {
            isRunning = false;
        }

        public void setStrategy(Consumer<String> strategy) {
            this.strategy = strategy;
        }
    }

    @Override
    public void display() {
        Thread readInputThread = new Thread(inputProcessor);
        readInputThread.start();
    }

    @Override
    public void setInputProcessingStrategy(Consumer<String> strategy) {
        this.inputProcessor.setStrategy(strategy);
    }

    @Override
    public void dispose() {
        inputProcessor.kill();
    }
}
