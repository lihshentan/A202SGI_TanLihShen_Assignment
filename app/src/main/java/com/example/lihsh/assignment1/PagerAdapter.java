package com.example.lihsh.assignment1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter{

    int numTabs;

    public PagerAdapter(FragmentManager fm, int num){

        super(fm);
        this.numTabs = num;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                Register register = new Register();
                return register;
            case 1:
                Login login = new Login();
                return login;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
