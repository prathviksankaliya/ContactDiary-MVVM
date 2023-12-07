package com.itcraftsolution.contactdiary.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Database;

import com.itcraftsolution.contactdiary.Database.DatabaseHelper;
import com.itcraftsolution.contactdiary.Database.TBLContact;

public class ContactViewModel extends AndroidViewModel {

    private DatabaseHelper databaseHelper;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        databaseHelper = DatabaseHelper.databaseHelper(application.getApplicationContext());
    }

    public void addContact(TBLContact tblContact){
        databaseHelper.databaseInterface().addContact(tblContact);
    }
}
