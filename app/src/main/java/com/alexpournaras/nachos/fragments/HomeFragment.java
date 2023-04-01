package com.alexpournaras.nachos.fragments;

import com.alexpournaras.nachos.BuildConfig;
import com.alexpournaras.nachos.MainActivity;
import com.alexpournaras.nachos.R;
import com.alexpournaras.nachos.adapters.MovieAdapter;
import com.alexpournaras.nachos.adapters.SliderAdapter;
import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.services.ApiClient;
import com.alexpournaras.nachos.services.ApiClient.ApiService;
import com.alexpournaras.nachos.services.ApiResponse;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
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

        moviesComponent = view.findViewById(R.id.moviesComponent);
        movieAdapter = new MovieAdapter(getActivity(), nowPlayingMovies, this);
        moviesComponent.setAdapter(movieAdapter);

        return view;
    }

    private void fetchPopularMovies() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ApiResponse> apiRequest = apiService.getPopularMovies(BuildConfig.API_KEY, "en", 1, "GR");

        apiRequest.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(Call<ApiResponse> apiRequest, Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    List<Movie> popularMovies = response.body().getResults();

                    for (int i = 0; i < 5; i++) {
                        sliderMovies.add(popularMovies.get(i));
                    }

                    updateSlider();

                } else {
                    Toast.makeText(getActivity(), "Failed to fetch movies. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> apiRequest, Throwable t) {
                Toast.makeText(getActivity(), "Failed to fetch movies. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void fetchNowPlayingMovies() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ApiResponse> apiRequest = apiService.getNowPlayingMovies(BuildConfig.API_KEY, "en", 1, "GR");

        apiRequest.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(Call<ApiResponse> apiRequest, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    nowPlayingMovies = response.body().getResults();
                    movieAdapter = new MovieAdapter(getActivity(), nowPlayingMovies, HomeFragment.this);
                    moviesComponent.setAdapter(movieAdapter);
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch movies. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> apiRequest, Throwable t) {
                Toast.makeText(getActivity(), "Failed to fetch movies. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void updateSlider() {
        sliderAdapter.notifyDataSetChanged();
        sliderComponent.setAdapter(sliderAdapter);
    }

    @Override
    public void onMovieItemClick(int position) {
        Movie selectedMovie = nowPlayingMovies.get(position);
        Toast.makeText(getActivity(), "Selected movie: " + selectedMovie.getTitle() + ", rating: " + selectedMovie.getRating(), Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", selectedMovie);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateToolbar("Home", false);
        }
    }

}
