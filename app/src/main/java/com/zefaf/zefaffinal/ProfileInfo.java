package com.zefaf.zefaffinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zefaf.zefaffinal.Adapter.CustomListAdapter;
import com.zefaf.zefaffinal.Model.MenuItems;
import com.google.firebase.auth.FirebaseAuth;
import com.zefaf.zefaffinal.Adapter.CustomListAdapter;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileInfo extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    Uri uri;

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

        if (auth.getCurrentUser() != null) {
            mTxtUserName.setText(auth.getCurrentUser().getDisplayName());
            mTxtUserEmail.setText(auth.getCurrentUser().getEmail());
        }

        txtEditProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileInfo.this,EditProfileInfo.class);
                startActivity(intent);
            }
        });

        MenuList();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (intent != null) {

                    uri = intent.getData();
                    mProfileImage.setImageURI(uri);
                }
            }
        }

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
                    startActivity(new Intent(ProfileInfo.this, SignInActivity.class));
                }
            }
        });
    }

}
