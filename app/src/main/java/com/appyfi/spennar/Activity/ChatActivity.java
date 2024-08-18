package com.appyfi.spennar.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appyfi.spennar.Adapter.MassageAdapter;
import com.appyfi.spennar.Model.MassageModel;
import com.appyfi.spennar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    String reciverId, reciverName, senderroom, resiverRoom;
    String senderId;
    EditText edmassage;
    ImageView sent;
    Toolbar toolbar;
    MassageAdapter massageAdapter;
    ArrayList<MassageModel> list;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        edmassage = findViewById(R.id.edmassage);
        sent = findViewById(R.id.sent);
        recyclerView = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();
        massageAdapter = new MassageAdapter(this, list);

        database = FirebaseDatabase.getInstance();

        recyclerView.scrollToPosition(massageAdapter.getItemCount());
        reciverId = getIntent().getStringExtra("id");
        reciverName = getIntent().getStringExtra("name");
        senderId = FirebaseAuth.getInstance().getUid();
        getSupportActionBar().setTitle(reciverName);

        edmassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(massageAdapter.getItemCount());
            }
        });

        if (reciverId != null) {
            senderroom = FirebaseAuth.getInstance().getUid() + reciverId;
            resiverRoom = reciverId + FirebaseAuth.getInstance().getUid();
        }

        database.getReference().child("Chat")
                .child(senderroom)
                .child("massages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            MassageModel massageModel = dataSnapshot.getValue(MassageModel.class);
                            list.add(massageModel);
                        }
                        massageAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(massageAdapter.getItemCount()-1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String massage = edmassage.getText().toString();
                if (massage.isEmpty()) {
                    return;
                }
                MassageModel massageModel = new MassageModel(massage, senderId);


                database.getReference().child("Chat")
                        .child(senderroom)
                        .child("massages")
                        .push()
                        .setValue(massageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                database.getReference().child("Chat")
                                        .child(resiverRoom)
                                        .child("massages")
                                        .push()
                                        .setValue(massageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {


                                            }
                                        });


                            }
                        });


                edmassage.setText("");


            }
        });

        recyclerView.setAdapter(massageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}