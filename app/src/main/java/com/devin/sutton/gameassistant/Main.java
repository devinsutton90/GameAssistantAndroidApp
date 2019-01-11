package com.devin.sutton.gameassistant;

import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class Main extends SingleFragmentActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected Fragment createFragment() {
        return new HomeFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(R.string.app_name);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow();
        }

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.menu_dice_roller:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, DiceFragment.newInstance())
                                        .commitNow();
                                menuItem.setChecked(false);
                                break;
                            case R.id.menu_life_counter:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, LifeCounterFragment.newInstance())
                                        .commitNow();
                                menuItem.setChecked(false);
                                break;
                            case R.id.menu_turn_timer:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, TurnTimerFragment.newInstance())
                                        .commitNow();
                                menuItem.setChecked(false);
                                break;
                            case R.id.menu_initiative_tracker:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, InitiativeFragment.newInstance())
                                        .commitNow();
                                menuItem.setChecked(false);
                                break;
                        }

                        return true;
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}