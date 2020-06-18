package com.zefaf.zefaffinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileInfo extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    StorageReference reference;

    Uri uri;

    private CircleImageView profileImage;
    private EditText editname;
    private EditText editphone;
    private EditText editemail;
    private Button buttonEdit;
    private ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_info);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        profileImage = findViewById(R.id.profile_image);
        editname = findViewById(R.id.editname);
        editphone = findViewById(R.id.editphone);
        editemail = findViewById(R.id.editemail);
        buttonEdit = findViewById(R.id.buttonEdit);
        progressbar = findViewById(R.id.progressbar);


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);


            }
        });

        if (user.getPhotoUrl() != null){
            Picasso.get().load(user.getPhotoUrl()).into(profileImage);
        }
        fillInfo();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();

            }
        });

    }


    public void fillInfo() {

        if (user != null) {
            editname.setText(user.getDisplayName());
            editemail.setText(user.getEmail());
            editphone.setText(user.getPhoneNumber());

        }

    }

    public void updateInfo() {
        if (user != null) {

            user.updateEmail(editemail.getText().toString().trim());

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(editname.getText().toString().trim())
                    .setPhotoUri(uri)
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfileInfo.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        Toast.makeText(EditProfileInfo.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void uploadProfilePic(Uri uri) {

        reference = FirebaseStorage.getInstance().getReference("userProfilePictures/").child(user.getUid() + ".jpg");

        reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditProfileInfo.this, "image uploaded successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                if (intent != null) {
                    uri = intent.getData();

                    profileImage.setImageURI(uri);
                    uploadProfilePic(uri);
                }
            }
        }
    }
}
