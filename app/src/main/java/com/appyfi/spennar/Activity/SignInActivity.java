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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class SignInActivity extends AppCompatActivity {

    EditText editTextEmail,editpassword;
    Button signin;
    TextView signUp;
    String email ;
    String password ;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        editTextEmail  = findViewById(R.id.editTextEmail);
        editpassword = findViewById(R.id.password);

        signin = findViewById(R.id.signin);
        signUp = findViewById(R.id.signup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString().trim();
                password = editpassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    editpassword.setError("Password is required");
                    editpassword.requestFocus();
                    return;
                }

                SignIn();






            }
        });

signUp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }
});




    }


    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){

            startActivity(new Intent(SignInActivity.this,MainActivity.class));
            finish();

        }
    }
    private void SignIn() {

       FirebaseAuth.getInstance().signInWithEmailAndPassword(email.trim(),password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
           @Override
           public void onSuccess(AuthResult authResult) {
               String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
               Intent intent = new Intent(SignInActivity.this,MainActivity.class);
               intent.putExtra("name",name);
               startActivity(intent);
               finish();



           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {

               if (e instanceof FirebaseAuthInvalidUserException){
                   Toast.makeText(SignInActivity.this, "user das't extist", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(SignInActivity.this, "Something went wrong"+ e.getMessage(), Toast.LENGTH_SHORT).show();
               }



           }
       });
    }

}