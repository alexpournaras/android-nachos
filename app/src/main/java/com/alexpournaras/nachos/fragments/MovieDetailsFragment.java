package com.alexpournaras.nachos.fragments;

import com.alexpournaras.nachos.BuildConfig;
import com.alexpournaras.nachos.MainActivity;
import com.alexpournaras.nachos.adapters.CastAdapter;
import com.alexpournaras.nachos.adapters.VideoAdapter;
import com.alexpournaras.nachos.database.MovieEntity;
import com.alexpournaras.nachos.models.Cast;
import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;
import com.alexpournaras.nachos.models.Video;
import com.alexpournaras.nachos.services.ApiCastResponse;
import com.alexpournaras.nachos.services.ApiClient;
import com.alexpournaras.nachos.services.ApiVideoResponse;
import com.bumptech.glide.Glide;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsFragment extends Fragment {
    private static final String ARG_MOVIE = "movie";

    private Movie movie;
    private Boolean isMovieFavorite = false;

    private List<Video> videos;
    private VideoAdapter videoAdapter;
    private RecyclerView videosComponent;

    private List<Cast> cast;
    private CastAdapter castAdapter;
    private RecyclerView castComponent;

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

            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                MovieEntity movieEntity = mainActivity.getMovieById(movie.getId());
                if (movieEntity != null) {
                    isMovieFavorite = true;
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        fetchMovieVideos();
        fetchMovieCast();

        videosComponent = view.findViewById(R.id.videosComponent);
        castComponent = view.findViewById(R.id.castComponent);

        ImageView movieImage = view.findViewById(R.id.movieImage);
        String backgroundImageUrl = BuildConfig.TMDB_OIMAGE_URL + movie.getBackgroundImageUrl();
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

    private void fetchMovieVideos() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        ApiClient.ApiService apiService = ApiClient.getClient(getContext()).create(ApiClient.ApiService.class);
        Call<ApiVideoResponse> apiRequest = apiService.getMovieVideos(movie.getId(), BuildConfig.TMDB_API_KEY, sharedPreferences.getString("locale", "en"), "max-age=" + BuildConfig.CACHE_DURATION);

        apiRequest.enqueue(new Callback<ApiVideoResponse>() {

            @Override
            public void onResponse(Call<ApiVideoResponse> apiRequest, Response<ApiVideoResponse> response) {
                if (response.isSuccessful()) {
                    videos = response.body().getResults();
                    videoAdapter = new VideoAdapter(getActivity(), videos);
                    videosComponent.setAdapter(videoAdapter);

                    if (getView() != null) {
                        TextView videoTextView = getView().findViewById(R.id.videoTextView);
                        RecyclerView videosComponent = getView().findViewById(R.id.videosComponent);

                        if (videos.size() == 0) {
                            videoTextView.setVisibility(View.INVISIBLE);
                            videosComponent.setVisibility(View.INVISIBLE);
                        } else {
                            videoTextView.setVisibility(View.VISIBLE);
                            videosComponent.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    // Toast.makeText(getActivity(), "Failed to fetch videos. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiVideoResponse> apiRequest, Throwable t) {
                // Toast.makeText(getActivity(), "Failed to fetch videos. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void fetchMovieCast() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        ApiClient.ApiService apiService = ApiClient.getClient(getContext()).create(ApiClient.ApiService.class);
        Call<ApiCastResponse> apiRequest = apiService.getMovieCast(movie.getId(), BuildConfig.TMDB_API_KEY, sharedPreferences.getString("locale", "en"), "max-age=" + BuildConfig.CACHE_DURATION);

        apiRequest.enqueue(new Callback<ApiCastResponse>() {

            @Override
            public void onResponse(Call<ApiCastResponse> apiRequest, Response<ApiCastResponse> response) {
                if (response.isSuccessful()) {
                    cast = response.body().getResults();
                    castAdapter = new CastAdapter(getActivity(), cast);
                    castComponent.setAdapter(castAdapter);
                } else {
                    // Toast.makeText(getActivity(), "Failed to fetch cast. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiCastResponse> apiRequest, Throwable t) {
                // Toast.makeText(getActivity(), "Failed to fetch cast. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateToolbar(movie.getTitle(), true, true, false);

            if (isMovieFavorite) {
                mainActivity.setFavoriteMovie(true);
            } else {
                mainActivity.setFavoriteMovie(false);
            }

            mainActivity.checkInternetConnection();
        }
    }

    public void toggleFavoriteMovie() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {

            if (isMovieFavorite) {
                mainActivity.deleteMovie(movie.getId());
                mainActivity.setFavoriteMovie(false);
                Toast.makeText(getContext(), movie.getTitle() + " " + getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show();
            } else {
                mainActivity.insertMovie(movie);
                mainActivity.setFavoriteMovie(true);
                Toast.makeText(getContext(), movie.getTitle() + " " + getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
            }

            isMovieFavorite = !isMovieFavorite;
        }
    }


}
