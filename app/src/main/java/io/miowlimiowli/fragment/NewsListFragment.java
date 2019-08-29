package io.miowlimiowli.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.miowlimiowli.R;
import io.miowlimiowli.adapter.NewsListAdapter;
import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.manager.Manager;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class NewsListFragment extends BaseListFragment {


    private String mKeyword="";
    private String mCategory;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mCategory=getArguments().getString("category");
        mKeyword=getArguments().getString("keyword");
    }

    public static NewsListFragment newInstance(String category,String keyword){
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        args.putString("category",category);
        args.putString("keyword",keyword);
        fragment.setArguments(args);
        return fragment;
    }

    public void setNewsList(List<DisplayableNews> list)
    {
        newsListAdapter.setData(list);
    }

    @Override
    public void fetchNews(){

        Single<List<DisplayableNews>> single = null;
        single = Manager.getInstance().FetchDisplayableNewsbyCategoryandKeyword(mPageSize,mPageNo,mCategory,mKeyword);
        single.subscribe(new Consumer<List<DisplayableNews>>() {
            @Override
            public void accept(List<DisplayableNews> displayableNews) throws Exception {
                setNewsList(displayableNews);

            }
        });

    }

}
