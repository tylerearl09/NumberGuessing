package com.example.cs3270a6_tylerearl;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;


public class GameFragment extends Fragment {

    private View view;
    private GameFragmentInterface listener;
    Random rand = new Random();

    int numToGuess = 0;

    int range = 0;
    TextView higherLowerWin;

    private TextInputEditText t1;
        public interface GameFragmentInterface{
        void YouWin();
        int GetRange();
        void SendGuess(int guess);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);
        t1 = (TextInputEditText) view.findViewById(R.id.guess);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GameFragmentInterface){
            listener = (GameFragmentInterface) context;
        }else{
            throw new ClassCastException(context + " Must implement GameFragmentInterface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        t1.addTextChangedListener(tw);
        numToGuess = rand.nextInt(listener.GetRange());
    }

    private final TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(t1.getText() != null && !t1.getText().toString().isEmpty()) {
                int guess = Integer.parseInt(String.valueOf(t1.getText()));
                listener.SendGuess(guess);
                playGame(guess);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    public void playGame(int guess){
        higherLowerWin = (TextView) view.findViewById(R.id.HigherLowerWin);
        if (guess < numToGuess){
            higherLowerWin.setText("Higher!");
            higherLowerWin.setTextColor(Color.RED);
        }

        if (guess > numToGuess){
            higherLowerWin.setText("Lower!");
            higherLowerWin.setTextColor(Color.BLACK);
        }

        if (guess == numToGuess){
            listener.YouWin();
            higherLowerWin.setTextColor(Color.GREEN);
            higherLowerWin.setText("You Win!");

        }

    }

    void ResetGame(){
        numToGuess = rand.nextInt(listener.GetRange());
        higherLowerWin = (TextView) view.findViewById(R.id.HigherLowerWin);
        higherLowerWin.setText("Guess!");
        higherLowerWin.setTextColor(Color.BLACK);
    }

    void setHigherLowerWinText(String text){
        higherLowerWin = (TextView) view.findViewById(R.id.HigherLowerWin);
        higherLowerWin.setText(text);
        higherLowerWin.setTextColor(Color.BLACK);
    }

}