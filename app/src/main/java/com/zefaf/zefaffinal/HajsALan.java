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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.zefaf.zefaffinal.Model.Reservation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.zefaf.zefaffinal.ActivityMap.Adress;
import static com.zefaf.zefaffinal.ActivityMap.Desc;
import static com.zefaf.zefaffinal.ActivityMap.IMG;
import static com.zefaf.zefaffinal.ActivityMap.IMGFav;
import static com.zefaf.zefaffinal.ActivityMap.IMGLocation;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hajsalan);


        Intent intent = getIntent();
        String texnmae = intent.getStringExtra(Name);
        String texprice = intent.getStringExtra(Price);
        String texadress = intent.getStringExtra(Adress);
        String texdese = intent.getStringExtra(Desc);

        int img = intent.getIntExtra(IMG, 0);
        int imgfav = intent.getIntExtra(IMGFav, 0);
        int imagloction = intent.getIntExtra(IMGLocation, 0);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ZEFAF");

        auth = FirebaseAuth.getInstance();

        imgVenueImage = findViewById(R.id.imgVenueImage);
        imgBookmark = findViewById(R.id.imgBookmark);
        txtVenueRating = findViewById(R.id.txtVenueRating);
        txtVenueAddress = findViewById(R.id.txtVenueAddress);
        txtVenueDescription = findViewById(R.id.txtVenueDescription);
        btnBook = findViewById(R.id.btnBook);
        txtVenueName = findViewById(R.id.txtVenueName);
        txtVenuePrice = findViewById(R.id.txtVenuePrice);

        imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pick date
                pickDate();
            }
        });


    }

    public void pickDate() {
        // Initialize
        final SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Pick your date",
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

    public void sendOrder(){

        Reservation r = new Reservation();
        if (auth.getCurrentUser() != null) {
            r.setUserName(auth.getCurrentUser().getDisplayName());
        }
        r.setVenueName(txtVenueName.getText().toString());
        r.setVenueAddress(txtVenueAddress.getText().toString());
        r.setVenuePrice(txtVenuePrice.getText().toString());
        r.setReservationDate(orderDate);
        r.setReservationStatus(0);

        myRef.child("Reservations").push().setValue(r).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(HajsALan.this, "success", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(HajsALan.this, "fail", Toast.LENGTH_SHORT).show();
            }


        });

    }

    public static String ConvertToDateString(Date date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM yyyy");
        return dateformat.format(date.getTime());
    }

}
