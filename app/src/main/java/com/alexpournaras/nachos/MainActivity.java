package com.alexpournaras.nachos;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.alexpournaras.nachos.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private ViewPager sliderViewPager;
    private RecyclerView moviesRecyclerView;
    private SliderPagerAdapter sliderPagerAdapter;
    private MovieAdapter movieAdapter;
    private List<Movie> sliderMovies;
    private List<Movie> movieList;
    private BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderMovies = new ArrayList<>();
        movieList = new ArrayList<>();

        createDummySliderMovies();
        createDummyMovies();

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.home);

        sliderViewPager = findViewById(R.id.viewPager);
        sliderPagerAdapter = new SliderPagerAdapter(this, sliderMovies);
        sliderViewPager.setAdapter(sliderPagerAdapter);

        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        movieAdapter = new MovieAdapter(this, movieList);
        moviesRecyclerView.setAdapter(movieAdapter);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        // Handle the home action
                        break;
                    case R.id.movies:
                        // Handle the movies action
                        break;
                    case R.id.favorites:
                        // Handle the favorites action
                        break;
                    default:
                        return false;
                }
                return true;
            }

        });



    }

    private void createDummySliderMovies() {
        String[] randomTitles = {"Slider Movie A", "Slider Movie B", "Slider Movie C"};
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            String title = randomTitles[random.nextInt(randomTitles.length)];
            double rating = 1 + random.nextDouble() * 4; // Random rating between 1 and 5
            sliderMovies.add(new Movie(title, rating));
        }
    }

    private void createDummyMovies() {
        String[] randomTitles = {"Movie A", "Movie B", "Movie C", "Movie D", "Movie E", "Movie F", "Movie G", "Movie H"};
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            String title = randomTitles[random.nextInt(randomTitles.length)];
            double rating = 1 + random.nextDouble() * 4; // Random rating between 1 and 5
            movieList.add(new Movie(title, rating));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}