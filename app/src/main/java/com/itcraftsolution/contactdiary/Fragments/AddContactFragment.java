package com.itcraftsolution.contactdiary.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.itcraftsolution.contactdiary.Database.TBLContact;
import com.itcraftsolution.contactdiary.R;
import com.itcraftsolution.contactdiary.ViewModels.ContactViewModel;
import com.itcraftsolution.contactdiary.databinding.FragmentAddContactBinding;

public class AddContactFragment extends Fragment {

    private FragmentAddContactBinding binding;
    private ActivityResultLauncher<Intent> launchGallery;
    private Uri selectedImageUri;
    private ContactViewModel contactViewModel;
    private SharedPreferences spf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddContactBinding.inflate(getLayoutInflater());

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        isUpdate();

        binding.btnSaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edName.getText().toString().isEmpty()){
                    binding.edName.setError("Plz Enter Name!!");
                    binding.edName.requestFocus();
                }else if(binding.edPhone.getText().toString().length() != 10){
                    binding.edPhone.setError("Phone Number must be 10 Digit!!");
                    binding.edPhone.requestFocus();
                }else if(!spf.getBoolean("isUpdate", false) && selectedImageUri == null){
                    Toast.makeText(requireContext(), "Please Select Image of Person!", Toast.LENGTH_SHORT).show();
                }else{
                    if(spf.getBoolean("isUpdate", false)){
                        SharedPreferences.Editor editor = spf.edit();
                        editor.putBoolean("isUpdate", false);
                        editor.apply();
                        if(selectedImageUri == null){
                            selectedImageUri = Uri.parse(spf.getString("image", null));
                        }
                        contactViewModel.updateContact(binding.edName.getText().toString(), spf.getString("phone", null), binding.edPhone.getText().toString(), binding.edEmail.getText().toString(), selectedImageUri.toString());
                        Toast.makeText(requireContext(), binding.edName.getText().toString() + " Updated", Toast.LENGTH_SHORT).show();
                    }else{
                        contactViewModel.addContact(new TBLContact(binding.edName.getText().toString(), binding.edPhone.getText().toString(), binding.edEmail.getText().toString(), selectedImageUri.toString()));
                        Toast.makeText(requireContext(), binding.edName.getText().toString() + " Added", Toast.LENGTH_SHORT).show();
                    }
                    getParentFragmentManager().beginTransaction().replace(R.id.frMainContainer, new HomeFragment()).addToBackStack(null).commit();
                }
            }
        });

        binding.igImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!spf.getBoolean("isUpdate", false)){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    launchGallery.launch(intent);
                }



            }
        });

        launchGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result-> {
            if(result.getResultCode() == Activity.RESULT_OK){
                Intent data = result.getData();
                if(data != null && data.getData() != null){
                    selectedImageUri = data.getData();
//                    int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    requireContext().getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);
                    binding.igImage.setImageURI(selectedImageUri);
                }
            }else{
                Toast.makeText(requireContext(), "Profile Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    private void isUpdate(){
        spf = requireContext().getSharedPreferences("ContactDetails", Context.MODE_PRIVATE);
        if(spf.getBoolean("isUpdate", false)){
            String name = spf.getString("name", null);
            String phone = spf.getString("phone", null);
            String image = spf.getString("image", null);
            String email = spf.getString("email", null);
            binding.edPhone.setText(phone);
            binding.edName.setText(name);
            binding.edEmail.setText(email);

//            try{
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), Uri.parse(image));
//            binding.igImage.setImageBitmap(bitmap);
//            }catch (Exception e)
//            {
//                Toast.makeText(requireContext(), "Update Bitmap Image Error", Toast.LENGTH_SHORT).show();
//            }
            binding.igImage.setImageURI(Uri.parse(image));
            binding.btnSaveContact.setText("Update Contact");

        }
    }


}