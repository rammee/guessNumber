package com.ramilizmailov.guessnumber.gui;

import com.ramilizmailov.guessnumber.GameCore;
import com.ramilizmailov.guessnumber.GameIO;

import javax.swing.*;
import java.awt.event.*;

public class MainDialog extends JDialog  implements GameIO {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane gameMasterTextPane;
    private JTextField answerField;

    private GameCore gameCore;
    private boolean isGameFinished = false;

    public MainDialog(GameCore gameCore) {
        this.gameCore = gameCore;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        gameMasterTextPane.setEditable(false);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (!isGameFinished) {
            isGameFinished = gameCore.processInput(answerField.getText());
            answerField.setText("");
        } else {
            gameCore.saveResultsAndShowResultsTable(answerField.getText());
        }
    }

    private void onCancel() {
        dispose();
    }

    @Override
    public void printMessage(String message) {
        gameMasterTextPane.setText(message);
    }

    @Override
    public void terminate() {
        this.dispose();
    }

    @Override
    public void start() {
        pack();
        setVisible(true);
    }
}
