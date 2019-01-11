package com.devin.sutton.gameassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class InitiativeFragment extends Fragment {

    private Button mNextButton;
    private RecyclerView mInitiativeRecyclerView;
    private TextView mCurrentPlayer;

    private List<Player> mPlayerList;
    private int currentPlayerPosition;

    private PlayerAdapter mAdapter;

    public static InitiativeFragment newInstance() {
        return new InitiativeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.initiative_tracker_layout, container, false);
        setHasOptionsMenu(true);
        currentPlayerPosition = -1;

        mCurrentPlayer = view.findViewById(R.id.current_player_text_view);

        mNextButton = view.findViewById(R.id.next_turn_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mPlayerList.isEmpty()){
                    currentPlayerPosition ++;
                    if (currentPlayerPosition == mPlayerList.size()) currentPlayerPosition = 0;
                    mCurrentPlayer.setText(mPlayerList.get(currentPlayerPosition).getName());
                }
            }
        });

        mPlayerList = new ArrayList<Player>();
        mInitiativeRecyclerView = (RecyclerView) view.findViewById(R.id.initiative_recycler_view);
        mInitiativeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private class PlayerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String name;
        private int initiative;
        private TextView mPlayerName;
        private TextView mPlayerInitiative;

        public void bind(Player player){
            initiative = player.getInitiative();
            name = player.getName();
            mPlayerInitiative.setText(String.valueOf(initiative));
            mPlayerName.setText(name);
            if(player.isMonster()) mPlayerName.setTextColor(Color.RED);
            if(!player.isMonster()) mPlayerName.setTextColor(Color.BLACK);
        }

        public PlayerHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.initiative_item_layout, parent, false));
            itemView.setOnClickListener(this);
            mPlayerInitiative = (TextView) itemView.findViewById(R.id.player_initiative);
            mPlayerName = (TextView) itemView.findViewById(R.id.player_name);
        }

        @Override
        public void onClick(View v) {
        }
    }

    private class PlayerAdapter extends RecyclerView.Adapter<PlayerHolder> {
        private List<Player> mPlayerList;

        public PlayerAdapter(List<Player> players){
            mPlayerList = players;
        }

        @Override
        public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PlayerHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PlayerHolder holder, int position) {
            Player player = mPlayerList.get(position);
            holder.bind(player);
        }

        @Override
        public int getItemCount() {
            return mPlayerList.size();
        }
    }

    private void updateUI() {
        List<Player> players = mPlayerList;

        if(mAdapter == null){
            mAdapter = new PlayerAdapter(players);
            mInitiativeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.initiative_options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_monster_menu_item:
                AlertDialog.Builder builderAddMonster = new AlertDialog.Builder(getContext());
                builderAddMonster.setTitle("Add Monster");
                final View alertViewMonster = getLayoutInflater().inflate(R.layout.add_player_alert_layout, null);
                builderAddMonster.setView(alertViewMonster);
                builderAddMonster.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText nameEditText = alertViewMonster.findViewById(R.id.name_edit_text);
                        EditText initiativeEditText = alertViewMonster.findViewById(R.id.initiative_edit_text);
                        if(nameEditText.getText().length() != 0 && initiativeEditText.getText().length() != 0){
                            Player newMonster = new Player(nameEditText.getText().toString(), Integer.valueOf(initiativeEditText.getText().toString()));
                            newMonster.setIsMonster(true);
                            mPlayerList.add(newMonster);
                            Collections.sort(mPlayerList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                builderAddMonster.setCancelable(true);
                AlertDialog alertMonster = builderAddMonster.create();
                alertMonster.show();

                return true;
            case R.id.add_player_menu_item:
                AlertDialog.Builder builderAddPlayer = new AlertDialog.Builder(getContext());
                builderAddPlayer.setTitle("Add Player");
                final View alertViewAddPlayer = getLayoutInflater().inflate(R.layout.add_player_alert_layout, null);
                builderAddPlayer.setView(alertViewAddPlayer);
                builderAddPlayer.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText nameEditText = alertViewAddPlayer.findViewById(R.id.name_edit_text);
                        EditText initiativeEditText = alertViewAddPlayer.findViewById(R.id.initiative_edit_text);
                        if(nameEditText.getText().length() != 0 && initiativeEditText.getText().length() != 0){
                            Player newPlayer = new Player(nameEditText.getText().toString(), Integer.valueOf(initiativeEditText.getText().toString()));
                            newPlayer.setIsMonster(false);
                            mPlayerList.add(newPlayer);
                            Collections.sort(mPlayerList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                builderAddPlayer.setCancelable(true);
                AlertDialog alertPlayer = builderAddPlayer.create();
                alertPlayer.show();
                return true;
            case R.id.reset_initiative_menu_item:
                currentPlayerPosition = -1;
                mPlayerList.clear();
                mAdapter.notifyDataSetChanged();
                return true;
            default: return true;
        }
    }

    private class Player implements Comparable<Player>{
        private String name;
        private int initiative;
        private boolean isMonster;
        Player(String name, int initiative){
            this.name = name;
            this.initiative = initiative;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getInitiative() {
            return initiative;
        }

        public void setInitiative(int initiative) {
            this.initiative = initiative;
        }

        public boolean isMonster() {
            return isMonster;
        }

        public void setIsMonster(boolean monster) {
            isMonster = monster;
        }

        @Override
        public int compareTo(@NonNull Player o) {
            return o.getInitiative() - this.getInitiative();
        }
    }

}
