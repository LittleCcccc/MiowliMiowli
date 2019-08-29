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

abstract public class BaseListFragment extends Fragment {

    protected List<DisplayableNews> mNews;
    protected RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    protected NewsListAdapter newsListAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    protected int mPageSize = 100;
    protected int mPageNo = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        init();
        fetchNews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void init(){
        // Configure Recommend component
        mRecyclerView = this.getView().findViewById(R.id.news_recycler_view);

        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        newsListAdapter = new NewsListAdapter(getContext());
        mRecyclerView.setAdapter(newsListAdapter);

        DividerItemDecoration recommendRecyclerViewDecoration = new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL);
        recommendRecyclerViewDecoration.setDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.recommend_activity_recommend_recycler_view_separator));
        mRecyclerView.addItemDecoration(recommendRecyclerViewDecoration);

    }

    public void setNewsList(List<DisplayableNews> list)
    {
        newsListAdapter.setData(list);
    }

    abstract public void fetchNews();
}
