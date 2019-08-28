/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.*;


public class TwoActivityPagerAdapter extends FragmentPagerAdapter {
	public final List<Fragment> fragments;
	
	public TwoActivityPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
		super(fragmentManager);
		this.fragments = fragments;
	}
	
	@Override
	public Fragment getItem(int position) {
	
		return fragments.get(position);
	}
	
	@Override
	public int getCount() {
	
		return fragments.size();
	}
}
