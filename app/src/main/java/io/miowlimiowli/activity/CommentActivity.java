/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.support.v7.widget.RecyclerView;
import io.miowlimiowli.R;
import io.miowlimiowli.adapter.RecommandActivityRecommandRecyclerViewAdapter;

import android.support.design.widget.TabLayout;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.MenuInflater;


public class CommentActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, CommentActivity.class);
	}
	
	private Toolbar toolbar;
	private ConstraintLayout tabConstraintLayout;
	private TabLayout collectionTabBar;
	private RecyclerView recommandRecyclerView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.comment_activity);
		this.init();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	
		switch (menuItem.getItemId()) {
			case android.R.id.home: 
				this.onGroupPressed();
				return true;
			default:
				return super.onOptionsItemSelected(menuItem);
		}
	}
	
	private void init() {

		// Configure TabView component
		tabConstraintLayout = this.findViewById(R.id.tab_constraint_layout);
		
		// Configure Online component
		collectionTabBar = this.findViewById(R.id.collection_tab_bar);
		collectionTabBar.setOnClickListener((view) -> {
	this.onOnlineValueChanged();
});
		
		// Configure Recommand component
		recommandRecyclerView = this.findViewById(R.id.recommand_recycler_view);
		recommandRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		recommandRecyclerView.setAdapter(new RecommandActivityRecommandRecyclerViewAdapter());

	}
	
	public void onOnlineValueChanged() {
	
	}
	
	public void onGroupPressed() {
	
		this.finish();
	}
}
