package com.alexpournaras.nachos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieDetailsFragment extends Fragment {

    private ActionBar actionBar;
    private MovieAdapter movieAdapter;
    private RecyclerView moviesRecyclerView;


    private List<Movie> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            float rating = getArguments().getFloat("rating");
            // Retrieve more data from the bundle as needed

            Toast.makeText(getContext(), "Title: " + title + ", Rating: " + rating, Toast.LENGTH_LONG).show();
        }

        return view;
    }



}
