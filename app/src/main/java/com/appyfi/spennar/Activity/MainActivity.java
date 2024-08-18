package com.appyfi.spennar.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appyfi.spennar.Adapter.Myadapter;
import com.appyfi.spennar.R;
import com.appyfi.spennar.Model.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference myref;

    Toolbar toolbar;
    RecyclerView recyclerView;

    String myname = "Uploding...";

    Myadapter myadapter;

    UserData userData = new UserData();







    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        setSupportActionBar(toolbar);



        getSupportActionBar().setTitle(myname);


        myadapter = new Myadapter(this);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myref  = FirebaseDatabase.getInstance().getReference("Users");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                myadapter.clear();

              String Disply =  FirebaseAuth.getInstance().getCurrentUser().getDisplayName();




                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String UID = dataSnapshot.getKey();
                    UserData userData = dataSnapshot.getValue(UserData.class);
                    if (userData!=null && userData.getUserID()!=null && !userData.getUserID().equals(FirebaseAuth.getInstance().getUid())){
                        myadapter.add(userData);
                    }
                    getSupportActionBar().setTitle("My Chat App");


                }
                List<UserData> userData = myadapter.getList();
                myadapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,SignInActivity.class));
            finish();
            return true;
        }


        return false;
    }
}