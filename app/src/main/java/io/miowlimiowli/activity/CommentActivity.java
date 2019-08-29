/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import io.miowlimiowli.R;
import io.miowlimiowli.adapter.NewsListAdapter;

import com.google.android.material.tabs.TabLayout;
import android.view.MenuItem;
import android.os.Bundle;


public class CommentActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, CommentActivity.class);
	}
	
	private Toolbar toolbar;
	private ConstraintLayout tabConstraintLayout;
	private TabLayout collectionTabBar;
	private RecyclerView recommendRecyclerView;
	
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
		
		// Configure Recommend component
		recommendRecyclerView = this.findViewById(R.id.recommend_recycler_view);
		recommendRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		recommendRecyclerView.setAdapter(new NewsListAdapter(getApplicationContext()));

	}
	
	public void onOnlineValueChanged() {
	
	}
	
	public void onGroupPressed() {
	
		this.finish();
	}
}
