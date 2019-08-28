package io.miowlimiowli.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import io.miowlimiowli.R;
import io.miowlimiowli.adapter.NewsPagerAdaptor;

public class NewsListFragment extends Fragment {
    NewsPagerAdaptor newsPagerAdaptor;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.news_list,container,false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        newsPagerAdaptor = new NewsPagerAdaptor(getChildFragmentManager());
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(newsPagerAdaptor);
    }
}
