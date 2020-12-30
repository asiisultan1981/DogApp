package com.example.dogapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dogapp.model.DogBreedModel;

import java.util.List;

@Dao
public interface DogDao {
    @Insert
    List<Long> insertAll(DogBreedModel... dogBreeds);

    @Query("SELECT * FROM dogbreedmodel")
    List<DogBreedModel> getAllDogs();

    @Query("SELECT * FROM dogbreedmodel WHERE dogBreedUuid = :dogId")
    DogBreedModel getDog(int dogId);

    @Query("DELETE FROM dogbreedmodel")
    void deleteAllDogs();
}
