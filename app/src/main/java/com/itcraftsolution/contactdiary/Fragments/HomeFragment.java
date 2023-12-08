package com.itcraftsolution.contactdiary.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itcraftsolution.contactdiary.Adapters.ContactRecyclerAdapter;
import com.itcraftsolution.contactdiary.Database.TBLContact;
import com.itcraftsolution.contactdiary.Models.Contact;
import com.itcraftsolution.contactdiary.R;
import com.itcraftsolution.contactdiary.ViewModels.ContactViewModel;
import com.itcraftsolution.contactdiary.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ContactRecyclerAdapter adapter;
    private List<TBLContact> list;
    private ContactViewModel contactViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        list = new ArrayList<>();
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        list = contactViewModel.getAllContacts();

        adapter = new ContactRecyclerAdapter(requireContext(), list);
        binding.rvContactList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvContactList.setAdapter(adapter);

        binding.fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frMainContainer, new AddContactFragment()).commit();
            }
        });
        return binding.getRoot();
    }
}