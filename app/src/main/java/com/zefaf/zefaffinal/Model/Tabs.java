package com.zefaf.zefaffinal.Model;

import androidx.fragment.app.Fragment;

public class Tabs {
    String tabTitle;
    Fragment tabFragment;

    public Tabs(String tabTitle
                ,Fragment tabFragment
    ) {
        this.tabTitle = tabTitle;
        this.tabFragment = tabFragment;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public Fragment gettabFragment() {
        return tabFragment;
    }

    public void settabFragment(Fragment tabFragment) {
        this.tabFragment = tabFragment;
    }
}
