/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.fragment;

import io.miowlimiowli.R;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import io.miowlimiowli.activity.*;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends Fragment {
	
	public static ProfileActivity newInstance() {
	
		ProfileActivity fragment = new ProfileActivity();
		Bundle arguments = new Bundle();
		fragment.setArguments(arguments);
		return fragment;
	}
	
	private Button avatarButton;
	private TextView myNicknameTextView;
	private TextView myTitleTextView;
	private TextView myIntroductionTextView;
	private TextView collectSumTextView;
	private Button collectionButton;
	private TextView commentSumTextView;
	private Button commentButton;
	private TextView brouseSumTextView;
	private Button brouserButton;
	private ImageButton settingsButton;


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		return inflater.inflate(R.layout.profile_activity, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	
		super.onViewCreated(view, savedInstanceState);
		System.out.println("hahaha");
		init();

	}
	
	public void onAvatarPressed() {
	
	}
	
	public void onColletctionPressed() {
	
		this.startCommentActivity();
	}

	public void onCommentPressed() {
	
		this.startCommentActivity();
	}
	
	public void onBrouserPressed() {
	
		this.startCommentActivity();
	}
	
	public void onSettingsButtonPressed() {
	
		this.startSettingsActivity();
	}
	
	public void init() {
	
		// Configure Avatar component
		avatarButton = this.getView().findViewById(R.id.avatar_button);
		avatarButton.setOnClickListener((view) -> {
	this.onAvatarPressed();
});
		
		// Configure MyNickname component
		myNicknameTextView = this.getView().findViewById(R.id.my_nickname_text_view);
		
		// Configure MyTitle component
		myTitleTextView = this.getView().findViewById(R.id.my_title_text_view);
		
		// Configure MyIntroduction component
		myIntroductionTextView = this.getView().findViewById(R.id.my_introduction_text_view);
		
		// Configure 365 component
		collectSumTextView = this.getView().findViewById(R.id.collect_sum_text_view);
		
		// Configure Collection component
		collectionButton = this.getView().findViewById(R.id.clooection_button);
		collectionButton.setOnClickListener((view) -> {
	this.onColletctionPressed();
});
		
		// Configure 58k component
		commentSumTextView = this.getView().findViewById(R.id.comment_sum_text_view);
		
		// Configure DisplayableComment component
		commentButton = this.getView().findViewById(R.id.comment_button);
		commentButton.setOnClickListener((view) -> {
	this.onCommentPressed();
});
		
		// Configure 326 component
		brouseSumTextView = this.getView().findViewById(R.id.brouse_sum_text_view);
		
		// Configure Brouse component
		brouserButton = this.getView().findViewById(R.id.brouser_button);
		brouserButton.setOnClickListener((view) -> {
	this.onBrouserPressed();
});
		
		// Configure Setting component
		settingsButton = this.getView().findViewById(R.id.settings_button);
		settingsButton.setOnClickListener((view) -> {
	this.onSettingsButtonPressed();
});
	}
	
	private void startSettingsActivity() {
	
		this.getActivity().startActivity(SettingsActivity.newIntent(this.getContext()));
	}
	
	private void startCommentActivity() {
	
		this.getActivity().startActivity(CommentActivity.newIntent(this.getContext()));
	}
}
