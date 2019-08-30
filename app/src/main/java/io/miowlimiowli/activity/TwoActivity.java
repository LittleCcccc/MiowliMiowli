/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import java.util.Arrays;
import io.miowlimiowli.R;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import io.miowlimiowli.fragment.ListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import io.miowlimiowli.fragment.ProfileActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import io.miowlimiowli.adapter.TwoActivityPagerAdapter;
import android.content.Context;
import java.util.List;
import io.miowlimiowli.fragment.RecommendActivity;
import pub.devrel.easypermissions.EasyPermissions;

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

		String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

		if (EasyPermissions.hasPermissions(this, galleryPermissions)) {

		} else {
			EasyPermissions.requestPermissions(this, "我要存储！",
					101, galleryPermissions);
		}
	}
	
	public void init() {
	
		// Configure Tab Bar #1 component
		bottomNavigationBar = this.findViewById(R.id.bottom_navigation_bar);
		
		// Configure View Pager
		viewPager = findViewById(R.id.view_pager);
		
		// Configure View Pager Adapter
		List<Fragment> fragments = Arrays.asList(RecommendActivity.newInstance(), ListActivity.newInstance(), ProfileActivity.newInstance());
		viewPager.setAdapter(new TwoActivityPagerAdapter(getSupportFragmentManager(), fragments));
		bottomNavigationBar.setOnNavigationItemSelectedListener((menuItem) -> {
	onTabSelected(menuItem);
	return true;
});
	}
	
	public void onTabSelected(MenuItem menuItem) {
	
		switch (menuItem.getItemId()) {
			case R.id.recommend_activity_menu_item:
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
