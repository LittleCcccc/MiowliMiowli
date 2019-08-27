/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import io.miowlimiowli.R;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import io.miowlimiowli.adapter.RecommandActivityRecommandRecyclerViewAdapter;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;


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
