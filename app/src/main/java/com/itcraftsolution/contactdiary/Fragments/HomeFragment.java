package com.itcraftsolution.contactdiary.Fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itcraftsolution.contactdiary.Adapters.ContactRecyclerAdapter;
import com.itcraftsolution.contactdiary.Database.TBLContact;
import com.itcraftsolution.contactdiary.Models.Contact;
import com.itcraftsolution.contactdiary.R;
import com.itcraftsolution.contactdiary.ViewModels.ContactViewModel;
import com.itcraftsolution.contactdiary.databinding.FragmentHomeBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ContactRecyclerAdapter adapter;
    private List<TBLContact> list;
    private ContactViewModel contactViewModel;
    private Boolean isCallPermissionAllow = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        list = new ArrayList<>();
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        if(!isCallPermissionAllow){
            callPermission();
        }

//        these results are not liveData
//        list = contactViewModel.getAllContacts();

        adapter = new ContactRecyclerAdapter(requireContext(), list);
        binding.rvContactList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvContactList.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                TBLContact contact = adapter.list.get(viewHolder.getAdapterPosition());
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+91" + contact.getPhone()));
                startActivity(intent);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        }).attachToRecyclerView(binding.rvContactList);
        binding.fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.frMainContainer, new AddContactFragment()).commit();
            }
        });

        //Live Contact Data Fetch from Room
        contactViewModel.getAllLiveContacts().observe(getViewLifecycleOwner(), new Observer<List<TBLContact>>() {
            @Override
            public void onChanged(List<TBLContact> tblContacts) {
                adapter.setContact((ArrayList<TBLContact>) tblContacts);
            }
        });
        return binding.getRoot();
    }

    private Boolean callPermission(){

        Dexter.withContext(requireContext()).withPermission(Manifest.permission.CALL_PHONE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    isCallPermissionAllow = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
        return isCallPermissionAllow;
    }
}