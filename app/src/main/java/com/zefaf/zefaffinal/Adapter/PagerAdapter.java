package com.zefaf.zefaffinal.Adapter;

import com.zefaf.zefaffinal.Model.Tabs;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Tabs> tabs;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Tabs> tabs) {
        super(fm, behavior);
        this.tabs = tabs;
    }


    public ArrayList<Tabs> getTabs() {
        return tabs;
    }

    public void setTabs(ArrayList<Tabs> tabs) {
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).gettabFragment();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return tabs.get(position).getTabTitle();
    }
}
