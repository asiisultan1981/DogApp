package com.example.dogapp.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dogapp.model.DogBreedModel;
import com.example.dogapp.retrofit.DogsApiService;
import com.example.dogapp.room.DogDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static java.lang.Thread.currentThread;

public class DogsRepository {
    private static final String TAG = "repo";
    public MutableLiveData<List<DogBreedModel>> mutableDogsBreedList = new MutableLiveData<>();
    public MutableLiveData<Boolean> mutableDogLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> mutableLoading = new MutableLiveData<>();
    private MutableLiveData<DogBreedModel> dogLiveData;
    private DogsApiService dogsApiService;
    private DogDatabase dogDatabase;

    public Context context;
    private Activity myActivity;

    public CompositeDisposable disposable = new CompositeDisposable();
    private ExecutorService es = Executors.newSingleThreadExecutor();


    public DogsRepository(Context context, Activity myActivity) {
        this.context = context;
        this.myActivity = myActivity;
    }

    public static void insertToast(String string ,Activity myActivity, Context context){
        if (myActivity != null){
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, string,Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    public DogsRepository(Context context) {
        this.context = context;
        dogDatabase = DogDatabase.getInstance(context);
    }


    public void deleteAllFromDatabase() {
        mutableLoading.setValue(true);
        deleteAllDogs();
        mutableLoading.setValue(false);
        Log.i(TAG, "fetchFromDatabase: Dogs Have deleted from Database");
    }

    public void fetchFromDatabase() {
        mutableLoading.setValue(true);
//        Toast.makeText(context,"Fetching the dogs from database",Toast.LENGTH_LONG).show();
        getAllDogs();


        Log.i(TAG, "fetchFromDatabase: Dogs Have received from Database");

    }

    public void fetchFromRemote() {
        mutableLoading.setValue(true);
//        Toast.makeText(context,"Fetching the dogs from API",Toast.LENGTH_LONG).show();

        disposable.add(
                getApi()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreedModel>>() {
                            @Override
                            public void onSuccess(List<DogBreedModel> dogBreedModels) {
                                insertDogs(dogBreedModels);
//                                Toast.makeText(context,"successfully loaded",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mutableDogLoadError.setValue(true);
                                mutableLoading.setValue(false);
                                e.printStackTrace();

                            }
                        })

        );

    }


    public Single<List<DogBreedModel>> getApi() {
        dogsApiService = new DogsApiService();
        return dogsApiService.getDogs();
    }

    public LiveData<DogBreedModel> liveData() {
        dogLiveData = new MutableLiveData<>();
        fetch();
        return dogLiveData;
    }

    public void fetch() {
        DogBreedModel dog1 = new DogBreedModel("1", "jony", "30 years", "", "alsetion", "thrice", "");
        DogBreedModel dog2 = new DogBreedModel("2", "bony", "40 years", "", "lucci", "twice", "");
        DogBreedModel dog3 = new DogBreedModel("2", "bony", "40 years", "", "lucci", "twice", "");
        DogBreedModel dog4 = new DogBreedModel("2", "bony", "40 years", "", "lucci", "twice", "");
        DogBreedModel dog5 = new DogBreedModel("2", "bony", "40 years", "", "lucci", "twice", "");
        DogBreedModel dog6 = new DogBreedModel("2", "bony", "40 years", "", "lucci", "twice", "");

        dogLiveData.setValue(dog1);
        dogLiveData.setValue(dog2);
        dogLiveData.setValue(dog3);
        dogLiveData.setValue(dog4);
        dogLiveData.setValue(dog5);
        dogLiveData.setValue(dog6);
    }

    private void dogsRetrieved(List<DogBreedModel> dogsList) {
        mutableDogsBreedList.setValue(dogsList);
        mutableLoading.setValue(false);
        mutableDogLoadError.setValue(false);
    }

    private void insertDogs(List<DogBreedModel>... lists) {
        Future<List<DogBreedModel>> future = es.submit(() -> {
            // do some work to get this list
            List<DogBreedModel> list = lists[0];
            dogDatabase.dogDao().deleteAllDogs();

            List<DogBreedModel> newList = new ArrayList<>(list);
            List<Long> resultedList = dogDatabase.dogDao().insertAll(newList.toArray(new DogBreedModel[0]));

            int i = 0;
            while (i < list.size()) {
                list.get(i).dogBreedUuid = resultedList.get(i).intValue();
                i++;
//                Log.i(TAG, "DogsInserted " + list.get(i).getDogBreedUuid());
            }
            Log.i(TAG, "executor: " + currentThread().getName());


            return list;

        });
        List<DogBreedModel> dogBreedModelList;
        try {
            dogBreedModelList = future.get();
            dogsRetrieved(dogBreedModelList);
            Log.i(TAG, "dogs Retrieved on: " + currentThread().getName());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getAllDogs() {
        Future<List<DogBreedModel>> future = es.submit(() -> {
            // do some work to get this list
            Log.i(TAG, "executor: " + currentThread().getName());
            return dogDatabase.dogDao().getAllDogs();

        });
        List<DogBreedModel> dogBreedModelList;
        try {

            dogBreedModelList = future.get();
            dogsRetrieved(dogBreedModelList);
            if (dogBreedModelList.size() == 0) {
                Log.i(TAG, "getAllDogs: database is empty");
                insertToast("database is empty",myActivity,context);
//                Toast.makeText(context,"database is empty",Toast.LENGTH_LONG).show();
            } else {
                Log.i(TAG, "Database has size of " + dogBreedModelList.size());
                insertToast("Database has a list of: "+dogBreedModelList.size(),myActivity,context);
//                Toast.makeText(context,"Database has a list of: "+dogBreedModelList.size(),Toast.LENGTH_LONG).show();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();

        }
    }

    private void deleteAllDogs() {

        es.execute(new Runnable() {
            @Override
            public void run() {
                dogDatabase.dogDao().deleteAllDogs();

            }
        });

        insertToast("Dogs deleted successfully",myActivity,context);

    }


}
