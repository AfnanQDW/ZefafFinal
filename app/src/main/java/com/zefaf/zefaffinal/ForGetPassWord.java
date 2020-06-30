package com.zefaf.zefaffinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForGetPassWord extends AppCompatActivity {

    EditText mail;
    Button change;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_get_pass_word);

        setTitle(R.string.bookmarks);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mail=findViewById(R.id.mail);
        change=findViewById(R.id.change);
        firebaseAuth=FirebaseAuth.getInstance();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=mail.getText().toString();
                if(id.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter Your email ID", Toast.LENGTH_SHORT).show();
                }
                else {

                    firebaseAuth.sendPasswordResetEmail(id).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                firebaseAuth.signOut();
                                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                                Toast.makeText(getApplicationContext(),"Password Reset Code sent to your email",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                firebaseAuth.signOut();
                                Toast.makeText(getApplicationContext(),"Error to sent email",Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                }
            }
        });



    }
}
