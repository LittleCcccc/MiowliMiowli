package io.miowlimiowli.fragment;

import java.util.List;

import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.manager.Manager;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class CommentListFragment extends BaseListFragment {
    @Override
    public void fetchNews(){

        Single<List<DisplayableNews>> single = null;
        single = Manager.getInstance().fetch_like_list();
        single.subscribe(new Consumer<List<DisplayableNews>>() {
            @Override
            public void accept(List<DisplayableNews> displayableNews) throws Exception {
                setNewsList(displayableNews);
            }
        });

    }
    public static CommentListFragment newInstance(){
        CommentListFragment fragment = new CommentListFragment();
        return fragment;
    }
}
