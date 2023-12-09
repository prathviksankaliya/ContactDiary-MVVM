package com.itcraftsolution.contactdiary.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.itcraftsolution.contactdiary.Database.DatabaseHelper;
import com.itcraftsolution.contactdiary.Database.TBLContact;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private DatabaseHelper databaseHelper;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        databaseHelper = DatabaseHelper.databaseHelper(application.getApplicationContext());
    }

    public void addContact(TBLContact tblContact){
        databaseHelper.databaseInterface().addContact(tblContact);
    }

    public List<TBLContact> getAllContacts(){
        return databaseHelper.databaseInterface().getAllContacts();
    }

    public LiveData<List<TBLContact>> getAllLiveContacts(){
        return databaseHelper.databaseInterface().getAllLiveContacts();
    }

    public void deleteContact(String phone){
        databaseHelper.databaseInterface().deleteContact(phone);
    }

    public void updateContact(String name, String oldPhone, String newPhone, String email, String image){
        databaseHelper.databaseInterface().updateContact(name, oldPhone, newPhone, email, image);
    }
}
