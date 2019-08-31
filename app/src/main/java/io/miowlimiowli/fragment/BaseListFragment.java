package io.miowlimiowli.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;

import java.util.List;

import io.miowlimiowli.R;
import io.miowlimiowli.adapter.NewsListAdapter;
import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.others.VerticalSpaceItemDecoration;

abstract public class BaseListFragment extends Fragment {

    protected List<DisplayableNews> mNews;
    protected RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    private SuperSwipeRefreshLayout mSwipeRefreshWidget;
    protected NewsListAdapter newsListAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected int mPageSize = 10;
    protected int mPageNo = 1;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    private View createHeaderView() {
        View headerView = LayoutInflater.from(mSwipeRefreshWidget.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(mSwipeRefreshWidget.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.news_list, container, false);

        // init SuperSwipeRefreshLayout
        mSwipeRefreshWidget = (SuperSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setHeaderView(createHeaderView());
        mSwipeRefreshWidget.setFooterView(createFooterView());
        mSwipeRefreshWidget.setTargetScrollWithLayout(true);


        mSwipeRefreshWidget.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener(){
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        refreshNews(()->{
                            mSwipeRefreshWidget.setRefreshing(false);
                            progressBar.setVisibility(View.GONE);
                        });

                    }
                });
            }
            @Override
            public void onPullDistance(int distance){
            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        mSwipeRefreshWidget.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener(){
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        requireMoreNews(()->{
                            footerImageView.setVisibility(View.VISIBLE);
                            footerProgressBar.setVisibility(View.GONE);
                            mSwipeRefreshWidget.setLoadMore(false);
                        });
                    }
                });
            }


            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
            @Override
            public void onPushDistance(int distance) {
                // TODO 上拉距离

            }
        });

        // Configure Recommend component
        mRecyclerView = view.findViewById(R.id.news_recycler_view);

        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        newsListAdapter = new NewsListAdapter(getContext());
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(32));
        mRecyclerView.setAdapter(newsListAdapter);


        DividerItemDecoration recommendRecyclerViewDecoration = new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL);
        recommendRecyclerViewDecoration.setDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.recommend_activity_recommend_recycler_view_separator));
        mRecyclerView.addItemDecoration(recommendRecyclerViewDecoration);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchNews(()->{});
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    public void setNewsList(List<DisplayableNews> list)
    {
        newsListAdapter.setData(list);
    }

    abstract public void fetchNews(Runnable callback);

    public void requireMoreNews(Runnable callback){
        mPageNo++;
        fetchNews(callback);
    }

    public void refreshNews(Runnable callback){
        mPageNo = 1;
        fetchNews(callback);
    }

}
