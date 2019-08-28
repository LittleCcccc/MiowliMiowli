package io.miowlimiowli.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import io.miowlimiowli.fragment.NewsObjectFragment;

public class NewsPagerAdaptor extends FragmentStatePagerAdapter {
    public NewsPagerAdaptor(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i){
        Fragment fragment = new NewsObjectFragment();
        Bundle args = new Bundle();
        args.putInt(NewsObjectFragment.ARG_OBJECT,i+1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount(){
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        return "object"+(position+1);
    }

}
