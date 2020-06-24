package com.zefaf.zefaffinal.Adapter;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zefaf.zefaffinal.Fragments.ItemFragment;
import com.zefaf.zefaffinal.Fragments.NoReservationsFragment;
import com.zefaf.zefaffinal.Model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {

    ArrayList<Reservation> reservations;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");


    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        reservations = new ArrayList<>();

        myRef.child("Reservations").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Reservation reservation = dataSnapshot.getValue(Reservation.class);
                Log.i("reservation", dataSnapshot.toString());
                reservations.add(reservation);

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

        if (reservations.isEmpty()) {
            Log.i("aa","full");

            for (Reservation r : reservations) {
                String resDate = r.getReservationDate();
                Date dd = new Date();
                try {
                    dd = new SimpleDateFormat("dd MMM yyyy").parse(resDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                switch (position) {
                    case 0:
                        if (dd != null && dd.after(Calendar.getInstance().getTime())) {
                            Log.i("aa","current");
                            return new ItemFragment();
                        }
                    case 1:
                        if (dd != null && dd.before(Calendar.getInstance().getTime())) {
                            Log.i("aa","prev");

                            return new ItemFragment();
                        }
                }


            }

        }else {
            Log.i("aa", "empty");
            return new NoReservationsFragment();
        }
        return new ItemFragment();
    }

    public static String ConvertToDateString(Date date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM yyyy");
        return dateformat.format(date.getTime());
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
