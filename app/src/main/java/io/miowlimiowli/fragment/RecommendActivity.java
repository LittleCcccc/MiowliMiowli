/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;

import android.os.Bundle;

import io.miowlimiowli.R;
import io.miowlimiowli.activity.SearchActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class RecommendActivity extends Fragment {

	public static RecommendActivity newInstance() {
	
		RecommendActivity fragment = new RecommendActivity();
		Bundle arguments = new Bundle();
		fragment.setArguments(arguments);
		return fragment;
	}
	private FloatingActionButton searchButton;
	private String mKeyword = "";
	protected int mPageSize = 10;
	protected int mPageNo = 1;
	//private NewsListFragment newsListFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.recommend_activity, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);
		init();
	}

	public void init() {
		//newsListFragment = this.getView().findViewById(R.news_id.news_list_fragment);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		/**
		 * 在这里写新闻推荐
		 */
		NewsListFragment fragment = NewsListFragment.newInstance("体育","");
		fragmentTransaction.add(R.id.news_list_layout,fragment);
		fragmentTransaction.commit();

		searchButton = this.getView().findViewById(R.id.search_button);
		searchButton.setOnClickListener((view) -> {
			//System.out.println("按下浮动搜索按钮");
			//boolean ret = this.getActivity().onSearchRequested();
			//System.out.println(ret);
			this.onSearchButtonPressed();
		});
}

	private void onSearchButtonPressed() {
		startSearchActivity();
	}

	public void startSearchActivity(){
		this.getActivity().startActivity(SearchActivity.newIntent(this.getContext()));
	}
}
