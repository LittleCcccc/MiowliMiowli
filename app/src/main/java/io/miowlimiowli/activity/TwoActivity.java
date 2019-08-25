/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import java.util.Arrays;
import io.miowlimiowli.R;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import io.miowlimiowli.fragment.ListActivity;
import android.support.design.widget.BottomNavigationView;
import io.miowlimiowli.fragment.ProfileActivity;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import io.miowlimiowli.adapter.TwoActivityPagerAdapter;
import android.content.Context;
import java.util.List;
import io.miowlimiowli.fragment.RecommandActivity;
import android.os.Bundle;
import android.content.Intent;


public class TwoActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, TwoActivity.class);
	}
	
	private BottomNavigationView bottomNavigationBar;
	private ViewPager viewPager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.two_activity);
		init();
	}
	
	public void init() {
	
		// Configure Tab Bar #1 component
		bottomNavigationBar = this.findViewById(R.id.bottom_navigation_bar);
		
		// Configure View Pager
		viewPager = findViewById(R.id.view_pager);
		
		// Configure View Pager Adapter
		List<Fragment> fragments = Arrays.asList(RecommandActivity.newInstance(), ListActivity.newInstance(), ProfileActivity.newInstance());
		viewPager.setAdapter(new TwoActivityPagerAdapter(getSupportFragmentManager(), fragments));
		bottomNavigationBar.setOnNavigationItemSelectedListener((menuItem) -> {
	onTabSelected(menuItem);
	return true;
});
	}
	
	public void onTabSelected(MenuItem menuItem) {
	
		switch (menuItem.getItemId()) {
			case R.id.recommand_activity_menu_item: 
				viewPager.setCurrentItem(0, true);
				break;
			case R.id.list_activity_menu_item: 
				viewPager.setCurrentItem(1, true);
				break;
			case R.id.profile_activity_menu_item: 
				viewPager.setCurrentItem(2, true);
				break;
		}
	}
}
