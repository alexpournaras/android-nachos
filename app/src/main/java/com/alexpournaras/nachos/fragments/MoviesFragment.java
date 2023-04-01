package com.alexpournaras.nachos.fragments;

import com.alexpournaras.nachos.BuildConfig;
import com.alexpournaras.nachos.MainActivity;
import com.alexpournaras.nachos.adapters.MovieAdapter;
import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;
import com.alexpournaras.nachos.services.ApiClient;
import com.alexpournaras.nachos.services.ApiResponse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment implements MovieAdapter.MovieItemClickListener {

    private List<Movie> popularMovies;
    private MovieAdapter movieAdapter;
    private RecyclerView moviesComponent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        popularMovies = new ArrayList<>();

        fetchPopularMovies();

        moviesComponent = view.findViewById(R.id.moviesComponent);
        movieAdapter = new MovieAdapter(getActivity(), popularMovies, this);
        moviesComponent.setAdapter(movieAdapter);

        return view;
    }

    private void fetchPopularMovies() {
        ApiClient.ApiService apiService = ApiClient.getClient().create(ApiClient.ApiService.class);
        Call<ApiResponse> apiRequest = apiService.getPopularMovies(BuildConfig.API_KEY, "en", 1, "GR");

        apiRequest.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(Call<ApiResponse> apiRequest, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    popularMovies = response.body().getResults();
                    movieAdapter = new MovieAdapter(getActivity(), popularMovies, MoviesFragment.this);
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

    @Override
    public void onMovieItemClick(int position) {
        Movie selectedMovie = popularMovies.get(position);
        Toast.makeText(getActivity(), "Selected movie: " + selectedMovie.getTitle() + ", rating: " + selectedMovie.getRating(), Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", selectedMovie);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_moviesFragment_to_movieDetailsFragment, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateToolbar("Movies", false, false);
        }
    }

}
