/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import io.miowlimiowli.R;
import io.miowlimiowli.adapter.CollectionPagerAdapter;
import io.miowlimiowli.fragment.CollectionListFragment;
import io.miowlimiowli.fragment.HistoryListFragment;

import com.google.android.material.tabs.TabLayout;
import android.view.MenuItem;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommentActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, CommentActivity.class);
	}


	CollectionPagerAdapter collectionPagerAdapter;
	private TabLayout mTabLayout;
	ViewPager viewPager;
	private List<String> mCategories = new ArrayList<>();

	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.comment_activity);

		viewPager = this.findViewById(R.id.pager);
		viewPager.setOffscreenPageLimit(3);
		mTabLayout = this.findViewById(R.id.tab_layout);
		for(int i=0;i<3;i++)
			mTabLayout.addTab(mTabLayout.newTab());
		List<Fragment> fragments = Arrays.asList(CollectionListFragment.newInstance(), CollectionListFragment.newInstance(), HistoryListFragment.newInstance());
		collectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager(),fragments);
		viewPager.setAdapter(collectionPagerAdapter);
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

	public void onGroupPressed() {

		this.finish();
	}
}
