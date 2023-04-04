package com.alexpournaras.nachos.fragments;

import com.alexpournaras.nachos.BuildConfig;
import com.alexpournaras.nachos.MainActivity;
import com.alexpournaras.nachos.adapters.MovieAdapter;
import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;
import com.alexpournaras.nachos.services.ApiClient;
import com.alexpournaras.nachos.services.ApiMovieResponse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    private EndlessScrollListener endlessScrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        endlessScrollListener = new EndlessScrollListener(gridLayoutManager);

        popularMovies = new ArrayList<>();

        fetchPopularMovies(1);

        moviesComponent = view.findViewById(R.id.moviesComponent);
        movieAdapter = new MovieAdapter(getActivity(), popularMovies, this);
        moviesComponent.setAdapter(movieAdapter);
        moviesComponent.setLayoutManager(gridLayoutManager);
        moviesComponent.addOnScrollListener(endlessScrollListener);

        return view;
    }

    private void fetchPopularMovies(int page) {
        ApiClient.ApiService apiService = ApiClient.getClient().create(ApiClient.ApiService.class);
        Call<ApiMovieResponse> apiRequest = apiService.getPopularMovies(BuildConfig.API_KEY, "en", page, "GR");

        apiRequest.enqueue(new Callback<ApiMovieResponse>() {

            @Override
            public void onResponse(Call<ApiMovieResponse> apiRequest, Response<ApiMovieResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getResults();
                    popularMovies.addAll(movies);
                    movieAdapter.notifyDataSetChanged();
                    endlessScrollListener.setLoading(false);
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch movies. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiMovieResponse> apiRequest, Throwable t) {
                Toast.makeText(getActivity(), "Failed to fetch movies. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onMovieItemClick(int position) {
        Movie selectedMovie = popularMovies.get(position);

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

    private class EndlessScrollListener extends RecyclerView.OnScrollListener {
        private int currentPage = 1;
        private boolean isLoading = false;
        private LinearLayoutManager layoutManager;

        public EndlessScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                isLoading = true;
                currentPage++;
                fetchPopularMovies(currentPage);
            }
        }

        public void setLoading(boolean loading) {
            isLoading = loading;
        }
    }

}
