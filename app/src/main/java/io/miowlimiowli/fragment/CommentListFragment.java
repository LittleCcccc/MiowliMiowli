package io.miowlimiowli.fragment;

import java.util.List;

import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.manager.Manager;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CommentListFragment extends BaseListFragment {
    @Override
    public void fetchNews(Runnable callback){

        Single<List<DisplayableNews>> single = null;
        single = Manager.getInstance().fetch_comment_list();
        Disposable d = single.subscribe(new Consumer<List<DisplayableNews>>() {
            @Override
            public void accept(List<DisplayableNews> displayableNews) throws Exception {
                setNewsList(displayableNews);
                callback.run();
            }
        });
    }

    public static CommentListFragment newInstance(){
        CommentListFragment fragment = new CommentListFragment();
        return fragment;
    }
}
