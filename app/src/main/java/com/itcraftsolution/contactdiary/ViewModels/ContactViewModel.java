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
}
