package com.alexpournaras.nachos.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity movie);

    @Query("SELECT * FROM favorite_movies")
    List<MovieEntity> getAllMovies();

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    MovieEntity getMovieById(int id);

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    void delete(int id);
}
