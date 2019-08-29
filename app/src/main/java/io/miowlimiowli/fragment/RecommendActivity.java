/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;

import android.os.Bundle;

import io.miowlimiowli.R;
import io.miowlimiowli.adapter.NewsListAdapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;


public class RecommendActivity extends Fragment {

	public static RecommendActivity newInstance() {
	
		RecommendActivity fragment = new RecommendActivity();
		Bundle arguments = new Bundle();
		fragment.setArguments(arguments);
		return fragment;
	}

	private SearchView newsSearchBarSearchView;
	//private NewsListFragment newsListFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		return inflater.inflate(R.layout.recommend_activity, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	
		super.onViewCreated(view, savedInstanceState);
		init();
	}

	public void init() {
		//newsListFragment = this.getView().findViewById(R.id.news_list_fragment);

		newsSearchBarSearchView = this.getView().findViewById(R.id.news_search_bar_search_view);
	}
}
