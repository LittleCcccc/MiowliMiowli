/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import io.miowlimiowli.R;
import android.view.View;
import android.view.ViewGroup;
import java.util.*;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import io.miowlimiowli.adapter.RecommandActivityRecommandRecyclerViewAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.app.AppCompatActivity;
import io.miowlimiowli.activity.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;


public class RecommandActivity extends Fragment {
	
	public static RecommandActivity newInstance() {
	
		RecommandActivity fragment = new RecommandActivity();
		Bundle arguments = new Bundle();
		fragment.setArguments(arguments);
		return fragment;
	}
	
	private RecyclerView recommandRecyclerView;
	private ConstraintLayout newsSearchConstraintLayout;
	private SearchView newsSearchBarSearchView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		return inflater.inflate(R.layout.recommand_activity, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	
		super.onViewCreated(view, savedInstanceState);
		init();
	}
	
	public void init() {
	
		// Configure Recommand component
		recommandRecyclerView = this.getView().findViewById(R.id.recommand_recycler_view);
		recommandRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
		recommandRecyclerView.setAdapter(new RecommandActivityRecommandRecyclerViewAdapter());
		DividerItemDecoration recommandRecyclerViewDecoration = new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL);
		recommandRecyclerViewDecoration.setDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.recommand_activity_recommand_recycler_view_separator));
		recommandRecyclerView.addItemDecoration(recommandRecyclerViewDecoration);
		
		// Configure SearchView component
		newsSearchConstraintLayout = this.getView().findViewById(R.id.news_search_constraint_layout);
		
		// Configure Search component
		newsSearchBarSearchView = this.getView().findViewById(R.id.news_search_bar_search_view);
	}
}
