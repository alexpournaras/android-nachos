package com.alexpournaras.nachos;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements MovieAdapter.MovieItemClickListener {

    private ViewPager sliderViewPager;
    private RecyclerView moviesRecyclerView;
    private SliderPagerAdapter sliderPagerAdapter;
    private MovieAdapter movieAdapter;
    private List<Movie> sliderMovies;
    private List<Movie> movieList;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderMovies = new ArrayList<>();
        movieList = new ArrayList<>();

        createDummySliderMovies();
        fetchPopularMovies();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().setTitle(R.string.home);
        }

        sliderViewPager = view.findViewById(R.id.viewPager);
        sliderPagerAdapter = new SliderPagerAdapter(getActivity(), sliderMovies);
        sliderViewPager.setAdapter(sliderPagerAdapter);
        sliderViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % sliderMovies.size()));

        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        movieAdapter = new MovieAdapter(getActivity(), movieList, this);
        moviesRecyclerView.setAdapter(movieAdapter);

        return view;
    }

    private void fetchPopularMovies() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String apiKey = "9294941597b6fe907a02e1b94c646b17";
        Call<MovieApiResponse> call = apiService.getPopularMovies(apiKey, "en", 1);
        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                if (response.isSuccessful()) {
                    movieList = response.body().getResults();
                    movieAdapter = new MovieAdapter(getActivity(), movieList, HomeFragment.this);
                    moviesRecyclerView.setAdapter(movieAdapter);
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch movies. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to fetch movies. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createDummySliderMovies() {
        String[] randomTitles = {"Slider Movie A", "Slider Movie B", "Slider Movie C"};
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            String title = randomTitles[random.nextInt(randomTitles.length)];
            double rating = 1 + random.nextDouble() * 4; // Random rating between 1 and 5
            sliderMovies.add(new Movie(title, rating, ""));
        }
    }

    @Override
    public void onMovieItemClick(int position) {
        Movie selectedMovie = movieList.get(position);
        Toast.makeText(getActivity(), "Selected movie: " + selectedMovie.getTitle() + ", rating: " + selectedMovie.getRating(), Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", selectedMovie);

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle);
    }

}
