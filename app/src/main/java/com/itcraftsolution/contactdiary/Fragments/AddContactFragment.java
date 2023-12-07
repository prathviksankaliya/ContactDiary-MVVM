package com.itcraftsolution.contactdiary.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itcraftsolution.contactdiary.R;
import com.itcraftsolution.contactdiary.databinding.FragmentAddContactBinding;

public class AddContactFragment extends Fragment {

    private FragmentAddContactBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddContactBinding.inflate(getLayoutInflater());



        return binding.getRoot();
    }
}