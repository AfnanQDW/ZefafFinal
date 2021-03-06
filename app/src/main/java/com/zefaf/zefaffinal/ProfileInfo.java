package com.zefaf.zefaffinal;

import android.content.Intent;
import android.content.pm.SigningInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zefaf.zefaffinal.Adapter.CustomListAdapter;
import com.zefaf.zefaffinal.Model.MenuItems;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileInfo extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    private CircleImageView mProfileImage;
    private TextView mTxtUserName;
    private TextView mTxtUserEmail;
    private ListView mLv;
    private TextView txtEditProfileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        mProfileImage = findViewById(R.id.profile_image);
        mTxtUserName = findViewById(R.id.txtUserName);
        mTxtUserEmail = findViewById(R.id.txtUserEmail);
        mLv = findViewById(R.id.lv);
        txtEditProfileInfo = findViewById(R.id.txtEditProfileInfo);

        txtEditProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileInfo.this,EditProfileInfo.class);
                startActivity(intent);
            }
        });

        MenuList();

    }

    public void MenuList() {

        ArrayList<MenuItems> profileData = new ArrayList<>();

        profileData.add(new MenuItems(R.drawable.ic_user, getString(R.string.full_name)));
        profileData.add(new MenuItems(R.drawable.ic_email, getString(R.string.email_address)));
        profileData.add(new MenuItems(R.drawable.ic_mobile, getString(R.string.mobile_number)));
        profileData.add(new MenuItems(R.drawable.ic_logout, getString(R.string.logout)));

        CustomListAdapter adapt = new CustomListAdapter(this, R.layout.menu_list_item, profileData);

        mLv.setAdapter(adapt);

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    startActivity(new Intent(ProfileInfo.this, EditProfileInfo.class));
                }
                if (i == 1) {
                    startActivity(new Intent(ProfileInfo.this, EditProfileInfo.class));
                }
                if (i == 2) {
                    startActivity(new Intent(ProfileInfo.this, EditProfileInfo.class));
                }
                if (i == 3) {
                    //logout
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent intent = new Intent(ProfileInfo.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(ProfileInfo.this, SignInActivity.class));
        }else {
            mTxtUserName.setText(auth.getCurrentUser().getDisplayName());
            mTxtUserEmail.setText(auth.getCurrentUser().getEmail());
            if (auth.getCurrentUser().getPhotoUrl() != null) {
                Picasso.get().load(auth.getCurrentUser().getPhotoUrl().toString()).into(mProfileImage);
            }
        }
    }

}
