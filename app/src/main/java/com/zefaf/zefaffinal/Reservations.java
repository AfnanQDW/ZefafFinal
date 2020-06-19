package com.zefaf.zefaffinal;

import android.os.Build;
import android.os.Bundle;

import com.zefaf.zefaffinal.Adapter.PagerAdapter;
import com.afq.zefaf.Fragments.dummy.DummyContent;
import com.zefaf.zefaffinal.Fragments.ItemFragment;
import com.zefaf.zefaffinal.Fragments.NoReservationsFragment;
import com.zefaf.zefaffinal.Model.Tabs;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class Reservations extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    private MaterialToolbar mToolbar;
    private TabLayout mTabs;

    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    ArrayList<Tabs> tabs;
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

        mTabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.pager);

        mTabs.setTabTextColors(getColor(R.color.black), getColor(R.color.accent));
        mTabs.setSelectedTabIndicatorColor(getColor(R.color.accent));

        tabs = new ArrayList<>();
        tabs.add(new Tabs(getString(R.string.current_res),new NoReservationsFragment()));
        tabs.add(new Tabs(getString(R.string.previous_res), new ItemFragment()));

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabs);
        viewPager.setAdapter(pagerAdapter);
        mTabs.setupWithViewPager(viewPager);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        ft.commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
