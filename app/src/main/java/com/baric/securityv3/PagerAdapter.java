package com.baric.securityv3;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {

    private final int tabs;

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity, int tabs) {
        super(fragmentActivity);
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Security();
            case 1:
                return new Programming();
            case 2:
                return new Pdf();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return tabs;
    }
}
