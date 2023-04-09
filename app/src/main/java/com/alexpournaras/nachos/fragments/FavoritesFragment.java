package com.alexpournaras.nachos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.stream.Collectors;

import com.alexpournaras.nachos.MainActivity;
import com.alexpournaras.nachos.adapters.MovieAdapter;
import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;

public class FavoritesFragment extends Fragment implements MovieAdapter.MovieItemClickListener {

    private List<Movie> favoriteMovies;
    private MovieAdapter movieAdapter;
    private RecyclerView moviesComponent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {

            favoriteMovies = mainActivity.getAllMovies().stream()
                    .map(movieEntity -> new Movie(
                            movieEntity.getId(),
                            movieEntity.getTitle(),
                            movieEntity.getReleaseDate(),
                            movieEntity.getDescription(),
                            movieEntity.getRating(),
                            movieEntity.getVotes(),
                            movieEntity.getPosterImageUrl(),
                            movieEntity.getBackgroundImageUrl()
                    ))
                    .collect(Collectors.toList());

            moviesComponent = view.findViewById(R.id.moviesComponent);
            movieAdapter = new MovieAdapter(getActivity(), favoriteMovies, this);
            moviesComponent.setAdapter(movieAdapter);

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateToolbar("Favourites", false, false, false);
        }
    }

    @Override
    public void onMovieItemClick(int position) {
        Movie selectedMovie = favoriteMovies.get(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", selectedMovie);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_favoritesFragment_to_movieDetailsFragment, bundle);
    }

}