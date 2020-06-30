package com.zefaf.zefaffinal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.zefaf.zefaffinal.Adapter.HajzAdapter;
import com.zefaf.zefaffinal.Model.Bookmark;
import com.zefaf.zefaffinal.Model.Hajz;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
    public static final String Address = "adress";
    public static final String Price = "price";
    public static final String Desc = "descraption";
    public static final String IMG = "img";
    public static final String IMGFav = "imgfav";
    public static final String IMGLocation = "imgloction";



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        showdata();
        buildRecycler();

        mImgNotifications = findViewById(R.id.imgNotifications);
        mImgMenu = findViewById(R.id.imgMenu);
        mEditTextserash = findViewById(R.id.editTextserash);
        mProgressCircle = findViewById(R.id.progress_circle);

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

        mEditTextserash = findViewById(R.id.editTextserash);

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
        });
    }

    public void buildRecycler() {
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

                detailIntent.putExtra(Address, clickedItem.getAdress());
                detailIntent.putExtra(Desc, clickedItem.getDese());
                detailIntent.putExtra(Name, clickedItem.getName());

                detailIntent.putExtra(Price, clickedItem.getPrice());
                detailIntent.putExtra(IMG, clickedItem.getLink());

                startActivity(detailIntent);
            }

//            @Override
//            public void onBookmarkClick(int position) {
//                Hajz h = mUploads.get(position);
//
//                Bookmark bk = new Bookmark();
//                bk.setVenueName(h.getName());
//                bk.setVenueAddress(h.getAdress());
//                bk.setVenuePic(h.getLink());
//                bk.setVenueRating("4");
//                bk.setUid(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
//
//                myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bookmark").push().setValue(bk);
//
////                myRef.child("Bookmark").push().setValue(bk);
//                Toast.makeText(ActivityMap.this, "تمت اضافة الصالة للمفضلة", Toast.LENGTH_SHORT).show();
//
//            }
        });
    }

    public void showdata() {

        myRef.child("Venues").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Log.i("AFQ", dataSnapshot.toString());

                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    Hajz upload = snap.getValue(Hajz.class);

                    mUploads.add(upload);
                    mAdapter.notifyDataSetChanged();

                }
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
            }
        });
    }


    private void filter(String text) {
        ArrayList<Hajz> filteredList = new ArrayList<>();
        for (Hajz item : mUploads) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

}