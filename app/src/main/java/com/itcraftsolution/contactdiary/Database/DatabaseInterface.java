package com.itcraftsolution.contactdiary.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Query("select * from TBLContact")
    LiveData<List<TBLContact>> getAllLiveContacts();

    @Query("delete from TBLContact where phone= :phoneNumber")
    void deleteContact(String phoneNumber);

    @Query("Update TBLContact set name=:name, phone= :newPhone, image=:image, email=:email where phone=:oldPhone")
    void updateContact(String name, String oldPhone, String newPhone, String email, String image);
}
