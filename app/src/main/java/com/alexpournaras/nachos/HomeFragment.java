package com.alexpournaras.nachos;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;



public class HomeFragment extends Fragment {

    private ViewPager sliderViewPager;
    private RecyclerView moviesRecyclerView;
    private SliderPagerAdapter sliderPagerAdapter;
    private MovieAdapter movieAdapter;
    private List<Movie> sliderMovies;
    private List<Movie> movieList;
    private BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        sliderMovies = new ArrayList<>();
        movieList = new ArrayList<>();

        createDummySliderMovies();
        createDummyMovies();

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.home);

        sliderViewPager = view.findViewById(R.id.viewPager);
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), sliderMovies);
        sliderViewPager.setAdapter(sliderPagerAdapter);

        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        movieAdapter = new MovieAdapter(getActivity(), movieList);
        moviesRecyclerView.setAdapter(movieAdapter);

        return view;
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

        for (int i = 0; i < 12; i++) {
            String title = randomTitles[random.nextInt(randomTitles.length)];
            double rating = 1 + random.nextDouble() * 4; // Random rating between 1 and 5
            movieList.add(new Movie(title, rating));
        }
    }
}
