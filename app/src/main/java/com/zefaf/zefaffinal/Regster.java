package com.zefaf.zefaffinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Regster extends AppCompatActivity {

    ProgressBar progressBar;
    private EditText editname;
    private EditText editphone;
    private EditText editemail;
    private EditText editpassword;
    private Button buttonlogin;
    FirebaseAuth firebaseAuth;

    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regster);

        editname = findViewById(R.id.editname);
//        editphone = findViewById(R.id.editphone);
        editemail = findViewById(R.id.editemail);
        editpassword = findViewById(R.id.editpassword);
        buttonlogin = findViewById(R.id.buttonlogin);
        progressBar = findViewById(R.id.progressbar);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = editname.getText().toString().trim();
//        String phone = editphone.getText().toString().trim();
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();

        if (email.isEmpty()) {
            editemail.setError("Email is required");
            editemail.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("Please enter a valid email");
            editemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editpassword.setError("Password is required");
            editpassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editpassword.setError("Minimum length of password is be 6");
            editpassword.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editname.setError("Name is required");
            editname.requestFocus();
            return;
        }
//
//        if (phone.isEmpty()) {
//            editphone.setError("Phone is required");
//            editphone.requestFocus();
//            return;
//        } else
//            mobile = phone;
//        if (editphone.length() < 10) {
//            editphone.setError("Enter a valid mobile");
//            editphone.requestFocus();
//            return;
//        }


        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Regster.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(Regster.this, ActivityMap.class);
//                            intent.putExtra("mobile", mobile);
                            startActivity(intent);
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });


    }


}

