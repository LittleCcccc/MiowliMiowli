package io.miowlimiowli.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.miowlimiowli.fragment.CollectionListFragment;
import io.miowlimiowli.fragment.NewsListFragment;
import io.miowlimiowli.manager.DisplayableNews;

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
    public CollectionPagerAdapter(FragmentManager fm){
        super(fm);
    }
    public List<Fragment> fragments;
    List<String> mCategories = new ArrayList<>();

    public CollectionPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments){
        super(fragmentManager);
        this.fragments = fragments;
        mCategories.add("收藏");
        mCategories.add("评论");
        mCategories.add("历史");
    }

    @Override
    public Fragment getItem(int position){
        return fragments.get(position);
    }

    @Override
    public int getCount(){
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mCategories.get(position);
    }


}
