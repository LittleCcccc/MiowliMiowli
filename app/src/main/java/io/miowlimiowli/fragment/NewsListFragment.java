package io.miowlimiowli.fragment;


import android.os.Bundle;

import java.util.List;

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

    public void appendNewsList(List<DisplayableNews> list){
        newsListAdapter.appendData(list);
    }

    @Override
    public void fetchNews(Runnable callback){

        Single<List<DisplayableNews>> single = null;
        single = Manager.getInstance().FetchDisplayableNewsbyCategoryandKeyword(mPageSize,mPageNo,mCategory,mKeyword);
        single.subscribe(new Consumer<List<DisplayableNews>>() {
            @Override
            public void accept(List<DisplayableNews> displayableNews) throws Exception {
                if(mPageNo==1)
                    setNewsList(displayableNews);
                else
                    appendNewsList(displayableNews);
                callback.run();
            }
        });

    }




}


