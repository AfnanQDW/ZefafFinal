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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.zefaf.zefaffinal.Model.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileInfo extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    StorageReference reference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");

    Uri uri;

    private CircleImageView profileImage;
    private EditText editname;
    private EditText editphone;
    private EditText editemail;
    private Button buttonEdit;
    private ProgressBar progressbar;

    String profilePicUrl;

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

        fillInfo();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
                Toast.makeText(EditProfileInfo.this, "Done", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void fillInfo() {

        if (user != null) {
            editname.setText(user.getDisplayName());
            editemail.setText(user.getEmail());
            editphone.setText(user.getPhoneNumber());
            if (user.getPhotoUrl() != null) {
                Picasso.get().load(user.getPhotoUrl()).into(profileImage);
            }
        }

    }

    public void updateInfo() {
        if (user != null) {

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(editname.getText().toString().trim())
//                    .setPhotoUri(Uri.parse(profilePicUrl))
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfileInfo.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if (user.getPhotoUrl() != null) {
                Picasso.get().load(user.getPhotoUrl()).into(profileImage);
            }

            User u = new User();
            u.setName(user.getDisplayName());
            u.setEmail(user.getEmail());
            u.setProfilePic(profilePicUrl);
            u.setPhoneNumber(editphone.getText().toString().trim());

            myRef.child("Users").child(user.getUid()).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(EditProfileInfo.this, "user added", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void uploadProfilePic(Uri uri) {

        reference = FirebaseStorage.getInstance().getReference("userProfilePictures/" + user.getUid() + ".jpg");

        if (uri != null) {
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profilePicUrl = taskSnapshot.getStorage().getDownloadUrl().toString();

                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfileInfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 0 && resultCode == RESULT_OK && intent != null && intent.getData() != null) {

            uri = intent.getData();

            profileImage.setImageURI(uri);
            uploadProfilePic(uri);

        }
    }
}
