package com.itcraftsolution.contactdiary.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addContact(TBLContact tblContact);

    @Query("select * from TBLContact")
    List<TBLContact> getAllContacts();
}
