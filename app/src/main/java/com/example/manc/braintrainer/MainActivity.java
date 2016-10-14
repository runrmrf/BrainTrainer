package com.example.manc.braintrainer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button playAgain;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView txtTimer;
    TextView txtScore;
    TextView txtMessage;
    TextView txtQuestion;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    Boolean gameIsActive = true;


//Go! button
    public void btnGo (View view) {

        RelativeLayout startLayout = (RelativeLayout) findViewById(R.id.startLayout);
        RelativeLayout gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);

        startLayout.setVisibility(View.GONE);
        gameLayout.setVisibility(View.VISIBLE);

        gameStart();

    }

//Timer
    public void updateTimer (int secondsLeft) {

        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);

        txtTimer.setText(secondString + "s");

    }

    public void gameStart () {

        if (counterIsActive == false) {

            gameIsActive = true;
            counterIsActive = true;
            generateQuestion();

            countDownTimer = new CountDownTimer(30000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilDone) {

                    updateTimer((int) millisUntilDone / 1000);

                }

                @Override
                public void onFinish() {

                    txtTimer.setText("0s");
                    playAgain.setVisibility(View.VISIBLE);
                    txtMessage.setTextColor(Color.RED);
                    txtMessage.setText("Your score: " + score + "/" + numberOfQuestions );
                    gameIsActive = false;
                }

            }.start();

        } else {

            newGame((Button) findViewById(R.id.btnPlayAgain));

        }

    }


//Play Again Button
    public void newGame (View view) {

        txtTimer.setText("30s");
        countDownTimer.cancel();
        txtScore.setText("0/0");
        numberOfQuestions = 0;
        score = 0;
        counterIsActive = false;
        playAgain.setVisibility(View.INVISIBLE);
        txtMessage.setText("");
        gameStart();
    }

//Generate Question
    public void generateQuestion() {



        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        int incorrectAnswer;

        txtQuestion.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationOfCorrectAnswer = rand.nextInt(4);
        answers.clear();

        for (int i = 0; i < 4; i++) {

            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);

            } else {

                incorrectAnswer = rand.nextInt(41);

                while (incorrectAnswer == a + b) {

                    incorrectAnswer = rand.nextInt(41);
                }

                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

//Answer buttons
    public void chooseAnswer (View view) {

        if (gameIsActive) {

            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {

                score++;
                txtMessage.setTextColor(Color.GREEN);
                txtMessage.setText("Correct!");

            } else {
                txtMessage.setTextColor(Color.RED);
                txtMessage.setText("Wrong!");

            }

            numberOfQuestions++;
            txtScore.setText(score + "/" + numberOfQuestions);
            generateQuestion();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.go);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        playAgain = (Button) findViewById(R.id.btnPlayAgain);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);



    }
}
