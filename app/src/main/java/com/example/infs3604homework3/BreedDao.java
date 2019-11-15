package com.example.infs3604homework3;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

// Basic DAO for BreedDatabase
@Dao
public interface BreedDao {

// A workaround for conflicts that arise re-inserting the same data every time
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Breed> breedList);


    @Query("Select name from breed where id = :breedID")
    String getBreedName(String breedID);


}
