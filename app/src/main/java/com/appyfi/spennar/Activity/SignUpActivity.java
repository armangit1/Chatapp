package com.appyfi.spennar.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appyfi.spennar.R;
import com.appyfi.spennar.Model.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    EditText edname, edemail, edpassword;
    Button signup;

    TextView signin;

    String name, email, password;

    DatabaseReference databaseReference;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edemail = findViewById(R.id.editTextEmail);
        edpassword = findViewById(R.id.password);
        edname = findViewById(R.id.name);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edname.getText().toString().trim();
                email = edemail.getText().toString().trim();
                password = edpassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    edname.setError("Enter your name");
                    edname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    edname.setError("Enter your email");
                    edname.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edname.setError("Enter your password");
                    edname.requestFocus();
                    return;
                }
                if (password.length() > 15 || password.length() > 6) {
                    edname.setError("password is invalid");
                    edname.requestFocus();
                    return;
                }


                signup();


            }


        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();

        }
    }

    public void signup() {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.trim(), password.trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                firebaseUser.updateProfile(userProfileChangeRequest);

                UserData userData = new UserData(name,email,password,FirebaseAuth.getInstance().getUid());

                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userData);

                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
               finishAffinity();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(SignUpActivity.this, "" +e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


}