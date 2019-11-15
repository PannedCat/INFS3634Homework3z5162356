package com.example.infs3604homework3;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Basic Room Database for the Breed Class

@Database(entities = {Breed.class}, version = 1)
public abstract class BreedDatabase extends RoomDatabase {
    public abstract BreedDao breedDao();

    private static BreedDatabase instance;
    public static BreedDatabase getInstance(Context context) {

        if(instance == null) {
            instance = Room.databaseBuilder(context, BreedDatabase.class, "breedInfoDb")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}