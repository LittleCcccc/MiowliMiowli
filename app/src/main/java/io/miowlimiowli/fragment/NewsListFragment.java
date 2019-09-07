package io.miowlimiowli.fragment;


import android.os.Bundle;

import java.util.List;

import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.manager.Manager;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
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



    @Override
    public void fetchNews(Runnable callback){

        Single<List<DisplayableNews>> single = null;
        single = Manager.getInstance().FetchDisplayableNewsbyCategoryandKeyword(mPageSize,mPageNo,mCategory,mKeyword);
        Disposable d = single.subscribe(displayableNews -> {
            if(mPageNo==1)
                setNewsList(displayableNews);
            else
                appendNewsList(displayableNews);
            callback.run();
        });

    }




}


