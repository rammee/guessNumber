package com.ramilizmailov.guessnumber.view;

import com.ramilizmailov.guessnumber.model.GameModel;
import com.ramilizmailov.guessnumber.model.levels.Level;

import javax.swing.*;
import javax.swing.text.JTextComponent;
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
    private JLabel effortsLabel;
    private JLabel totalEffortsLabel;

    private Consumer<String> inputProcessingStrategy;

    public DialogGameView() {

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        buttonOK = new JButton("OK");
        buttonOK.setSize(150, 100);
        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        answerField = new JTextField();
        answerField.setSize(350, 100);

        JPanel southPanel = new JPanel(new GridLayout());
        southPanel.add(answerField);
        southPanel.add(buttonOK);
        contentPane.add(southPanel, BorderLayout.SOUTH);

        effortsLabel = new JLabel("Efforts: 0");
        totalEffortsLabel = new JLabel("Total efforts: 0");
        JPanel northPanel = new JPanel(new GridLayout());
        northPanel.add(effortsLabel);
        northPanel.add(totalEffortsLabel);
        contentPane.add(northPanel, BorderLayout.NORTH);

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
        String propName = evt.getPropertyName();
        if (GameModel.MESSAGE_PROPERTY_NAME.equals(propName)) {
            showMessage(evt.getNewValue().toString());
        } else if (GameModel.CURRENT_LEVEL_PROPERTY_NAME.equals(propName)) {
            Level level = (Level)evt.getNewValue();
            textPane.setText("");
            if (level.getLevelNo() > 1) showMessage("Bingo! Proceeding to the next level.\n");
            setTitle(level.getDescription());
        } else if (GameModel.CURRENT_LEVEL_EFFORTS_PROPERTY_NAME.equals(propName)) {
            effortsLabel.setText("Efforts: " + evt.getNewValue());
        } else if (GameModel.TOTAL_EFFORTS_PROPERTY_NAME.equals(propName)) {
            totalEffortsLabel.setText("Total efforts: " + evt.getNewValue());
        }
    }

    @Override
    public void display() {
        setSize(350, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void setInputProcessingStrategy(Consumer<String> strategy) {
        this.inputProcessingStrategy = strategy;
    }

    public JTextPane getTextPane() {
        return textPane;
    }
}
