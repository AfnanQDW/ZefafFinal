package com.zefaf.zefaffinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.squareup.picasso.Picasso;
import com.zefaf.zefaffinal.Model.Bookmark;
import com.zefaf.zefaffinal.Model.Reservation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.zefaf.zefaffinal.ActivityMap.Address;
import static com.zefaf.zefaffinal.ActivityMap.Desc;
import static com.zefaf.zefaffinal.ActivityMap.IMG;
import static com.zefaf.zefaffinal.ActivityMap.Name;
import static com.zefaf.zefaffinal.ActivityMap.Price;

public class HajsALan extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");

    FirebaseAuth auth = FirebaseAuth.getInstance();

    private ImageView imgVenueImage;
    private TextView txtVenueName;
    private ImageView imgBookmark;
    private TextView txtVenueRating;
    private TextView txtVenueAddress;
    private TextView txtVenueDescription;
    private Button btnBook;

    private String orderDate;
    private TextView txtVenuePrice;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hajsalan);

        parentLayout = findViewById(android.R.id.content);

        Intent intent = getIntent();
        String texnmae = intent.getStringExtra(Name);
        String texprice = intent.getStringExtra(Price);
        String texadress = intent.getStringExtra(Address);
        String texdese = intent.getStringExtra(Desc);

        final String img = intent.getStringExtra(IMG);

        imgVenueImage = findViewById(R.id.imgVenueImage);
        Picasso.get().load(img).into(imgVenueImage);

        txtVenueAddress = findViewById(R.id.txtVenueAddress);
        txtVenueAddress.setText(texadress);

        txtVenueDescription = findViewById(R.id.txtVenueDescription);
        txtVenueDescription.setText(texdese);

        txtVenueName = findViewById(R.id.txtVenueName);
        txtVenueName.setText(texnmae);

        txtVenuePrice = findViewById(R.id.txtVenuePrice);
        txtVenuePrice.setText(texprice);

        btnBook = findViewById(R.id.btnBook);
        txtVenueRating = findViewById(R.id.txtVenueRating);

        imgBookmark = findViewById(R.id.imgBookmark);
        imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgBookmark.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_bookmark_border).getConstantState()) {
                    Bookmark bkm = new Bookmark();

                    bkm.setUid(auth.getCurrentUser().getUid());
                    bkm.setVenueRating(txtVenueRating.getText().toString());
                    bkm.setVenuePic(img);
                    bkm.setVenueAddress(txtVenueAddress.getText().toString());
                    bkm.setVenueName(txtVenueName.getText().toString());

//                    myRef.child("Bookmark").push().setValue(bkm);
                    myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Bookmarks").push().setValue(bkm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Snackbar.make(parentLayout, "تمت الاضافة للمفضلة", BaseTransientBottomBar.LENGTH_LONG).setAction("الذهاب للمفضلة", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(HajsALan.this, BookmarksActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();
                            } else
                                Toast.makeText(HajsALan.this, "fail", Toast.LENGTH_SHORT).show();
                        }


                    });

                    imgBookmark.setImageResource(R.drawable.ic_bookmark_filled);
                } else {

                    myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("Bookmarks").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snap : snapshot.getChildren()) {

                                String key = snap.getKey();

                                if (key != null) {
                                    myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("Bookmarks").child(key).removeValue();
                                    imgBookmark.setImageResource(R.drawable.ic_bookmark_border);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (auth.getCurrentUser() != null) {
                    pickDate();
                } else {
                    intent = new Intent(HajsALan.this, SignInActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void pickDate() {
        // Initialize
        final SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "أختر موعد المناسبة",
                "OK",
                "Cancel"
        );

        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.setMinimumDateTime(Calendar.getInstance().getTime());
        dateTimeDialogFragment.setDefaultDateTime(Calendar.getInstance().getTime());

        // Set listener
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                String d = ConvertToDateString(date);
                orderDate = d;
                sendOrder();

            }

            @Override
            public void onNegativeButtonClick(Date date) {
                dateTimeDialogFragment.dismiss();
            }
        });

        dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");

    }

    public void sendOrder() {

        final Reservation r = new Reservation();
        if (auth.getCurrentUser() != null) {
            r.setUserName(auth.getCurrentUser().getDisplayName());
        }
        r.setVenueName(txtVenueName.getText().toString());
        r.setVenueAddress(txtVenueAddress.getText().toString());
        r.setVenuePrice(txtVenuePrice.getText().toString());
        r.setReservationDate(orderDate);
        r.setReservationStatus(0);

        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Reservations").push().setValue(r).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    myRef.child("Reservations").push().setValue(r);
                    Snackbar.make(parentLayout, "تمت اضافة الطلب", BaseTransientBottomBar.LENGTH_LONG).setAction("الذهاب للحجوزات", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(HajsALan.this, Reservations.class);
                            startActivity(intent);
                        }
                    }).show();
                } else
                    Toast.makeText(HajsALan.this, "fail", Toast.LENGTH_SHORT).show();
            }


        });

    }

    public static String ConvertToDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(date.getTime());
    }

}
