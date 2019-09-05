package io.miowlimiowli.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.miowlimiowli.R;
import io.miowlimiowli.activity.NewsdetailActivity;
import io.miowlimiowli.adapter.CommentAdapter;
import io.miowlimiowli.adapter.NewsListAdapter;
import io.miowlimiowli.manager.DisplayableComment;
import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.manager.Manager;
import io.miowlimiowli.others.VerticalSpaceItemDecoration;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CommentListFragmentTmp extends Fragment {
    protected CommentAdapter commentAdapter;
    protected List<DisplayableComment> mcomment = new ArrayList<>();
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;

/*
    public void fetchComment(){

        Single<List<DisplayableComment>> single = null;
        single = Manager.getInstance().fetch_comment_list();
        Disposable d = single.subscribe(new Consumer<List<DisplayableNews>>() {
            @Override
            public void accept(List<DisplayableNews> displayableNews) throws Exception {
                setCommentList(displayableNews);
                callback.run();
            }
        });

    }


 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.comment_list, container, false);

        mRecyclerView = view.findViewById(R.id.news_recycler_view);

        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        commentAdapter = new CommentAdapter(getContext());
        commentAdapter.setCommentClickListener((View itemView,int position)->{
            //DisplayableNews news=commentAdapter.getNews(position);
            //startNewsDetails(news);
        });


        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(24));
        mRecyclerView.setAdapter(commentAdapter);


        DividerItemDecoration recommendRecyclerViewDecoration = new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL);
        recommendRecyclerViewDecoration.setDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.recommend_activity_recommend_recycler_view_separator));
        mRecyclerView.addItemDecoration(recommendRecyclerViewDecoration);

        return view;

    }

    public static CollectionListFragment newInstance(){
        CollectionListFragment fragment = new CollectionListFragment();
        return fragment;
    }

    public void startNewsDetails(DisplayableNews news)
    {
        Intent intent = new Intent(this.getContext(), NewsdetailActivity.class);
        intent.putExtra(NewsdetailActivity.NEWS_ID,news.id);
        startActivity(intent);
    }

}
