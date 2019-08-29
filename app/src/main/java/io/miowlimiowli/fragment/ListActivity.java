/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;
import io.miowlimiowli.R;

import android.view.ViewGroup;
import android.view.LayoutInflater;

import io.miowlimiowli.adapter.NewsPagerAdaptor;
import io.miowlimiowli.dialog.ListActivityTypeButtonSheet;
import io.miowlimiowli.manager.Manager;

import androidx.fragment.app.Fragment;
import android.widget.ImageButton;

import androidx.appcompat.widget.SearchView;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends Fragment implements ListActivityTypeButtonSheet.TypeDialogFragment_Listener {

	NewsPagerAdaptor newsPagerAdaptor;
	private TabLayout mTabLayout;
	ViewPager viewPager;
	private String mKeyword = "";
	private List<String> mCategories = new ArrayList<>();
	private boolean type[];

	public void setKeyword(String keyword){
		mKeyword = keyword;
		newsPagerAdaptor.notifyDataSetChanged();
	}
	
	public static ListActivity newInstance() {
		ListActivity fragment = new ListActivity();
		//Bundle arguments = new Bundle();
		//fragment.setArguments(arguments);
		return fragment;
	}

	private ImageButton moreTypeButton;
	private ConstraintLayout newsSearchConstraintLayout;
	private SearchView newsSearchBarSearchView;

	@Override
	public void getDataFrom_TypeDialogFragment(boolean[] type){
		for(int i=1;i<=10;i++)
			this.type[i]=type[i];
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mCategories = Manager.getInstance().getCat_list();
		//mCategories = Manager.getInstance().

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		type = new boolean[12];

		View view = inflater.inflate(R.layout.list_activity, container, false);
		viewPager = view.findViewById(R.id.pager);
		viewPager.setOffscreenPageLimit(3);

		mTabLayout = view.findViewById(R.id.tab_layout);
		for(int i=0;i<mCategories.size();i++)
			mTabLayout.addTab(mTabLayout.newTab());

		newsPagerAdaptor = new NewsPagerAdaptor(getChildFragmentManager(),mCategories,mKeyword);
		viewPager.setAdapter(newsPagerAdaptor);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		init();
	}
	
	public void onNewsTypeValueChanged() {
	
	}
	
	public void onMoreTypeButtonPressed() {
		ListActivityTypeButtonSheet listActivityTypeButtonSheet = new ListActivityTypeButtonSheet();
		Bundle bundle = new Bundle();
		for(int i=1;i<=10;i++)
		{
			bundle.putBoolean("type" + i, type[i]);
		}
		listActivityTypeButtonSheet.setArguments(bundle);
		listActivityTypeButtonSheet.setTargetFragment(this,1);


		listActivityTypeButtonSheet.show(getFragmentManager(),"ListActivityTypeButtonSheet");
	}

	public void init() {



		// Configure Button component
		moreTypeButton = this.getView().findViewById(R.id.more_type_button);
		moreTypeButton.setOnClickListener((view) -> {
	this.onMoreTypeButtonPressed();
});

	}


}
