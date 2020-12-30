package com.example.dogapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapp.R;
import com.example.dogapp.databinding.FragmentDetailsBinding;
import com.example.dogapp.model.DogBreedModel;
import com.example.dogapp.viewmodel.DetailViewModel;

public class DetailsFragment extends Fragment {
    private static final String TAG = "detail";
    FragmentDetailsBinding fragmentDetailsBinding;
    private int dogId;
    private DetailViewModel viewModel;


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_details, container, false);
        fragmentDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container, false);
        return fragmentDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            dogId = DetailsFragmentArgs.fromBundle(getArguments()).getDogUuid();
        }
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.getLiveData();

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getLiveData().observe(this, dogBreedModel -> {
                if (dogBreedModel != null && dogBreedModel instanceof DogBreedModel){
                    fragmentDetailsBinding.tvName.setText(dogBreedModel.getDogBreedName());
                    fragmentDetailsBinding.tvPurpose.setText(dogBreedModel.getDogBredFor());
                    fragmentDetailsBinding.tvTemprament.setText(dogBreedModel.getDogBreedTemprament());
                    fragmentDetailsBinding.tvLifespan.setText(dogBreedModel.getDogBreedLifeSpan());
                    Log.i(TAG, "observeViewModel: "+dogBreedModel.toString());
                }
        });

    }
}