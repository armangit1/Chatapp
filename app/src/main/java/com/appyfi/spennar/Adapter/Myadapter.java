package com.appyfi.spennar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appyfi.spennar.Activity.ChatActivity;
import com.appyfi.spennar.Model.UserData;
import com.appyfi.spennar.R;

import java.util.ArrayList;
import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {

    private final Context context;
    private final List<UserData> list;

    public Myadapter(Context context) {

        this.list = new ArrayList<>();
        this.context = context;
    }

    public void add(UserData userData){
        list.add(userData);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public Myadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.MyViewHolder holder, int position) {

        UserData userData = list.get(position);
        holder.name.setText(userData.getName());
        holder.email.setText(userData.getEmail());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("name",userData.getName());
                intent.putExtra("id",userData.getUserID());
                context.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public List<UserData> getList(){

        return list;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  name,email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
        }
    }

}
