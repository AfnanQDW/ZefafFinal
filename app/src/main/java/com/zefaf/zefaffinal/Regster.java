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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zefaf.zefaffinal.Model.User;

public class Regster extends AppCompatActivity {

    ProgressBar progressBar;
    private EditText editname;
    private EditText editemail;
    private EditText editpassword;
    private Button buttonlogin;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");
    FirebaseUser user;

    String name;
    String email;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regster);

        editname = findViewById(R.id.editname);
        editemail = findViewById(R.id.editemail);
        editpassword = findViewById(R.id.editpassword);
        buttonlogin = findViewById(R.id.buttonlogin);
        progressBar = findViewById(R.id.progressbar);



        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        name = editname.getText().toString().trim();
        email = editemail.getText().toString().trim();
        password = editpassword.getText().toString().trim();

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

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        user = firebaseAuth.getCurrentUser();

                        if (task.isSuccessful()) {
                            finish();
                            Intent intent = new Intent(Regster.this, ActivityMap.class);

                            startActivity(intent);

                            if (user != null) {

                                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Regster.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                User u = new User();
                                u.setName(user.getDisplayName());
                                u.setEmail(user.getEmail());

                                myRef.child("Users").child(user.getUid()).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Regster.this, "user added", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


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

