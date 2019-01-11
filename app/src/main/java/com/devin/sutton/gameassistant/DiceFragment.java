package com.devin.sutton.gameassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceFragment extends Fragment {

    private Button mSixDie;
    private Button mFourDie;
    private Button mEightDie;
    private Button mTenDie;
    private Button mTwelveDie;
    private Button mTwentyDie;
    private Button mHundredDie;
    private Button mClearButton;
    private RecyclerView mDiceResultsRecyclerView;
    private TextView mDiceQuantity;

    private ImageButton mAddDiceButton;
    private ImageButton mRemoveDiceButton;

    private int diceQuantity;

    private DiceAdapter mAdapter;
    private List<Integer> mDiceRolls;

    public static DiceFragment newInstance() {
        return new DiceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dice_fragment, container, false);

        diceQuantity = 1;

        mDiceQuantity = view.findViewById(R.id.dice_quantity_text);
        mDiceQuantity.setText(String.valueOf(diceQuantity));

        mAddDiceButton = view.findViewById(R.id.add_dice_button);
        mAddDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diceQuantity++;
                mDiceQuantity.setText(String.valueOf(diceQuantity));
            }
        });

        mRemoveDiceButton = view.findViewById(R.id.remove_dice_button);
        mRemoveDiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diceQuantity--;
                mDiceQuantity.setText(String.valueOf(diceQuantity));
            }
        });

        mDiceRolls = new ArrayList<Integer>();
        mDiceResultsRecyclerView = (RecyclerView) view.findViewById(R.id.dice_results);
        mDiceResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDiceQuantity = view.findViewById(R.id.dice_quantity_text);
        updateUI();

        mFourDie = view.findViewById(R.id.four_die_button);
        mFourDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceRolls.add(0, rollDice(diceQuantity, 4));
                updateUI();
                }
        });
        mSixDie = view.findViewById(R.id.six_die_button);
        mSixDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceRolls.add(0, rollDice(diceQuantity, 6));
                updateUI();
            }
        });
        mEightDie = view.findViewById(R.id.eight_die_button);
        mEightDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceRolls.add(0, rollDice(diceQuantity, 8));
                updateUI();
            }
        });
        mTenDie = view.findViewById(R.id.ten_die_button);
        mTenDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceRolls.add(0, rollDice(diceQuantity, 10));
                updateUI();
            }
        });
        mTwelveDie = view.findViewById(R.id.twelve_die_button);
        mTwelveDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceRolls.add(0, rollDice(diceQuantity, 12));
                updateUI();
            }
        });
        mTwentyDie = view.findViewById(R.id.twenty_die_button);
        mTwentyDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceRolls.add(0, rollDice(diceQuantity, 20));
                updateUI();
            }
        });

        mHundredDie = view.findViewById(R.id.one_hundred_die);
        mHundredDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceRolls.add(0, rollDice(diceQuantity, 100));
                updateUI();
            }
        });
        mClearButton = view.findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceRolls.clear();
                updateUI();
            }
        });

        return view;
    }

    private class DiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mDiceResultTextView;
        private Integer mResult;

        public void bind(Integer result){
            mResult = result;
            mDiceResultTextView.setText(mResult.toString());
        }

        public DiceHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.dice_result_layout, parent, false));
            itemView.setOnClickListener(this);
            mDiceResultTextView = (TextView) itemView.findViewById(R.id.dice_result_text);
        }

        @Override
        public void onClick(View v) {
        }
    }

    private class DiceAdapter extends RecyclerView.Adapter<DiceHolder> {
        private List<Integer> mDiceResults;

        public DiceAdapter(List<Integer> results){
            mDiceResults = results;
        }

        @Override
        public DiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new DiceHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(DiceHolder holder, int position) {
            Integer dieResult = mDiceResults.get(position);
            holder.bind(dieResult);
        }

        @Override
        public int getItemCount() {
            return mDiceResults.size();
        }
    }

    private void updateUI() {
        List<Integer> results = mDiceRolls;

        if(mAdapter == null){
            mAdapter = new DiceAdapter(results);
            mDiceResultsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private int rollDice(int quantity, int sides){
        int result = 0;
        Random random = new Random();
        for(int i = 0; i < quantity; i++){
            result += (random.nextInt(sides) + 1);
        }
        return result;
    }
}
