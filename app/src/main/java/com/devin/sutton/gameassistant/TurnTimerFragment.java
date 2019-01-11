package com.devin.sutton.gameassistant;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.Duration;

public class TurnTimerFragment extends Fragment {
    private TextView mPlayerOneTimeTextView;
    private TextView mPlayerTwoTimeTextView;
    private Button mPassTurnButton;
    private ImageButton mPausePlayButton;

    private CountDownTimer mTimer;
    private int currentPlayer;
    private boolean timerStarted;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (timerStarted) {
            menu.findItem(R.id.set_time).setEnabled(false);
        } else {
            menu.findItem(R.id.set_time).setEnabled(true);
        }
    }

    private Duration playerOneTime;
    private Duration playerTwoTime;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.timer_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.set_time:
                TimePickerDialog dialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            playerOneTime = Duration.ofMinutes((hourOfDay * 60) + minute);
                            playerTwoTime = Duration.ofMinutes((hourOfDay * 60) + minute);
                            updateTimerText(mPlayerOneTimeTextView, playerOneTime);
                            updateTimerText(mPlayerTwoTimeTextView, playerTwoTime);
                            mPassTurnButton.setText("Start Timer");
                            mPassTurnButton.setEnabled(true);
                            mPausePlayButton.setEnabled(false);
                            mPausePlayButton.setImageResource(R.drawable.ic_pause_black_24dp);

                    }
                }, 0, 20, true);
                dialog.show();
                return true;
            default: return true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.turn_timer_fragment_layout, container, false);

        setHasOptionsMenu(true);

        currentPlayer = 1;
        playerOneTime = Duration.ofSeconds(1200);
        playerTwoTime = Duration.ofSeconds(1200);;
        timerStarted = false;


        //Find Views
        mPlayerOneTimeTextView = view.findViewById(R.id.player_one_time);
        mPlayerTwoTimeTextView = view.findViewById(R.id.player_two_time);

        mPausePlayButton = view.findViewById(R.id.pause_resume_timer_button);
        mPausePlayButton.setEnabled(false);
        mPausePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerStarted){
                    timerStarted = false;
                    mTimer.cancel();
                    mPausePlayButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    mPassTurnButton.setEnabled(false);
                } else if(!timerStarted){
                    timerStarted = true;
                    mTimer = startTimer();
                    mPausePlayButton.setImageResource(R.drawable.ic_pause_black_24dp);
                    mPassTurnButton.setEnabled(true);
                }
            }
        });

        mPassTurnButton = view.findViewById(R.id.pass_turn_button);
        mPassTurnButton.setText("Start Timer");
        mPassTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerStarted) {
                    if (currentPlayer == 1) {currentPlayer = 2;} else if (currentPlayer == 2) {currentPlayer = 1;}
                } else {
                    timerStarted = true;
                    mPassTurnButton.setText("Pass Turn");
                    mTimer = startTimer();
                    mPausePlayButton.setEnabled(true);
                }

            }
        });
        return view;

    }

    public CountDownTimer startTimer(){
        return new CountDownTimer(playerOneTime.toMillis() + playerTwoTime.toMillis(), 1000) {

            public void onTick(long millisUntilFinished) {
                if(currentPlayer == 1){
                    playerOneTime = playerOneTime.minusSeconds(1);
                    updateTimerText(mPlayerOneTimeTextView, playerOneTime);
                } else if (currentPlayer == 2){
                    playerTwoTime = playerTwoTime.minusSeconds(1);
                    updateTimerText(mPlayerTwoTimeTextView, playerTwoTime);
                }
            }

            public void onFinish() {
                System.out.println("Timer Finished");
            }
        }.start();
    }

    public static TurnTimerFragment newInstance() {
        return new TurnTimerFragment();
    }

    public void updateTimerText(TextView textView, Duration timeRemaining){
        long totalSecondsRemaining = timeRemaining.getSeconds();
        int minutesRemaining = (int) totalSecondsRemaining / 60;
        int secondsRemaining = (int) (totalSecondsRemaining - (minutesRemaining * 60));
        String minutes = String.valueOf(minutesRemaining);
        String seconds;
        if(secondsRemaining < 10){
            seconds = "0" + String.valueOf(secondsRemaining);
        } else {
            seconds = String.valueOf(secondsRemaining);
        }

        textView.setText(minutes + ":" + seconds);

    }
}
