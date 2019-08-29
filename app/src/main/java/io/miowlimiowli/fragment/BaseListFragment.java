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
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.google.j2objc.annotations.ObjectiveCName;

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
    private SuperSwipeRefreshLayout mSwipeRefreshWidget;
    protected NewsListAdapter newsListAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    protected int mPageSize = 100;
    protected int mPageNo = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.news_list, container, false);
        init();
        /*
        mSwipeRefreshWidget = (SuperSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener(){
            @Override
            public void onRefresh(){
                refreshNews();
            }
            @Override
            public void onPullDistance(int distance){
                if(distance>2)
                {
                    requireMoreNews();
                }
            }
            @Override
            public void onPullEnable(boolean enable){

            }
        });
        */


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

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

    public void requireMoreNews(){
        mPageNo++;
        fetchNews();
    }

    public void refreshNews(){
        mPageNo = 1;
        fetchNews();
    }
}
