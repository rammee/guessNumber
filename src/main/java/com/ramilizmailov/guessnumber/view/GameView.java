package com.ramilizmailov.guessnumber.view;

import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

/**
 * Created by RAMSES on 27.02.2016.
 */
public interface GameView extends PropertyChangeListener {
    void display();
    void dispose();
    void setInputProcessingStrategy(Consumer<String> strategy);
    void showMessage(String message);
}
