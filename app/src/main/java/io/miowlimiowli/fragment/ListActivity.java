/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;
import io.miowlimiowli.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;

import io.miowlimiowli.adapter.NewsPagerAdaptor;
import io.miowlimiowli.adapter.RecommandActivityRecommandRecyclerViewAdapter;
import io.miowlimiowli.dialog.ListActivityTypeButtonSheet;

import androidx.recyclerview.widget.DividerItemDecoration;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import android.widget.ImageButton;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.SearchView;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import java.util.List;


public class ListActivity extends Fragment implements ListActivityTypeButtonSheet.TypeDialogFragment_Listener {

	NewsPagerAdaptor newsPagerAdaptor;
	ViewPager viewPager;
	private boolean type[];
	
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
	public void getDataFrom_TypeDialogFragment(boolean[] type){
		for(int i=1;i<=10;i++)
			this.type[i]=type[i];
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		type = new boolean[12];

		return inflater.inflate(R.layout.list_activity, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		newsPagerAdaptor = new NewsPagerAdaptor(getChildFragmentManager());
		viewPager = view.findViewById(R.id.pager);
		viewPager.setAdapter(newsPagerAdaptor);
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
