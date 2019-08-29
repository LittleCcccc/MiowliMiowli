package io.miowlimiowli.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import io.miowlimiowli.fragment.NewsListFragment;

public class NewsPagerAdaptor extends FragmentStatePagerAdapter {
    public NewsPagerAdaptor(FragmentManager fm){
        super(fm);
    }
    private List<String> mCategories;
    private String mKeyword;

    public NewsPagerAdaptor(FragmentManager fm, List<String> list, String keyword){
        super(fm);
        mCategories = list;
        mKeyword=keyword;
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = NewsListFragment.newInstance(mCategories.get(position),mKeyword);
        return fragment;
    }

    @Override
    public int getCount(){
        return mCategories.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mCategories.get(position);
    }

}
