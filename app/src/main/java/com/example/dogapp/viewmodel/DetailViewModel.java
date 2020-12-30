package com.example.dogapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogapp.model.DogBreedModel;
import com.example.dogapp.util.DogsRepository;

public class DetailViewModel extends AndroidViewModel {

    private DogsRepository dogsRepository;

    public DetailViewModel(Application application) {
        super(application);
        this.dogsRepository = new DogsRepository(getApplication());
    }

    public LiveData<DogBreedModel> getLiveData(){
        return dogsRepository.liveData();
    }

}
