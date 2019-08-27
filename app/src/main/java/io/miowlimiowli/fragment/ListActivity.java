/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;
import android.app.DialogFragment;
import io.miowlimiowli.R;
import io.miowlimiowli.dialog.ListActivityMoreTypeButtonSheet;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import io.miowlimiowli.activity.*;
import io.miowlimiowli.adapter.RecommandActivityRecommandRecyclerViewAdapter;
import io.miowlimiowli.dialog.ListActivityTypeButtonSheet;

import android.support.v7.widget.DividerItemDecoration;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;
import android.support.v4.content.ContextCompat;
import java.util.*;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;






public class ListActivity extends Fragment {
	
	public static ListActivity newInstance() {
	
		ListActivity fragment = new ListActivity();
		Bundle arguments = new Bundle();
		fragment.setArguments(arguments);
		return fragment;
	}
	
	private TabLayout newsTypeTabBar;
	private ImageButton moreTypeButton;
	private RecyclerView recommandRecyclerView;
	private ConstraintLayout newsSearchConstraintLayout;
	private SearchView newsSearchBarSearchView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		return inflater.inflate(R.layout.list_activity, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	
		super.onViewCreated(view, savedInstanceState);
		init();
	}
	
	public void onNewsTypeValueChanged() {
	
	}
	
	public void onMoreTypeButtonPressed() {
		new ListActivityTypeButtonSheet().show(getFragmentManager(),"ListActivityTypeButtonSheet");
	}
	
	public void init() {
	
		// Configure Online component
		newsTypeTabBar = this.getView().findViewById(R.id.news_type_tab_bar);
		newsTypeTabBar.setOnClickListener((view) -> {
	this.onNewsTypeValueChanged();
});
		
		// Configure Button component
		moreTypeButton = this.getView().findViewById(R.id.more_type_button);
		moreTypeButton.setOnClickListener((view) -> {
	this.onMoreTypeButtonPressed();
});
		
		// Configure Recommand component
		recommandRecyclerView = this.getView().findViewById(R.id.recommand_recycler_view);
		recommandRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
		recommandRecyclerView.setAdapter(new RecommandActivityRecommandRecyclerViewAdapter());
		DividerItemDecoration recommandRecyclerViewDecoration = new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL);
		recommandRecyclerViewDecoration.setDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.list_activity_recommand_recycler_view_separator));
		recommandRecyclerView.addItemDecoration(recommandRecyclerViewDecoration);
		
		// Configure SearchView component
		newsSearchConstraintLayout = this.getView().findViewById(R.id.news_search_constraint_layout);
		
		// Configure Search component
		newsSearchBarSearchView = this.getView().findViewById(R.id.news_search_bar_search_view);
	}
}
