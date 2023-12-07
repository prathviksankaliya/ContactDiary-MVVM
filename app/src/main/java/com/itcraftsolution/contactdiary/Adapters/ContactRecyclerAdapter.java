package com.itcraftsolution.contactdiary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.itcraftsolution.contactdiary.Models.Contact;
import com.itcraftsolution.contactdiary.R;
import com.itcraftsolution.contactdiary.databinding.SampleContactRowBinding;

import java.util.ArrayList;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.viewHolder> {

    private ArrayList<Contact> list;
    private Context context;

    public ContactRecyclerAdapter(Context context, ArrayList<Contact> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_contact_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Contact contact = list.get(position);

        holder.binding.txContactName.setText(contact.getName());
        holder.binding.txContactPhoneNumber.setText(contact.getPhoneNumber());
        Glide.with(context).load(contact.getImage()).into(holder.binding.igContactImage);
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
