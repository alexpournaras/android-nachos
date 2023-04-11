package com.alexpournaras.nachos.fragments;

import com.alexpournaras.nachos.BuildConfig;
import com.alexpournaras.nachos.MainActivity;
import com.alexpournaras.nachos.adapters.MovieAdapter;
import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;
import com.alexpournaras.nachos.services.ApiClient;
import com.alexpournaras.nachos.services.ApiMovieResponse;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
    private List<Movie> searchedMovies;
    private String searchInputQuery = "";
    private MovieAdapter movieAdapter;
    private RecyclerView moviesComponent;
    private EndlessScrollListener endlessScrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        endlessScrollListener = new EndlessScrollListener(gridLayoutManager);

        popularMovies = new ArrayList<>();
        searchedMovies = new ArrayList<>();

        fetchPopularMovies();

        moviesComponent = view.findViewById(R.id.moviesComponent);
        movieAdapter = new MovieAdapter(getActivity(), popularMovies, this);
        moviesComponent.setAdapter(movieAdapter);
        moviesComponent.setLayoutManager(gridLayoutManager);
        moviesComponent.addOnScrollListener(endlessScrollListener);

        searchInputQuery = "";

        return view;
    }

    private void fetchPopularMovies() {
        int page = popularMovies.size() / 20 + 1;

        ApiClient.ApiService apiService = ApiClient.getClient(getContext()).create(ApiClient.ApiService.class);
        Call<ApiMovieResponse> apiRequest = apiService.getPopularMovies(BuildConfig.API_KEY, "en", page, "GR", "max-age=300");

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

    private void fetchSearchedMovies() {
        ApiClient.ApiService apiService = ApiClient.getClient(getContext()).create(ApiClient.ApiService.class);
        Call<ApiMovieResponse> apiRequest = apiService.searchMovies(BuildConfig.API_KEY, "en", searchInputQuery, 1, "max-age=300");

        apiRequest.enqueue(new Callback<ApiMovieResponse>() {

            @Override
            public void onResponse(Call<ApiMovieResponse> apiRequest, Response<ApiMovieResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = response.body().getFirst15Results();

                    for (Movie movie : movies) {
                        if (!searchedMovies.contains(movie)) {
                            searchedMovies.add(movie);
                        }
                    }

                    movieAdapter.updateMovies(searchedMovies);
                    TextView noResultsFound = getView().findViewById(R.id.noResultsFound);
                    ImageView watchingMovieImage = getView().findViewById(R.id.watchingMovieImage);

                    if (searchedMovies.size() > 0) {
                        moviesComponent.smoothScrollToPosition(0);
                        noResultsFound.setVisibility(View.INVISIBLE);
                        watchingMovieImage.setVisibility(View.INVISIBLE);
                    } else {
                        noResultsFound.setVisibility(View.VISIBLE);
                        watchingMovieImage.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(getActivity(), "Failed to search movies. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiMovieResponse> apiRequest, Throwable t) {
                Toast.makeText(getActivity(), "Failed to search movies. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMovieItemClick(int position) {
        Movie selectedMovie;

        if (searchInputQuery.length() >= 2) {
            selectedMovie = searchedMovies.get(position);
        } else {
            selectedMovie = popularMovies.get(position);
        }

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
            mainActivity.updateToolbar("Movies", false, false, true);
        }
    }

    public void setSearchInputQuery(String query) {
        searchInputQuery = query;

        if (query.length() >= 2) {
            searchedMovies.clear();
            fetchSearchedMovies();
        } else {
            movieAdapter.updateMovies(popularMovies);
            moviesComponent.smoothScrollToPosition(0);
        }
    }

    private class EndlessScrollListener extends RecyclerView.OnScrollListener {
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

                if (searchInputQuery.length() < 2) {
                    fetchPopularMovies();
                }
            }
        }

        public void setLoading(boolean loading) {
            isLoading = loading;
        }
    }

}
