package com.zefaf.zefaffinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zefaf.zefaffinal.Adapter.BookmarksAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


public class ActivityMap extends AppCompatActivity {

    private ImageView mImgNotifications;
    private ImageView mImgMenu;
    private EditText mEditTextserash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mImgNotifications = findViewById(R.id.imgNotifications);
        mImgMenu = findViewById(R.id.imgMenu);
        mEditTextserash = findViewById(R.id.editTextserash);

        mImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMap.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mImgNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMap.this, Notifications.class);
                startActivity(intent);
            }
        });



    }
}
