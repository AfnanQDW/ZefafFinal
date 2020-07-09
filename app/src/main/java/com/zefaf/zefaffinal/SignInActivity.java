package com.zefaf.zefaffinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private TextView tvforgetpassword;
    private TextView mTextnewaccount;
    private EditText editEmail;

    private EditText mEditTextpass;
    private Button mButtonlogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvforgetpassword = findViewById(R.id.tvforgetpass);
        mTextnewaccount = findViewById(R.id.textnewaccount);
        editEmail = findViewById(R.id.editEmail);
        mEditTextpass = findViewById(R.id.editTextpass);
        mButtonlogin = findViewById(R.id.buttonlogin);
        mAuth = FirebaseAuth.getInstance();

        mButtonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
            }
        });

        mTextnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, Regster.class);
                startActivity(intent);
            }
        });

        tvforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ForGetPassWord.class);
                startActivity(intent);
            }
        });

    }

    private void userlogin() {
        String email = editEmail.getText().toString().trim();
        String password = mEditTextpass.getText().toString().trim();


        if (password.isEmpty()) {
            mEditTextpass.setError("Password is required");
            mEditTextpass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            mEditTextpass.setError("Minimum length of password should be 6");
            mEditTextpass.requestFocus();
            return;
        }


        if (email.isEmpty()) {
            editEmail.setError("email is required");
            editEmail.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(SignInActivity.this, ActivityMap.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, LoginAct.class));
        }
    }


}
