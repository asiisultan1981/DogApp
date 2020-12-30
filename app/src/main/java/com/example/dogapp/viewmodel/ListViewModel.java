package com.example.dogapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dogapp.model.DogBreedModel;
import com.example.dogapp.retrofit.DogsApiService;
import com.example.dogapp.util.DogsRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;



public class ListViewModel extends AndroidViewModel {

    private static final String TAG = "lViewModel";
    DogsRepository dogsRepository = new DogsRepository(getApplication());

    public ListViewModel(@NonNull Application application) {
        super(application);


    }

    public MutableLiveData<List<DogBreedModel>> listMutableLiveData = dogsRepository.mutableDogsBreedList;
    public MutableLiveData<Boolean> mutableLoading = dogsRepository.mutableLoading;
    public MutableLiveData<Boolean> mutableDogLoadError = dogsRepository.mutableDogLoadError;

    public void refresh() {
        dogsRepository.fetchFromDatabase();
    }

    @Override
    protected void onCleared() {

        super.onCleared();
        if (!dogsRepository.disposable.isDisposed()) {

            dogsRepository.disposable.dispose();
            dogsRepository.disposable.clear();
            Log.i(TAG, "compositeDisposable disposed");
        }
    }
}
