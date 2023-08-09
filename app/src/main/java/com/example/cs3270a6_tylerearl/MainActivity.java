package com.example.cs3270a6_tylerearl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextScoreFragment.TextScore,
StatsFragment.StatsResults, GameFragment.GameFragmentInterface, TimesUpFragmentDialog.TimesUpDialogDismissed {

    private FragmentManager fm;
    private GameFragment gameFragment;
    private StatsFragment statsFragment;

    int range = 10;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoadFragments(new GameFragment(), R.id.gameFragmentContainer, "GameFragment");
        LoadFragments(new StatsFragment(), R.id.statsFragmentContainer, "StatsFragment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();
        if(id == R.id.menuActionZero){
            statsFragment = (StatsFragment) fm.findFragmentByTag("StatsFragment");
            statsFragment.setCorrectCount(0);

            Toast toast = Toast.makeText(this, "Correct Count Reset", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        if( id == R.id.menuActionTextScore){
            fm.beginTransaction().replace(R.id.gameFragmentContainer, new TextScoreFragment(),
                    "TextScoreFragment")
                    .hide(fm.findFragmentById(R.id.statsFragmentContainer)).commit();

            return true;
        }
        else return super.onOptionsItemSelected(item);

    }

    @Override
    public void Reset() {
        gameFragment = (GameFragment) fm.findFragmentByTag("GameFragment");
        gameFragment.ResetGame();
    }

    private void LoadFragments(Fragment f, int fID, String tag){
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(fID, f, tag).commit();
    }


    @Override
    public void OpenText(String phone) {
        statsFragment = (StatsFragment) fm.findFragmentByTag("StatsFragment");
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra("sms_body", "Check out my high score: "
                    + statsFragment.numWins + "!");
            intent.setData(Uri.parse(("sms:"+phone)));
            startActivity(intent);

        } catch (Exception e){
            Log.e("OpenText", e.getMessage());
        }
    }

    @Override
    public void TextScoreFragmentDismissed() {
        fm.beginTransaction().replace(R.id.gameFragmentContainer, new GameFragment(),
                        "GameFragment")
                .show(fm.findFragmentById(R.id.statsFragmentContainer)).commit();
    }

    @Override
    public void TimesUp() {
        DialogFragment timesUpFragmentDialog = new TimesUpFragmentDialog();
        timesUpFragmentDialog.show(fm, "TimesUpFragmentDialog");

    }

    @Override
    public void YouWin(){
        statsFragment = (StatsFragment) fm.findFragmentByTag("StatsFragment");
        statsFragment.winner();
    }

    @Override
    public void SendRange(int range){
        this.range = range;
    }

    @Override
    public int GetRange() {
    gameFragment = (GameFragment) fm.findFragmentByTag("GameFragment");
    return this.range;
    }

    @Override
    public void SendGuess(int guess){
        statsFragment = (StatsFragment) fm.findFragmentByTag("StatsFragment");
        statsFragment.setPreviousGuess(guess);
    }


    @Override
    public void TimesUpDialogClosed() {
        statsFragment = (StatsFragment) fm.findFragmentByTag("StatsFragment");
        statsFragment.resetTimer();
        Reset();
    }
}