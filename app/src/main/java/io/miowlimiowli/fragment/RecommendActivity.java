/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;

import io.miowlimiowli.R;
import io.miowlimiowli.adapter.NewsListAdapter;
import io.miowlimiowli.manager.Manager;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class RecommendActivity extends Fragment {

	public static RecommendActivity newInstance() {
	
		RecommendActivity fragment = new RecommendActivity();
		Bundle arguments = new Bundle();
		fragment.setArguments(arguments);
		return fragment;
	}

	private SearchView mSearchView;
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
		//newsListFragment = this.getView().findViewById(R.id.news_list_fragment);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		NewsListFragment fragment = NewsListFragment.newInstance("体育","");
		fragmentTransaction.add(R.id.news_list_layout,fragment);
		fragmentTransaction.commit();

		SearchManager searchManager=(SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
		mSearchView = this.getView().findViewById(R.id.news_search_view);

		mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
		mSearchView.setIconifiedByDefault(false);

		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				mKeyword=query;
				searchNews();

				mSearchView.clearFocus();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});


	}

	public void searchNews()
	{
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		NewsListFragment fragment = NewsListFragment.newInstance("","海贼王");
		fragmentTransaction.add(R.id.news_list_layout,fragment);
		fragmentTransaction.commit();
	}

}
