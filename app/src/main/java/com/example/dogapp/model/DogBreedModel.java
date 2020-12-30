package com.example.dogapp.model;

import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dogapp.R;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;
@Entity
public class DogBreedModel {
    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    private String dogBreedId;

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    private String dogBreedName;

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    private String dogBreedLifeSpan;

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    private String dogBreedGroup;

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    private String dogBredFor;


    @SerializedName("temperament")
    private String dogBreedTemprament;

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    private String dogBreedImageUrl;

    @PrimaryKey(autoGenerate = true)
    public int dogBreedUuid;

    public DogBreedModel(String dogBreedId, String dogBreedName, String dogBreedLifeSpan,
                         String dogBreedGroup, String dogBredFor, String dogBreedTemprament,
                         String dogBreedImageUrl) {
        this.dogBreedId = dogBreedId;
        this.dogBreedName = dogBreedName;
        this.dogBreedLifeSpan = dogBreedLifeSpan;
        this.dogBreedGroup = dogBreedGroup;
        this.dogBredFor = dogBredFor;
        this.dogBreedTemprament = dogBreedTemprament;
        this.dogBreedImageUrl = dogBreedImageUrl;
    }

    @Ignore
    public DogBreedModel(String dogBreedName, String dogBreedLifeSpan) {
        this.dogBreedName = dogBreedName;
        this.dogBreedLifeSpan = dogBreedLifeSpan;

    }

    public String getDogBreedId() {
        return dogBreedId;
    }

    public void setDogBreedId(String dogBreedId) {
        this.dogBreedId = dogBreedId;
    }

    public String getDogBreedName() {
        return dogBreedName;
    }

    public void setDogBreedName(String dogBreedName) {
        this.dogBreedName = dogBreedName;
    }

    public String getDogBreedLifeSpan() {
        return dogBreedLifeSpan;
    }

    public void setDogBreedLifeSpan(String dogBreedLifeSpan) {
        this.dogBreedLifeSpan = dogBreedLifeSpan;
    }

    public String getDogBreedGroup() {
        return dogBreedGroup;
    }

    public void setDogBreedGroup(String dogBreedGroup) {
        this.dogBreedGroup = dogBreedGroup;
    }

    public String getDogBredFor() {
        return dogBredFor;
    }

    public void setDogBredFor(String dogBredFor) {
        this.dogBredFor = dogBredFor;
    }

    public String getDogBreedTemprament() {
        return dogBreedTemprament;
    }

    public void setDogBreedTemprament(String dogBreedTemprament) {
        this.dogBreedTemprament = dogBreedTemprament;
    }

    public String getDogBreedImageUrl() {
        return dogBreedImageUrl;
    }

    public void setDogBreedImageUrl(String dogBreedImageUrl) {
        this.dogBreedImageUrl = dogBreedImageUrl;
    }

    public int getDogBreedUuid() {
        return dogBreedUuid;
    }

    public void setDogBreedUuid(int dogBreedUuid) {
        this.dogBreedUuid = dogBreedUuid;
    }

    @Override
    public String toString() {
        return "DogBreedModel{" +
                "dogBreedId='" + dogBreedId + '\'' +
                ", dogBreedName='" + dogBreedName + '\'' +
                ", dogBreedLifeSpan='" + dogBreedLifeSpan + '\'' +
                ", dogBreedGroup='" + dogBreedGroup + '\'' +
                ", dogBredFor='" + dogBredFor + '\'' +
                ", dogBreedTemprament='" + dogBreedTemprament + '\'' +
                ", dogBreedImageUrl='" + dogBreedImageUrl + '\'' +
                ", dogBreedUuid=" + dogBreedUuid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogBreedModel that = (DogBreedModel) o;
        return getDogBreedUuid() == that.getDogBreedUuid() &&
                getDogBreedId().equals(that.getDogBreedId()) &&
                getDogBreedName().equals(that.getDogBreedName()) &&
                getDogBreedLifeSpan().equals(that.getDogBreedLifeSpan()) &&
                getDogBreedGroup().equals(that.getDogBreedGroup()) &&
                getDogBredFor().equals(that.getDogBredFor()) &&
                getDogBreedTemprament().equals(that.getDogBreedTemprament()) &&
                getDogBreedImageUrl().equals(that.getDogBreedImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDogBreedId(), getDogBreedName(), getDogBreedLifeSpan(), getDogBreedGroup(), getDogBredFor(), getDogBreedTemprament(), getDogBreedImageUrl(), getDogBreedUuid());
    }

    public static DiffUtil.ItemCallback<DogBreedModel> itemCallback = new DiffUtil.ItemCallback<DogBreedModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull DogBreedModel oldItem, @NonNull DogBreedModel newItem) {
            return oldItem.getDogBreedId().equals(newItem.getDogBreedId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DogBreedModel oldItem, @NonNull DogBreedModel newItem) {
            return oldItem.equals(newItem);
        }
    };


}
