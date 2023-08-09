package com.example.cs3270a6_tylerearl;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class StatsFragment extends Fragment {

    private View view;

    boolean timesUp = false;
    private SeekBar seekBar;
    int numWins = 0;

    private StatsResults listener;

    Random rand = new Random();

    TextView timeRemainingText, previousGuessText, numRangeText, numCorrectText;

    MainActivity mainActivity;
    public interface StatsResults{
        void TimesUp();

        void SendRange(int range);

        void Reset();

    }

    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timeRemainingText = view.findViewById(R.id.timeRemainingText);
            timeRemainingText.setText(String.valueOf(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            timesUp = true;
            timeRemainingText.setText("0");
            listener.TimesUp();

        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof StatsResults){
            listener = (StatsResults) context;
        } else {
            throw new ClassCastException(context + " Must implement StatsResults");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_stats, container, false);
        mainActivity = (MainActivity)getActivity();
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        Button btnNewNumber = (Button) view.findViewById(R.id.newNumberButton);

        btnNewNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.Reset();
                resetTimer();
            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        timer.start();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listener.SendRange(progress);
                numRangeText = (TextView) view.findViewById(R.id.numRangeText);
                numRangeText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void winner(){
        {
            timer.cancel();
            setCorrectCount(numWins + 1);

            numCorrectText = (TextView) view.findViewById(R.id.numCorrectText);
            numCorrectText.setText(String.valueOf(numWins));

        }
    }

    public void setPreviousGuess(int guess){
        previousGuessText = (TextView) view.findViewById(R.id.previousGuessText);
        previousGuessText.setText(String.valueOf(guess));
    }

    public void resetTimer(){
        timer.cancel();
        timer.start();
        setPreviousGuess(0);
    }

    public void setCorrectCount(int count){
        numWins = count;
        numCorrectText = (TextView) view.findViewById(R.id.numCorrectText);
        numCorrectText.setText(String.valueOf(count));
    }




}