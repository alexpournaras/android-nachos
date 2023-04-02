package com.alexpournaras.nachos.fragments;

import com.alexpournaras.nachos.MainActivity;
import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;
import com.bumptech.glide.Glide;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;

public class MovieDetailsFragment extends Fragment {
    private static final String ARG_MOVIE = "movie";

    private Movie movie;

    public MovieDetailsFragment() {
    }

    public static MovieDetailsFragment newInstance(Movie movie) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("movie");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        ImageView movieImage = view.findViewById(R.id.movieImage);
        String backgroundImageUrl = "https://image.tmdb.org/t/p/original" + movie.getBackgroundImageUrl();
        Glide.with(this)
            .load(backgroundImageUrl)
            .into(movieImage);

        AppCompatRatingBar ratingBar = view.findViewById(R.id.ratingBar);
        float rating = (float) (movie.getRating() / 2);
        ratingBar.setRating(rating);

        TextView ratingText = view.findViewById(R.id.ratingText);
        double ratingPercentage = movie.getRating() / 10 * 100;
        ratingText.setText(String.valueOf((int)ratingPercentage));

        TextView movieTitle = view.findViewById(R.id.movieTitle);
        String year = movie.getReleaseDate().substring(0, 4);

        SpannableStringBuilder spannableString = new SpannableStringBuilder(movie.getTitle() + " (" + year + ")");
        spannableString.setSpan(new AbsoluteSizeSpan(32, true), 0, movie.getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(16, true), movie.getTitle().length(), spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        movieTitle.setText(spannableString);

        TextView movieDescription = view.findViewById(R.id.movieDescription);
        movieDescription.setText(movie.getDescription());

        TextView votesNumber = view.findViewById(R.id.votesNumber);
        votesNumber.setText(String.valueOf(movie.getVotes()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateToolbar(movie.getTitle(), true, true);
        }
    }

}
