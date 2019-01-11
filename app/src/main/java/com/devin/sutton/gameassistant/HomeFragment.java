package com.devin.sutton.gameassistant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
    private Button mDiceRollerButton;
    private Button mLifeCounterButton;
    private Button mTurnTimerButton;
    private Button mInitiativeTrackerButton;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);

        mDiceRollerButton = view.findViewById(R.id.home_dice_roller_button);
        mDiceRollerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, DiceFragment.newInstance()).commitNow();
            }
        });
        mLifeCounterButton = view.findViewById(R.id.home_life_counter_button);
        mLifeCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, LifeCounterFragment.newInstance()).commitNow();
            }
        });
        mTurnTimerButton = view.findViewById(R.id.home_turn_timer_button);
        mTurnTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, TurnTimerFragment.newInstance()).commitNow();
            }
        });
        mInitiativeTrackerButton = view.findViewById(R.id.home_initiative_tracker_button);
        mInitiativeTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, InitiativeFragment.newInstance()).commitNow();
            }
        });

        return view;
    }
}
