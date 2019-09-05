package io.miowlimiowli.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.miowlimiowli.R;
import io.miowlimiowli.adapter.CommentAdapter;
import io.miowlimiowli.manager.DisplayableComment;
import io.miowlimiowli.manager.Manager;
import io.miowlimiowli.others.VerticalSpaceItemDecoration;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CommentFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    public CommentAdapter commentListAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    public String news_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list, container, false);
        mRecyclerView = view.findViewById(R.id.news_recycler_view);

        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setNestedScrollingEnabled(false);

        commentListAdapter = new CommentAdapter(getContext());
        commentListAdapter.setCommentClickListener((View itemView,int position)->{
            //DisplayableNews news=commentListAdapter.getNews(position);
            // startNewsDetails(news);
        });


        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(24));
        mRecyclerView.setAdapter(commentListAdapter);


        return view;
    }

    public static CommentFragment newInstance(String news_id){
        Bundle args = new Bundle();
        CommentFragment fragment = new CommentFragment();
        args.putString("news_id",news_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchComment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        news_id=getArguments().getString("news_id");
    }


    public void setCommentList(List<DisplayableComment> list)
    {
        commentListAdapter.setData(list);
    }

    public void fetchComment(){
        Single<List<DisplayableComment>> single = null;
        single =  Manager.getInstance().fetch_comment_by_news_id(news_id);
        Disposable d = single.subscribe(new Consumer<List<DisplayableComment>>() {
            @Override
            public void accept(List<DisplayableComment> displayableComment) throws Exception {
                setCommentList(displayableComment);
            }
        });
    }
}
