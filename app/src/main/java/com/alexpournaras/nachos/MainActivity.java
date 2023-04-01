package com.alexpournaras.nachos;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private boolean showFavoritesMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();

                switch (item.getItemId()) {
                    case R.id.home:
                        navController.navigate(R.id.homeFragment);
                        break;
                    case R.id.movies:
                        navController.navigate(R.id.moviesFragment);
                        break;
                    case R.id.favorites:
                        navController.navigate(R.id.favoritesFragment);
                        break;
                    default:
                        return false;
                }
                return true;
            }

        });

    }

    public void updateToolbar(String title, boolean showBackButton, boolean showFavoritesMenu) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(showBackButton);
            actionBar.setDisplayShowHomeEnabled(showBackButton);
        }

        this.showFavoritesMenu = showFavoritesMenu;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showFavoritesMenu) {
            getMenuInflater().inflate(R.menu.toolbar_with_favorites, menu);
        } else {
            getMenuInflater().inflate(R.menu.toolbar_with_search, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
