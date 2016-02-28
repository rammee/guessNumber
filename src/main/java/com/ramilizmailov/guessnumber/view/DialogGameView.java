package com.ramilizmailov.guessnumber.view;

import com.ramilizmailov.guessnumber.model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.util.function.Consumer;

/**
 * Created by RAMSES on 27.02.2016.
 */
public class DialogGameView extends JDialog implements GameView {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane textPane;
    private JTextField answerField;

    private GameModel gameModel;
    private Consumer<String> inputProcessingStrategy;

    public DialogGameView(GameModel gameModel) {
        this.gameModel = gameModel;

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        buttonOK = new JButton("OK");
        buttonOK.setSize(150, 100);
        textPane = new JTextPane();
        contentPane.add(textPane, BorderLayout.CENTER);

        answerField = new JTextField();
        answerField.setSize(350, 100);

        JPanel southPanel = new JPanel(new GridLayout());
        southPanel.add(answerField);
        southPanel.add(buttonOK);
        contentPane.add(southPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        textPane.setEditable(false);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        buttonOK.addActionListener(e -> {
            inputProcessingStrategy.accept(answerField.getText());
            answerField.setText("");
        });
    }

    public void showResults(String results) {
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
        resultsDialog.setVisible(true);
    }

    @Override
    public void showMessage(String message) {
        textPane.setText(textPane.getText() + "\n" + message);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (gameModel.MESSAGE_PROPERTY_NAME.equals(evt.getPropertyName())) {
            showMessage(evt.getNewValue().toString());
        }
    }

    @Override
    public void display() {
        setSize(350, 400);
        setTitle("Guess Number Game");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void setInputProcessingStrategy(Consumer<String> strategy) {
        this.inputProcessingStrategy = strategy;
    }

}
