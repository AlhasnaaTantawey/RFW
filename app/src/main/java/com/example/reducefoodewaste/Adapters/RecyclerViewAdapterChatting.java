package com.example.reducefoodewaste.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reducefoodewaste.Models.MessageModel;
import com.example.reducefoodewastebasic.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterChatting extends RecyclerView.Adapter<RecyclerViewAdapterChatting.ViewHoder> {
    ArrayList<MessageModel> item;

    public RecyclerViewAdapterChatting(ArrayList<MessageModel> item) {
        this.item = item;
    }

    public void setItemList(List<MessageModel> mItemArrayList) {
        item = (ArrayList<MessageModel>) mItemArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatitemui, parent, false);
        ViewHoder holder = new ViewHoder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        //all commit
        MessageModel Model = item.get(position);
        holder.date.setText(Model.getDate());
        holder.imgProfile.setImageResource(Model.getImgProfile());
        holder.nameperson1.setText(Model.getNameperson1());
        holder.mess1.setText(Model.getMess1());
        holder.imgMess1.setImageResource(Model.getImgMess1());
        holder.timestamp1.setText(Model.getTimestamp1());
        holder.imgProfile2.setImageResource(Model.getImgProfile2());
        holder.nameperson2.setText(Model.getNameperson2());
        holder.mess2.setText(Model.getMess2());
        holder.imgMess2.setImageResource(Model.getImgMess2());
        holder.timestamp2.setText(Model.getTimestamp2());
    }

    @Override
    public int getItemCount() {
//10
        return item.size();
    }

    protected class ViewHoder extends RecyclerView.ViewHolder {
        TextView date;
        ImageView imgProfile;
        TextView nameperson1;
        TextView mess1;
        ImageView imgMess1;
        TextView timestamp1;
        ImageView imgProfile2;
        TextView nameperson2;
        TextView mess2;
        ImageView imgMess2;
        TextView timestamp2;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.text_gchat_date_other);
            imgProfile = itemView.findViewById(R.id.image_gchat_profile_other);
            nameperson1 = itemView.findViewById(R.id.text_gchat_user_other);
            mess1 = itemView.findViewById(R.id.text_gchat_message_other);
            imgMess1 = itemView.findViewById(R.id.image_gchat);
            timestamp1 = itemView.findViewById(R.id.text_gchat_timestamp_other);
            imgProfile2 = itemView.findViewById(R.id.image_gchat_profile_other2);
            nameperson2 = itemView.findViewById(R.id.text_gchat_user_other2);
            mess2 = itemView.findViewById(R.id.text_gchat_message_other2);
            imgMess2 = itemView.findViewById(R.id.image_gchat2);
            timestamp2 = itemView.findViewById(R.id.text_gchat_timestamp_other2);
        }
    }
}
