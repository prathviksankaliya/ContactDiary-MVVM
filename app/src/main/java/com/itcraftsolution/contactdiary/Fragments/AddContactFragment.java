package com.itcraftsolution.contactdiary.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itcraftsolution.contactdiary.Database.TBLContact;
import com.itcraftsolution.contactdiary.R;
import com.itcraftsolution.contactdiary.ViewModels.ContactViewModel;
import com.itcraftsolution.contactdiary.databinding.FragmentAddContactBinding;

public class AddContactFragment extends Fragment {

    private FragmentAddContactBinding binding;
    private ActivityResultLauncher<Intent> launchGallery;
    private Uri selectedImageUri;
    private ContactViewModel contactViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddContactBinding.inflate(getLayoutInflater());

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        binding.btnSaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edName.getText().toString().isEmpty()){
                    binding.edName.setError("Plz Enter Name!!");
                    binding.edName.requestFocus();
                }else if(binding.edPhone.getText().toString().length() != 10){
                    binding.edPhone.setError("Phone Number must be 10 Digit!!");
                    binding.edPhone.requestFocus();
                }else if(selectedImageUri == null){
                    Toast.makeText(requireContext(), "Please Select Image of Person!", Toast.LENGTH_SHORT).show();
                }else{
                    contactViewModel.addContact(new TBLContact(binding.edName.getText().toString(), binding.edPhone.getText().toString(), binding.edEmail.getText().toString(), selectedImageUri.toString()));
                    getParentFragmentManager().beginTransaction().replace(R.id.frMainContainer, new HomeFragment()).addToBackStack(null).commit();
                    Toast.makeText(requireContext(), binding.edName.getText().toString() + " Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.igImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launchGallery.launch(intent);
            }
        });

        launchGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result-> {
            if(result.getResultCode() == Activity.RESULT_OK){
                Intent data = result.getData();
                if(data != null && data.getData() != null){
                    selectedImageUri = data.getData();
                    binding.igImage.setImageURI(selectedImageUri);
                }
            }else{
                Toast.makeText(requireContext(), "Profile Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }


}