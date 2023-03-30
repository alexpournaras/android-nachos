package com.alexpournaras.nachos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.alexpournaras.nachos.adapters.MovieAdapter;
import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;

public class FavoritesFragment extends Fragment {

    private ActionBar actionBar;
    private MovieAdapter movieAdapter;
    private RecyclerView moviesRecyclerView;


    private List<Movie> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        movieList = new ArrayList<>();

        createDummyMovies();


        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.favorites);

//        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
//        movieAdapter = new MovieAdapter(getActivity(), movieList, this);
//        moviesRecyclerView.setAdapter(movieAdapter);

        return view;
    }

    private void createDummyMovies() {
        String[] randomTitles = {"Movie A", "Movie B", "Movie C", "Movie D", "Movie E", "Movie F", "Movie G", "Movie H"};
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            String title = randomTitles[random.nextInt(randomTitles.length)];
            double rating = 1 + random.nextDouble() * 4; // Random rating between 1 and 5
            movieList.add(new Movie(title, rating, ""));
        }
    }

}