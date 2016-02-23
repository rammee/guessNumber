package com.ramilizmailov.guessnumber.controller;

import com.ramilizmailov.guessnumber.core.GameCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogGameController extends JDialog  implements GameController {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane gameMasterTextPane;
    private JTextField answerField;

    private GameCore gameCore;
    private boolean isGameFinished = false;

    public DialogGameController(GameCore gameCore) {
        this.gameCore = gameCore;

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        buttonOK = new JButton("OK");
        buttonOK.setSize(150, 100);
        gameMasterTextPane = new JTextPane();
        contentPane.add(gameMasterTextPane, BorderLayout.CENTER);

        answerField = new JTextField();
        answerField.setSize(350, 100);

        JPanel southPanel = new JPanel(new GridLayout());
        southPanel.add(answerField);
        southPanel.add(buttonOK);
        contentPane.add(southPanel, BorderLayout.SOUTH);

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
            gameMasterTextPane.setSize(gameMasterTextPane.getWidth(), 400);
            gameCore.saveResultsAndShowResultsTable(answerField.getText());
        }
    }

    private void onCancel() {
        dispose();
    }

    @Override
    public void printMessage(String message) {
        gameMasterTextPane.setText(gameMasterTextPane.getText() + "\n" + message);
    }

    @Override
    public void displayResultsTable(String results) {
        JDialog resultsDialog = new JDialog();
        resultsDialog.setSize(350, 400);
        resultsDialog.setTitle("Results");
        resultsDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JTextArea textArea = new JTextArea(results);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resultsDialog.add(textArea, BorderLayout.CENTER);

        resultsDialog.setLocationRelativeTo(null);
        this.dispose();
        resultsDialog.setVisible(true);
    }

    @Override
    public void terminate() {
        this.dispose();
    }

    @Override
    public void start() {
        if(!isVisible()) {
            setSize(350, 400);
            setTitle("Guess Number Game");
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
        }
    }
}
