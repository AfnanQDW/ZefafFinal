package com.zefaf.zefaffinal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zefaf.zefaffinal.Adapter.BookmarksAdapter;
import com.zefaf.zefaffinal.Model.Bookmark;
import com.zefaf.zefaffinal.Model.Hajz;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarksActivity extends AppCompatActivity {

    private ArrayList<Bookmark> mExampleList = new ArrayList<>();

    private RecyclerView mBookmarksRecyclerView;
    private BookmarksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressCircle;

    String key;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        setTitle(R.string.bookmarks);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        progressCircle = findViewById(R.id.progress_circle);

        createExampleList();
        buildRecyclerView();

    }

    public void removeItem(int position) {
        mExampleList.remove(position);
        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Bookmarks").child(key).removeValue();
        mAdapter.notifyItemRemoved(position);
    }


    public void createExampleList() {

        progressCircle.setVisibility(View.VISIBLE);
        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bookmarks")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressCircle.setVisibility(View.INVISIBLE);

                for (DataSnapshot snap : snapshot.getChildren()) {

                    key = snap.getKey();

                    Bookmark bkm = snap.getValue(Bookmark.class);
                    mExampleList.add(bkm);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
