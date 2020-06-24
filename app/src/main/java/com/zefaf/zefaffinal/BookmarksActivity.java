package com.zefaf.zefaffinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zefaf.zefaffinal.Adapter.BookmarksAdapter;
import com.zefaf.zefaffinal.Model.Bookmark;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarksActivity extends AppCompatActivity {


    private ArrayList<Bookmark> mExampleList = new ArrayList<>();

    private RecyclerView mBookmarksRecyclerView;
    private BookmarksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressCircle;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        progressCircle = findViewById(R.id.progress_circle);

        createExampleList();
        buildRecyclerView();

    }

    public void removeItem(int position) {
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }


    public void createExampleList() {

        progressCircle.setVisibility(View.VISIBLE);

        myRef.child("Bookmark").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                progressCircle.setVisibility(View.INVISIBLE);

                Bookmark bkm = dataSnapshot.getValue(Bookmark.class);
                mExampleList.add(bkm);

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

            }
        });


    }


    public void buildRecyclerView() {
        mBookmarksRecyclerView = findViewById(R.id.bookmarksRecyclerView);
        mBookmarksRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new BookmarksAdapter(mExampleList);
        mBookmarksRecyclerView.setLayoutManager(mLayoutManager);
        mBookmarksRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BookmarksAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent(BookmarksActivity.this, HajsALan.class);
                startActivity(intent);
            }

            @Override
            public void onBookmarkClick(int position) {
                removeItem(position);
            }
        });
    }
}
