package com.appyfi.spennar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appyfi.spennar.Model.MassageModel;
import com.appyfi.spennar.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MassageAdapter extends RecyclerView.Adapter {

    private  Context context;
    private  ArrayList<MassageModel> list;

    public MassageAdapter(Context context, ArrayList<MassageModel> list) {
        this.context = context;
        this.list = list;
    }

    public void add(MassageModel modelMassage){
        list.add(modelMassage);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }





    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.sent_layout, parent, false);

            return new sendViewHolder(view);

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.resive_layout, parent, false);

            return new reciveViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MassageModel Massage = list.get(position);


        if (holder.getClass()==sendViewHolder.class){
            sendViewHolder sendViewholder = (sendViewHolder) holder ;
            sendViewholder.sentd.setText(Massage.getMassage());
        }else {
            reciveViewHolder reciveViewholder = (reciveViewHolder) holder;
            reciveViewholder.recive.setText(Massage.getMassage());

        }

    }
    public class sendViewHolder extends RecyclerView.ViewHolder{

        TextView sentd;

        public sendViewHolder(@NonNull View itemView) {
            super(itemView);
            sentd = itemView.findViewById(R.id.massagesent);
        }
    }


    public class reciveViewHolder extends RecyclerView.ViewHolder{

        TextView recive;
        public reciveViewHolder(@NonNull View itemView) {
            super(itemView);
            recive = itemView.findViewById(R.id.massageresive);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    @Override
    public int getItemViewType(int position) {
        MassageModel modelMassage = list.get(position);
        if (modelMassage.getSenderId().equals(FirebaseAuth.getInstance().getUid())){
            return 1;
        }else return 2;
    }
}


















