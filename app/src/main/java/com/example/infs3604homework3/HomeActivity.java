package com.example.infs3604homework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Setting up toolbar and changing its name
        toolbar = getSupportActionBar();
        toolbar.setTitle("Search");

        // Setting up BottomNavigationView and associated action on selection
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationBar);
        navigationView.setOnNavigationItemSelectedListener(selectNav);

        // Initialising the initial fragment, HomeSearchFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.homeBaseFrame, new HomeSearchFragment());
        fragmentTransaction.commit();
    }

    // Method that dictates what happens when the BottomNavigationView elements are selected
    private BottomNavigationView.OnNavigationItemSelectedListener selectNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.searchPage:
                    toolbar.setTitle("Search");
                    fragment = new HomeSearchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.favouritesPage:
                    toolbar.setTitle("Favourites");
                    fragment = new FavouritesFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.homeBaseFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}
