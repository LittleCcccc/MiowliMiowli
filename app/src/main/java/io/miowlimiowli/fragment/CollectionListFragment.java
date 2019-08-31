package io.miowlimiowli.fragment;

import java.util.List;

import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.manager.Manager;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class CollectionListFragment extends BaseListFragment {
    @Override
    public void fetchNews(Runnable callback){

        Single<List<DisplayableNews>> single = null;
        single = Manager.getInstance().fetch_like_list();
        single.subscribe(new Consumer<List<DisplayableNews>>() {
            @Override
            public void accept(List<DisplayableNews> displayableNews) throws Exception {
                setNewsList(displayableNews);
                callback.run();
            }
        });

    }

    public static CollectionListFragment newInstance(){
        CollectionListFragment fragment = new CollectionListFragment();
        return fragment;
    }
}
