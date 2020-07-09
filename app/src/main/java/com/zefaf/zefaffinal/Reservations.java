package com.zefaf.zefaffinal;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zefaf.zefaffinal.Adapter.ReservationRecyclerViewAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.zefaf.zefaffinal.Model.Bookmark;
import com.zefaf.zefaffinal.Model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Reservations extends AppCompatActivity {

    private MaterialToolbar mToolbar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");

    ArrayList<Reservation> reservations;
    RecyclerView rv;
    RatingBar ratingBar;
    Reservation reservation = new Reservation();

    String key;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.reservations));

        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        myRef.child("Reservations").child("reservationStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Notify("تم الرد على طلبك", "انقر للاطلاع على حجوزاتك", "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getRes();
    }

    public void getRes() {

        rv = findViewById(R.id.list);
        reservations = new ArrayList<>();

        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Reservations")
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snap : snapshot.getChildren()) {

                            key = snap.getKey();

                            reservation = snap.getValue(Reservation.class);
                            reservations.add(reservation);
                        }

                        if (!reservations.isEmpty()) {
                            ReservationRecyclerViewAdapter adapt = new ReservationRecyclerViewAdapter(reservations, new ReservationRecyclerViewAdapter.OnReservationItemClickListener() {
                                @Override
                                public void OnReservationItemClick(int position) {

                                    String resDate = reservation.getReservationDate();
                                    Date dd = new Date();
                                    try {
                                        dd = new SimpleDateFormat("dd MMM yyyy").parse(resDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if (dd  != null&& dd.before(Calendar.getInstance().getTime())) {
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(Reservations.this);
                                        builder.setIcon(R.drawable.logo);

                                        builder.setTitle("ما تقييمك لهذه الصالة؟");

                                        final View customView = getLayoutInflater().inflate(R.layout.ratingbar, null);
                                        ratingBar = customView.findViewById(R.id.rating);

                                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                            @Override
                                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                                                HashMap<String, Object> map = new HashMap<>();
                                                map.put("reservationRating", rating);
                                                myRef.child("Reservations").child(key).updateChildren(map);
                                                Toast.makeText(Reservations.this, "" + rating, Toast.LENGTH_SHORT).show();


                                            }
                                        });

                                        builder.setView(customView);
                                        builder.setCancelable(true);

                                        final AlertDialog alertdialog = builder.create();
                                        alertdialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(Reservations.this, "Thanks", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                        alertdialog.show();

                                    } else
                                        Toast.makeText(Reservations.this, "لا يمكن تقييم الصالة قبل مرور الحجز", Toast.LENGTH_SHORT).show();
                                }

                            });
                            rv.setAdapter(adapt);
                            rv.setLayoutManager(new LinearLayoutManager(Reservations.this));
                            rv.setHasFixedSize(true);
                            adapt.notifyDataSetChanged();
                        } else {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Reservations.this);
                            builder.setTitle("");
                            builder.setIcon(R.drawable.logo);

                            final View customView = getLayoutInflater().inflate(R.layout.no_reservations, null);
                            Button b = customView.findViewById(R.id.button3);

                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(Reservations.this,ActivityMap.class);
                                    startActivity(i);
                                }
                            });

                            builder.setView(customView);
                            builder.setCancelable(true);

                            final AlertDialog alertdialog = builder.create();
                            alertdialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public static String ConvertToDateString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(date.getTime());
    }

    public void Notify(String title, String contentText, String subText)  {


        String CHANNEL_Id = "Zefaf";
        String CHANNEL_NAME = "Zefaf";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(CHANNEL_Id, CHANNEL_NAME, importance);
            ch.setDescription("notif desc");
            ch.setVibrationPattern(new long[]{1000, 200, 300, 200, 300, 200});

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(ch);

        }

        Intent i = new Intent(getBaseContext(), MainActivity.class);
        PendingIntent PI = PendingIntent.getActivity(getBaseContext(), 0, i, 0);

        NotificationCompat.Builder b = new NotificationCompat.Builder(getBaseContext(), CHANNEL_Id);

        b.setContentTitle(title);
        b.setContentText(contentText);
        b.setSubText(subText);
        b.setSmallIcon(R.drawable.logo);
        b.setContentIntent(PI);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getBaseContext());

        managerCompat.notify(1, b.build());

    }
}
