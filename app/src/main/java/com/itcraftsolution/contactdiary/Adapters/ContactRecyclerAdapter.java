package com.itcraftsolution.contactdiary.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.itcraftsolution.contactdiary.Database.TBLContact;
import com.itcraftsolution.contactdiary.Models.Contact;
import com.itcraftsolution.contactdiary.R;
import com.itcraftsolution.contactdiary.ViewModels.ContactViewModel;
import com.itcraftsolution.contactdiary.databinding.SampleContactRowBinding;

import java.util.ArrayList;
import java.util.List;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.viewHolder> {

    public List<TBLContact> list;
    private Context context;
    private ContactViewModel contactViewModel;

    public ContactRecyclerAdapter(Context context, List<TBLContact> list) {
        this.context = context;
        this.list = list;
        contactViewModel= new ViewModelProvider((ViewModelStoreOwner) context).get(ContactViewModel.class);
    }

    public void setContact(ArrayList<TBLContact> tblContacts){
            this.list = tblContacts;
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_contact_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        TBLContact contact = list.get(position);

        holder.binding.txContactName.setText(contact.getName());
        holder.binding.txContactPhoneNumber.setText(contact.getPhone());
        Glide.with(context).load(Uri.parse(contact.getImage())).into(holder.binding.igContactImage);
//        holder.binding.igContactImage.setImageURI(Uri.parse(contact.getImage()));

        holder.binding.igContactDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactViewModel.deleteContact(contact.getPhone());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private SampleContactRowBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleContactRowBinding.bind(itemView);
        }
    }
}
