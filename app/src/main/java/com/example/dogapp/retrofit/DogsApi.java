package com.example.dogapp.retrofit;

import com.example.dogapp.model.DogBreedModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface DogsApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    Single<List<DogBreedModel>> getDogs();
}
