package com.zefaf.zefaffinal;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayoutMediator;
import com.zefaf.zefaffinal.Adapter.FragmentAdapter;
import com.zefaf.zefaffinal.Fragments.ItemFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.zefaf.zefaffinal.Model.Reservation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

public class Reservations extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    private MaterialToolbar mToolbar;
    private TabLayout mTabs;

    FragmentAdapter myAdapter;
    ViewPager2 vp2;
    FragmentManager fm;
    FragmentTransaction ft;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getColor(R.color.accent));
        mToolbar.setBackgroundColor(getColor(R.color.white));

        setupTabs();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setupTabs(){
        vp2 = findViewById(R.id.pager);

        myAdapter = new FragmentAdapter(this);
        vp2.setAdapter(myAdapter);

        mTabs = findViewById(R.id.tabs);
        TabLayoutMediator tabMed = new TabLayoutMediator(mTabs, vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:
                        tab.setText(getString(R.string.current_res));
                        break;
                    case 1:
                        tab.setText(getString(R.string.previous_res));
                        break;
                }
            }
        });

        tabMed.attach();

        mTabs.setTabTextColors(getColor(R.color.black), getColor(R.color.accent));
        mTabs.setSelectedTabIndicatorColor(getColor(R.color.accent));

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        ft.commit();
    }


    @Override
    public void onListFragmentInteraction(int position) {

    }
}
