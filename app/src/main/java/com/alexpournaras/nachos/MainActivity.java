package com.alexpournaras.nachos;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import com.alexpournaras.nachos.database.MovieDatabase;
import com.alexpournaras.nachos.database.MovieEntity;
import com.alexpournaras.nachos.fragments.FavoritesFragment;
import com.alexpournaras.nachos.fragments.HomeFragment;
import com.alexpournaras.nachos.fragments.MovieDetailsFragment;
import com.alexpournaras.nachos.fragments.MoviesFragment;
import com.alexpournaras.nachos.models.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieDatabase movieDatabase;

    private BottomNavigationView bottomNavigationView;

    private boolean showFavoritesMenu;
    private boolean showSearchMenu;

    private boolean isFavoriteMovie = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieDatabase = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "movie_nachos")
            .allowMainThreadQueries() // TODO: Change this into async tasks
            .build();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

                switch (item.getItemId()) {
                    case R.id.home:
                        if (currentFragment instanceof HomeFragment) return false;
                        navController.navigate(R.id.homeFragment);
                        break;
                    case R.id.movies:
                        if (currentFragment instanceof MoviesFragment) return false;
                        navController.navigate(R.id.moviesFragment);
                        break;
                    case R.id.favorites:
                        if (currentFragment instanceof FavoritesFragment) return false;
                        navController.navigate(R.id.favoritesFragment);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.movieDetailsFragment) {
                    bottomNavigationView.setSelectedItemId(R.id.movieDetails);
                }
            }
        });

    }

    public void updateToolbar(String title, boolean showBackButton, boolean showFavoritesMenu, Boolean showSearchMenu) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(showBackButton);
            actionBar.setDisplayShowHomeEnabled(showBackButton);
        }

        this.showFavoritesMenu = showFavoritesMenu;
        this.showSearchMenu = showSearchMenu;
        invalidateOptionsMenu();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (showFavoritesMenu) {
            MenuItem favoritesItem = menu.findItem(R.id.favorites);
            if (favoritesItem != null) {
                Drawable icon;
                int colorId;
                if (isFavoriteMovie) {
                    icon = getDrawable(R.drawable.icon_star);
                    colorId = R.color.main;
                } else {
                    icon = getDrawable(R.drawable.icon_star_outline);
                    colorId = R.color.white;
                }
                icon.setTint(ContextCompat.getColor(this, colorId));
                favoritesItem.setIcon(icon);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void setFavoriteMovie(Boolean isFavoriteMovie) {
        this.isFavoriteMovie = isFavoriteMovie;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showFavoritesMenu) {
            getMenuInflater().inflate(R.menu.toolbar_with_favorites, menu);
        } else if (showSearchMenu) {
            getMenuInflater().inflate(R.menu.toolbar_with_search, menu);

            MenuItem searchItem = menu.findItem(R.id.search);
            SearchView searchView = (SearchView) searchItem.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

                    if (currentFragment instanceof MoviesFragment) {
                        ((MoviesFragment) currentFragment).setSearchInputQuery(query);
                    }

                    return false;
                }
            });
        } else {
            getMenuInflater().inflate(R.menu.toolbar, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.favorites) {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

            if (currentFragment instanceof MovieDetailsFragment) {
                ((MovieDetailsFragment) currentFragment).toggleFavoriteMovie();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        if (navController.getPreviousBackStackEntry() != null) {
            navController.popBackStack();

            if (navController.getCurrentDestination().getId() == R.id.homeFragment) {
                bottomNavigationView.setSelectedItemId(R.id.home);
            } else if (navController.getCurrentDestination().getId() == R.id.moviesFragment) {
                bottomNavigationView.setSelectedItemId(R.id.movies);
            } else if (navController.getCurrentDestination().getId() == R.id.favoritesFragment) {
                bottomNavigationView.setSelectedItemId(R.id.favorites);
            }
        } else {
            super.onBackPressed();
        }
    }

    public void insertMovie(Movie movie) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movie.getId());
        movieEntity.setTitle(movie.getTitle());
        movieEntity.setReleaseDate(movie.getReleaseDate());
        movieEntity.setDescription(movie.getDescription());
        movieEntity.setRating(movie.getRating());
        movieEntity.setVotes(movie.getVotes());
        movieEntity.setPosterImageUrl(movie.getPosterImageUrl());
        movieEntity.setBackgroundImageUrl(movie.getBackgroundImageUrl());

        movieDatabase.movieDao().insert(movieEntity);
    }

    public MovieEntity getMovieById(int id) {
        return movieDatabase.movieDao().getMovieById(id);
    }

    public List<MovieEntity> getAllMovies() {
        return movieDatabase.movieDao().getAllMovies();
    }

    public void deleteMovie(int id) {
        movieDatabase.movieDao().delete(id);
    }

}
