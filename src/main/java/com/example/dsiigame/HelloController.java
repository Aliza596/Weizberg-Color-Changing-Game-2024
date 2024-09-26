package com.example.dsiigame;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class HelloController {
    @FXML
    private Label welcomeText;
    public javafx.scene.control.Button Button;

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
    @FXML
//    private Button button1;
//    @FXML
//    private Button button2;
//    @FXML
//    private Button button3;
//    @FXML
//    private Button button4;
//    @FXML
//    private Button button5;
//    @FXML
//    private Button button6;
//    @FXML
//    private Button button7;
//    @FXML
//    private Button button8;
//    @FXML
//    private Button button9;
//    @FXML
//    private Button button10;
//    @FXML
//    private Button button11;
//    @FXML
//    private Button button12;
//    @FXML
//    private Button button13;
//    @FXML
//    private Button button14;
//    @FXML
//    private Button button15;
//    @FXML
//    private Button button16;
//    @FXML
//    private Button button17;
//    @FXML
//    private Button button18;
//    @FXML
//    private Button button19;
//    @FXML
//    private Button button20;
    Button[] buttonsArray = new Button[20];
    Random random = new Random();
    String colorOfLabel;
    String newColor;
    String colorUpTo;
    ArrayList<Integer> spotsTaken = new ArrayList<>();
    int randInt;

    int score;

    private String[] colorsToLoopThrough = {"-fx-background-color: red", "-fx-background-color: green",
            "-fx-background-color: blue", "-fx-background-color: yellow", "-fx-background-color: pink"};

    @FXML
    protected void onStartButtonClick(ActionEvent event) {
        startButton.setVisible(false);

        changingColor();
//
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> changingColor()));
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();

//        for (int i = 0; i < buttonBoxSize; i++) {
//            buttonsArray[i] = (Button)buttonBox.getChildren().get(i);
//        }
//
//
//        for (int i = 0; i < 4; i++) {
//            int randInt = random.nextInt(buttonBoxSize - 1);
//            buttonsArray[randInt].setVisible(true);
//            buttonsArray[randInt].setStyle(colorUpTo);
//        }

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

            currentButton.setVisible(true);
            currentButton.setStyle(newColor);

            int randTime = random.nextInt(4) + 1;

            if (i == 0) {
                currentButton.setOnAction(e -> correctButtonClicked());
            } else {
                currentButton.setOnAction(e -> inCorrectButtonClicked());
            }

            Timeline buttonTimeline = new Timeline(new KeyFrame(Duration.seconds(randTime), e -> {
                currentButton.setVisible(false);
                currentButton.setStyle("");
            }));
            buttonTimeline.setCycleCount(1);
            buttonTimeline.play();

//            PauseTransition pause = new PauseTransition(Duration.seconds(randTime));
//            pause.setOnFinished(e -> {
//                currentButton.setVisible(false);
//                currentButton.setStyle("");
//            });
//
//            pause.play();

            do {
                newColor = colorsToLoopThrough[random.nextInt(5)];
            } while (newColor.equals(colorOfLabel));
        }

        Timeline resetRound = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            allButtonsInvisible(buttonBoxSize);
            changingColor();
        }));
        resetRound.setCycleCount(1);
        resetRound.play();

//        PauseTransition resetRound = new PauseTransition(Duration.seconds(5));
//        resetRound.setOnFinished(e -> allButtonsInvisible(buttonBoxSize));
//        resetRound.play();
    }

    public void allButtonsInvisible(int size) {
        for (int i = 0; i < size; i++) {
            buttonBox.getChildren().get(i).setVisible(false);
            buttonBox.getChildren().get(i).setStyle("");
        }
    }

    public void correctButtonClicked() {
        score += 10;
        scoreLabel.setText("Score:\n" + score);
    }

    public void inCorrectButtonClicked() {
        score -= 10;
        scoreLabel.setText("Score:\n" + score);
    }


}