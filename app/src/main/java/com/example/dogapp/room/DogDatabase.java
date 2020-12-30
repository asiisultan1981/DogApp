package com.example.dogapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dogapp.model.DogBreedModel;

@Database(entities = {DogBreedModel.class}, version = 1, exportSchema = false)
public abstract class DogDatabase extends RoomDatabase {

    private static DogDatabase INSTANCE = null;

    public static DogDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DogDatabase.class, "dogdatabase").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DogDao dogDao();


}


