package com.zefaf.zefaffinal;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zefaf.zefaffinal.Adapter.HajzAdapter;
import com.zefaf.zefaffinal.Model.Bookmark;
import com.zefaf.zefaffinal.Model.Hajz;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityMap extends AppCompatActivity {

    private ArrayList<Hajz> mUploads = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");

    private ImageView mImgNotifications;
    private ImageView mImgMenu;
    private EditText mEditTextserash;

    private RecyclerView mRecyclerView;
    private HajzAdapter mAdapter;
    private ProgressBar mProgressCircle;

    private RecyclerView.LayoutManager mLayoutManager;


    public static final String Name = "name";
    public static final String Adress = "adress";
    public static final String Price = "price";
    public static final String Desc = "descraption";
    public static final String IMG = "img";
    public static final String IMGFav = "imgfav";
    public static final String IMGLocation = "imgloction";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        showdata();

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

      /*  mEditTextserash = findViewById(R.id.editTextserash);

        mEditTextserash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });*/
    }

    public void showdata() {
        mRecyclerView = findViewById(R.id.res);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new HajzAdapter(mUploads, ActivityMap.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.OnHajzClickListener(new HajzAdapter.OnHajzClickListener() {
            @Override
            public void onHajzItemClick(int position) {
                Intent detailIntent = new Intent(ActivityMap.this, HajsALan.class);
                Hajz clickedItem = mUploads.get(position);

                detailIntent.putExtra(Adress, clickedItem.getAdress());
                detailIntent.putExtra(Desc, clickedItem.getDese());
                detailIntent.putExtra(Name, clickedItem.getName());

                detailIntent.putExtra(Price, clickedItem.getPrice());
                detailIntent.putExtra(IMG, clickedItem.getLink());

                startActivity(detailIntent);
            }
        });

        mProgressCircle = findViewById(R.id.progress_circle);

        myRef.child("venues").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                Hajz upload = dataSnapshot.getValue(Hajz.class);
                Log.i("aa", upload.toString());

                mUploads.add(upload);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ActivityMap.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
//        myRef.child("Venues").child("الجنوب").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mUploads.clear();
//                Hajz upload = dataSnapshot.getValue(Hajz.class);
//                mUploads.add(upload);
//

//                mProgressCircle.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }
   /* private void filter(String text) {
        ArrayList<Hajz> filteredList = new ArrayList<>();
        for (Hajz item : mExampleList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }*/


}