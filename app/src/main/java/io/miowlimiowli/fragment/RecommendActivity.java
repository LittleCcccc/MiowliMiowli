/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;

import android.os.Bundle;

import io.miowlimiowli.R;
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

	private ConstraintLayout newsSearchConstraintLayout;
	private SearchView newsSearchBarSearchView;

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


		// Configure SearchView component
		newsSearchConstraintLayout = this.getView().findViewById(R.id.news_search_constraint_layout);
		
		// Configure Search component
		newsSearchBarSearchView = this.getView().findViewById(R.id.news_search_bar_search_view);
	}
}
