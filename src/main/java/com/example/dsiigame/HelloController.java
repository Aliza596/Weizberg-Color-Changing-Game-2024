package com.example.dsiigame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class HelloController {
    @FXML
    private Button startButton;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label colorLabel;
    @FXML
    private HBox labelBox;
    @FXML
    private Pane buttonBox;
    Random random = new Random();
    Timeline buttonTimeline;
    String colorOfLabel;
    String newColor;
    ArrayList<Integer> spotsTaken = new ArrayList<>();
    int randInt;
    int score;
    private Clip correctSound;
    private Clip incorrectSound;
    private int buttonBoxSound;

    private String[] colorsToLoopThrough = {"-fx-background-color: red", "-fx-background-color: green",
            "-fx-background-color: blue", "-fx-background-color: yellow", "-fx-background-color: pink"};


    public HelloController() {
        try {
            correctSound = loadSound("/correctAnswerSound.wav");
            incorrectSound = loadSound("/wrongAnswerSound.wav");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onStartButtonClick(ActionEvent event) {
        startButton.setVisible(false);
        changingColor();
    }

    public void changingColor() {
        int buttonBoxSize = buttonBox.getChildren().size();
        colorOfLabel = colorsToLoopThrough[random.nextInt(5)];
        colorLabel.setVisible(true);
        colorLabel.setStyle(colorOfLabel);

        labelBox.setVisible(true);

        newColor = colorOfLabel;
        spotsTaken.clear();


        for (int i = 0; i < 4; i++) {
            do {
                randInt = random.nextInt(buttonBoxSize);
            } while (spotsTaken.contains(randInt));

            spotsTaken.add(randInt);

            Button currentButton = (Button) buttonBox.getChildren().get(randInt);

            double randTime = random.nextDouble(1.5, 3);
            int points = calculateScore(randTime);

            currentButton.setVisible(true);
            currentButton.setStyle(newColor);
            currentButton.setText(Integer.toString(points));
            if (newColor == "-fx-background-color: yellow" || newColor == "-fx-background-color: pink")
            {
                currentButton.setTextFill(Color.BLACK);
            } else {
                currentButton.setTextFill(Color.WHITE);
            }

            if (i == 0) {
                currentButton.setOnAction(e -> correctButtonClicked(points));
            } else {
                currentButton.setOnAction(e -> inCorrectButtonClicked(points));
            }

            buttonTimeline = new Timeline(new KeyFrame(Duration.seconds(randTime), e -> {
                currentButton.setVisible(false);
                currentButton.setStyle("");
            }));
            buttonTimeline.setCycleCount(1);
            buttonTimeline.play();

            do {
                newColor = colorsToLoopThrough[random.nextInt(5)];
            } while (newColor.equals(colorOfLabel));
        }


        if (score >= 50) {
            winGame(true, buttonTimeline);
        } else if (score <= -50) {
            winGame(false, buttonTimeline);
        }

        Timeline resetRound = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            allButtonsInvisible();
            changingColor();
        }));
        resetRound.setCycleCount(1);
        resetRound.play();
    }

    public void allButtonsInvisible() {
        for (int i = 0; i < buttonBox.getChildren().size(); i++) {
            buttonBox.getChildren().get(i).setVisible(false);
            buttonBox.getChildren().get(i).setStyle("");
        }
    }

    public void correctButtonClicked(int points) {
        playSound(correctSound);
        int bonusPoints = random.nextInt(10);
        if (bonusPoints <= 3) {
            score += (points + 5);
            scoreLabel.setText("5 Bonus Points!!\nScore:\n" + score);
        } else {
            score += points;
            scoreLabel.setText("Score:\n" + score);
        }



        // Makes the game harder if these are commented out
//        allButtonsInvisible();
//        changingColor();
    }

    public void inCorrectButtonClicked(int points) {
        playSound(incorrectSound);
        score -= points;
        scoreLabel.setText("Score:\n" + score);
    }

    private void playSound(Clip sound) {
        if (sound.isRunning()) {
            sound.stop();
        }
        sound.setFramePosition(0);
        sound.start();
    }

    private Clip loadSound(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL soundURL = getClass().getResource(file);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    public int calculateScore(double time) {
        int points;
        if (time < 2) {
            points = 3;
        } else if (time < 2.5) {
            points = 2;
        } else {
            points = 1;
        }
        return points;
    }

    public void winGame(boolean bool, Timeline buttonTimeline) {
        allButtonsInvisible();
        colorLabel.setVisible(false);
        startButton.setVisible(true);
        scoreLabel.setVisible(false);
        if (bool) {
            startButton.setText("Congrats!\nYou Win!!!");
        } else {
            startButton.setText("Game Over ;(");
        }
        buttonTimeline.stop();
    }


}