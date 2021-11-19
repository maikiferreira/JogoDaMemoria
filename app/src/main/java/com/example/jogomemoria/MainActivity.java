package com.example.jogomemoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnReset;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private List<String> sequence = new ArrayList<>();
    private final List<Button> selectedButton = new ArrayList<>();
    private int indexButton;
    private ConstraintLayout constraintLayout;
    private List<Button> buttons = new ArrayList<>();
    private GridLayout grid;
    private LinearLayout progressBox;
    private LinearLayout congratulations;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReset = (Button) findViewById(R.id.btnReset);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        constraintLayout = (ConstraintLayout) findViewById(R.id.bgGame);
        buttons.addAll(Arrays.asList(btn1, btn2, btn3, btn4, btn5, btn6));
        grid = (GridLayout) findViewById(R.id.grid);
        progressBox = (LinearLayout) findViewById(R.id.progressBox);
        congratulations = (LinearLayout) findViewById(R.id.congratulations);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        initilizeButtons();
        resetGame();
    }

    private void resetGame() {
        sequence = createSequence();
        resetButtons();
        indexButton = 0;
        showButtonsAndProgress();
        progressBar.setProgress(0);
    }

    private void resetButtons() {
        for (Button button : selectedButton) button.setVisibility(View.VISIBLE);
        selectedButton.clear();
        constraintLayout.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private List<String> createSequence() {
        List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6"));
        Collections.shuffle(list);
        return list;
    }

    private void selectNumber(View view) {
        Button button = (Button) view;

        if (button.getText().equals(sequence.get(indexButton))) {
            int color = button.getBackgroundTintList().getDefaultColor();
            Drawable dra = (Drawable)view.getBackground();;
            constraintLayout.setBackground(dra);
            selectedButton.add(button);
            button.setVisibility(View.INVISIBLE);
            indexButton += 1;
            progressBar.setProgress(selectedButton.size());
            if (indexButton == 6) {
                showCongratulationsMessage();
            }
        } else {
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.white));
            indexButton = 0;
            resetButtons();
            progressBar.setProgress(0);
        }
    }

    private void initilizeButtons() {
        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectNumber(v);
                }
            });
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void showCongratulationsMessage(){
        grid.setVisibility(View.INVISIBLE);
        progressBox.setVisibility(View.INVISIBLE);
        congratulations.setVisibility(View.VISIBLE);
    }

    private void showButtonsAndProgress(){
        grid.setVisibility(View.VISIBLE);
        progressBox.setVisibility(View.VISIBLE);
        congratulations.setVisibility(View.INVISIBLE);
    }
}