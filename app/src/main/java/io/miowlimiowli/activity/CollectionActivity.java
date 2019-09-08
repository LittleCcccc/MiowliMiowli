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
import io.miowlimiowli.fragment.CommentListFragment;
import io.miowlimiowli.fragment.HistoryListFragment;

import com.google.android.material.tabs.TabLayout;

import android.graphics.Typeface;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CollectionActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, CollectionActivity.class);
	}

	CollectionPagerAdapter collectionPagerAdapter;
	private TabLayout mTabLayout;
	ViewPager viewPager;
	private List<String> mCategories = new ArrayList<>();
	public static String position="position";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.comment_activity);

		String pos = getIntent().getStringExtra(position);

		viewPager = this.findViewById(R.id.pager);
		viewPager.setOffscreenPageLimit(3);

		mTabLayout = this.findViewById(R.id.tab_layout);
		for(int i=0;i<3;i++)
			mTabLayout.addTab(mTabLayout.newTab());
		List<Fragment> fragments = Arrays.asList(CollectionListFragment.newInstance(), CommentListFragment.newInstance(), HistoryListFragment.newInstance());
		collectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager(),fragments);
		viewPager.setAdapter(collectionPagerAdapter);
		viewPager.setCurrentItem(Integer.parseInt(pos));
		//changeTabsFont();

	}

	private void changeTabsFont() {
		ViewGroup vg = (ViewGroup) mTabLayout.getChildAt(0);
		int tabsCount = vg.getChildCount();
		for (int j = 0; j < tabsCount; j++) {
			ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
			int tabChildsCount = vgTab.getChildCount();
			for (int i = 0; i < tabChildsCount; i++) {
				View tabViewChild = vgTab.getChildAt(i);
				if (tabViewChild instanceof TextView) {
					((TextView) tabViewChild).setTypeface(getResources().getFont(R.font.fzltxh), Typeface.NORMAL);
				}
			}
		}
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
