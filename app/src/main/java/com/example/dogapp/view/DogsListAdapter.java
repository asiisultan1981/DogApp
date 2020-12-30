package com.example.dogapp.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.util.Util;
import com.example.dogapp.R;
import com.example.dogapp.databinding.ItemDogBinding;
import com.example.dogapp.model.DogBreedModel;
import com.example.dogapp.util.LoadImage;

import java.util.ArrayList;
import java.util.List;

public class DogsListAdapter extends ListAdapter<DogBreedModel, DogsListAdapter.DogViewHolder>{
    private static final String TAG = "adapter";
    DogsListInterface dogsListInterface;

    protected DogsListAdapter() {
        super(DogBreedModel.itemCallback);
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDogBinding itemDogBinding = ItemDogBinding.inflate(layoutInflater, parent, false);

        return new DogViewHolder(itemDogBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        DogBreedModel dogBreed = getItem(position);
        holder.bind(dogBreed);



        Log.i(TAG, "onBindViewHolder: "+holder.toString());

    }

    public class DogViewHolder extends RecyclerView.ViewHolder {
        ItemDogBinding itemDogBinding;
        public DogViewHolder(ItemDogBinding itemDogBinding) {
            super(itemDogBinding.getRoot());
            this.itemDogBinding = itemDogBinding;
        }

        public void bind(DogBreedModel dogBreedModel){
            itemDogBinding.setDog(dogBreedModel);
            LoadImage.loadImage(itemDogBinding.ivDog,dogBreedModel.getDogBreedImageUrl(),LoadImage.getProgressDrwable(itemDogBinding.ivDog.getContext()));
            Log.i(TAG, "bind: "+dogBreedModel.getDogBreedImageUrl());

        }
    }

    public interface DogsListInterface {
        void addDogBreed(DogBreedModel DogBreed);

    }

}

