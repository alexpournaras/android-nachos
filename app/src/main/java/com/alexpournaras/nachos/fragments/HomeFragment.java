package com.alexpournaras.nachos.fragments;

import com.alexpournaras.nachos.BuildConfig;
import com.alexpournaras.nachos.MainActivity;
import com.alexpournaras.nachos.R;
import com.alexpournaras.nachos.adapters.MovieAdapter;
import com.alexpournaras.nachos.adapters.SliderAdapter;
import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.services.ApiClient;
import com.alexpournaras.nachos.services.ApiClient.ApiService;
import com.alexpournaras.nachos.services.ApiMovieResponse;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements MovieAdapter.MovieItemClickListener {

    private List<Movie> sliderMovies;
    private SliderAdapter sliderAdapter;
    private ViewPager sliderComponent;

    private List<Movie> nowPlayingMovies;
    private MovieAdapter movieAdapter;
    private RecyclerView moviesComponent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderMovies = new ArrayList<>();
        nowPlayingMovies = new ArrayList<>();

        fetchPopularMovies();
        fetchNowPlayingMovies();

        sliderComponent = view.findViewById(R.id.sliderComponent);

        sliderAdapter = new SliderAdapter(getActivity(), sliderMovies);
        sliderAdapter.setMovieItemClickListener(new SliderAdapter.MovieItemClickListener() {
            @Override
            public void onMovieItemClick(Movie movie) {
                goToMovieDetails(movie);
            }
        });

        moviesComponent = view.findViewById(R.id.moviesComponent);
        movieAdapter = new MovieAdapter(getActivity(), nowPlayingMovies, this);
        moviesComponent.setAdapter(movieAdapter);

        return view;
    }

    private void fetchPopularMovies() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        ApiService apiService = ApiClient.getClient(getContext()).create(ApiService.class);
        Call<ApiMovieResponse> apiRequest = apiService.getPopularMovies(BuildConfig.TMDB_API_KEY, sharedPreferences.getString("locale", "en"), 1, BuildConfig.REGION, "max-age=" + BuildConfig.CACHE_DURATION);

        apiRequest.enqueue(new Callback<ApiMovieResponse>() {

            @Override
            public void onResponse(Call<ApiMovieResponse> apiRequest, Response<ApiMovieResponse> response) {

                if (response.isSuccessful()) {
                    List<Movie> popularMovies = response.body().getResults();

                    for (int i = 0; i < 5; i++) {
                        sliderMovies.add(popularMovies.get(i));
                    }

                    updateSlider();

                } else {
                    // Toast.makeText(getActivity(), "Failed to fetch movies. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiMovieResponse> apiRequest, Throwable t) {
                // Toast.makeText(getActivity(), "Failed to fetch movies. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void fetchNowPlayingMovies() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        ApiService apiService = ApiClient.getClient(getContext()).create(ApiService.class);
        Call<ApiMovieResponse> apiRequest = apiService.getNowPlayingMovies(BuildConfig.TMDB_API_KEY, sharedPreferences.getString("locale", "en"), 1, BuildConfig.REGION, "max-age=" + BuildConfig.CACHE_DURATION);

        apiRequest.enqueue(new Callback<ApiMovieResponse>() {

            @Override
            public void onResponse(Call<ApiMovieResponse> apiRequest, Response<ApiMovieResponse> response) {
                if (response.isSuccessful()) {
                    nowPlayingMovies = response.body().getFirst15Results();
                    movieAdapter = new MovieAdapter(getActivity(), nowPlayingMovies, HomeFragment.this);
                    moviesComponent.setAdapter(movieAdapter);
                } else {
                    // Toast.makeText(getActivity(), "Failed to fetch movies. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiMovieResponse> apiRequest, Throwable t) {
                // Toast.makeText(getActivity(), "Failed to fetch movies. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void updateSlider() {
        sliderAdapter.notifyDataSetChanged();
        sliderComponent.setAdapter(sliderAdapter);
        sliderComponent.setCurrentItem(sliderMovies.size() * 10);
    }

    @Override
    public void onMovieItemClick(int position) {
        goToMovieDetails(nowPlayingMovies.get(position));
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateToolbar(getString(R.string.home), false, false, false);
            mainActivity.checkInternetConnection();
        }
    }

    public void goToMovieDetails(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle);
    }

}
