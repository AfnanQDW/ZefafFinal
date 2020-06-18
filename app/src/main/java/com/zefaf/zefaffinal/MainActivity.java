package com.zefaf.zefaffinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zefaf.zefaffinal.Adapter.CustomListAdapter;
import com.zefaf.zefaffinal.Model.MenuItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zefaf.zefaffinal.Adapter.CustomListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private TextView txtUserName;
    private TextView txtUserEmail;
    private ListView lv;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ZEFAF");

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ZEFAF");

        auth = FirebaseAuth.getInstance();

        lv = findViewById(R.id.lv);

        txtUserName = findViewById(R.id.txtUserName);
        txtUserEmail = findViewById(R.id.txtUserEmail);

        if (auth.getCurrentUser() != null) {
            txtUserName.setText(auth.getCurrentUser().getDisplayName());
            txtUserEmail.setText(auth.getCurrentUser().getEmail());
        }

        LinearLayout profileInfo = findViewById(R.id.profileInfo);

        profileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileInfo.class);
                startActivity(intent);
            }
        });

        MenuList();
    }

    public void MenuList() {


        ArrayList<MenuItems> data = new ArrayList<>();

        data.add(new MenuItems(R.drawable.ic_bookmarks, getString(R.string.bookmarks)));
        data.add(new MenuItems(R.drawable.ic_calender, getString(R.string.reservations)));
//        data.add(new MenuItems(R.drawable.ic_credit_card, getString(R.string.credit_card)));
//        data.add(new MenuItems(R.drawable.ic_language, getString(R.string.language)));
        data.add(new MenuItems(R.drawable.ic_terms, getString(R.string.terms_of_service)));
        data.add(new MenuItems(R.drawable.ic_policy, getString(R.string.privacy_policy)));
        data.add(new MenuItems(R.drawable.ic_help_center, getString(R.string.help_center)));
        data.add(new MenuItems(R.drawable.ic_info, getString(R.string.about_us)));

        CustomListAdapter adapt = new CustomListAdapter(this, R.layout.menu_list_item, data);

        lv.setAdapter(adapt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i){
                    case 0:
                        intent = new Intent(MainActivity.this, BookmarksActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, Reservations.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //terms of service
                        break;
                    case 3:
                        //privacy_policy
                        break;
                    case 4 :
                        //help_center
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, About.class);
                        startActivity(intent);
                        break;

                    default:
                }

            }
        });
    }

}
