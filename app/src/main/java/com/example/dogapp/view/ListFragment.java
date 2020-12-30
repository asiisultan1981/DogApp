package com.example.dogapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapp.R;
import com.example.dogapp.databinding.FragmentListBinding;
import com.example.dogapp.model.DogBreedModel;
import com.example.dogapp.viewmodel.ListViewModel;

import java.util.List;


public class ListFragment extends Fragment implements DogsListAdapter.DogsListInterface {
    private static final String TAG = "listFragment";
    FragmentListBinding fragmentListBinding;
    private ListViewModel viewModel;
    private DogsListAdapter dogsListAdapter;
    List<DogBreedModel> dogsBreedList;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        return fragmentListBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//       ListFragmentDirections.ListToDetails action = ListFragmentDirections.listToDetails();
//       Navigation.findNavController(view).navigate(action);
//        viewModel= new ViewModelProvider(requireActivity()).get(DogsListViewModel.class);
//        viewModel = new ViewModelProvider(this).get(DogsListViewModel.class);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();


        dogsListAdapter = new DogsListAdapter();

        fragmentListBinding.rvDogList.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentListBinding.rvDogList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        fragmentListBinding.rvDogList.setAdapter(dogsListAdapter);


        observeViewModel();


    }


    @Override
    public void addDogBreed(DogBreedModel DogBreed) {

    }

    private void observeViewModel() {

        viewModel.listMutableLiveData.observe(this, new Observer<List<DogBreedModel>>() {
            @Override
            public void onChanged(List<DogBreedModel> dogBreedModels) {
                if (dogBreedModels != null && dogBreedModels instanceof List) {
                    fragmentListBinding.rvDogList.setVisibility(View.VISIBLE);
                    dogsListAdapter.submitList(dogBreedModels);
                    Log.i(TAG, "onChanged: " + dogBreedModels.toString());
                }
            }

        });

        viewModel.mutableDogLoadError.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError != null && isError instanceof Boolean) {
                    fragmentListBinding.tvError.setVisibility(isError ? View.VISIBLE : View.GONE);
                }

            }
        });
        viewModel.mutableLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null && isLoading instanceof Boolean) {
                    fragmentListBinding.progressbar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        fragmentListBinding.tvError.setVisibility(View.GONE);
                        fragmentListBinding.rvDogList.setVisibility(View.GONE);
                    }
                }

            }
        });
        Log.i(TAG, "observeViewModel: all is set");

    }
}