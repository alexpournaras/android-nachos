package com.alexpournaras.nachos.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alexpournaras.nachos.database.MovieDao;
import com.alexpournaras.nachos.database.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
