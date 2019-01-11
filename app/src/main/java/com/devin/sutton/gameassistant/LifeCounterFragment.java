package com.devin.sutton.gameassistant;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class LifeCounterFragment extends Fragment {

    private int lifeOne;
    private int lifeTwo;
    private ImageButton mLifeUpOneButton;
    private ImageButton mLifeDownOneButton;
    private ImageButton mLifeUpTwoButton;
    private ImageButton mLifeDownTwoButton;
    private TextView mLifeOneText;
    private TextView mLifeTwoText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.life_counter_fragment, container, false);
        lifeOne = 20;
        lifeTwo = 20;
        mLifeOneText = (TextView) view.findViewById(R.id.player_one_life_text_view);
        mLifeOneText.setText(String.valueOf(lifeOne));
        mLifeTwoText = (TextView) view.findViewById(R.id.player_two_life_text_view);
        mLifeTwoText.setText(String.valueOf(lifeTwo));

        mLifeUpOneButton = view.findViewById(R.id.player_one_life_up_button);
        mLifeUpOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lifeOne += 1;
                updateText(mLifeOneText, lifeOne);
            }
        });

        mLifeDownOneButton = view.findViewById(R.id.player_one_life_down_button);
        mLifeDownOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lifeOne -= 1;
                updateText(mLifeOneText, lifeOne);
            }
        });

        mLifeUpTwoButton = view.findViewById(R.id.player_two_life_up_button);
        mLifeUpTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lifeTwo += 1;
                updateText(mLifeTwoText, lifeTwo);
            }
        });

        mLifeDownTwoButton = view.findViewById(R.id.player_two_life_down_button);
        mLifeDownTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lifeTwo -= 1;
                updateText(mLifeTwoText, lifeTwo);
            }
        });


        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static LifeCounterFragment newInstance() {
        return new LifeCounterFragment();
    }

    private void updateText(TextView textView, int life) {
        textView.setText(String.valueOf(life));
        if (life < 0) textView.setTextColor(Color.RED);
        if (life >= 0) textView.setTextColor(Color.BLACK);
    }
}