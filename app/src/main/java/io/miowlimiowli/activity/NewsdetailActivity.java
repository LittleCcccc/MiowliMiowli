/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import android.content.Intent;
import android.view.MenuItem;
import io.miowlimiowli.R;
import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.manager.Manager;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import android.content.Context;

import java.util.List;


public class NewsdetailActivity extends AppCompatActivity {
	
	public static Intent newIntent(Context context) {
	
		// Fill the created intent with the data you want to be passed to this Activity when it's opened.
		return new Intent(context, NewsdetailActivity.class);
	}
	
	private Toolbar toolbar;
	private SearchView writeCommentSearchView;
	private ImageButton shareButton;
	private ImageButton starButton;
	private ImageButton collectButton;
	private TextView titleTextView;
	private TextView contentTextView;
	private TextView timeTextView;
	private ImageView newsPhotoImageView;
	private ConstraintLayout commentGroupConstraintLayout;
	private ConstraintLayout commentConstraintLayout;
	private TextView commentNameTextView;
	private TextView commentContentTextView;
	private TextView commentTimeTextView;

	public static String NEWS_ID = "NEWS_ID";

	public static DisplayableNews news;
	public boolean newserror;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		String news_id = getIntent().getStringExtra(NEWS_ID);
		Single<DisplayableNews> single = null;
		single = Manager.getInstance().fetch_news_by_news_id(news_id);
		Disposable d = single.subscribe(new Consumer<DisplayableNews>() {
			@Override
			public void accept(DisplayableNews displayableNews) throws Exception {
				if(displayableNews==null)
					newserror = true;
				news=displayableNews;
			}
		});
		//String news_picture_url = getIntent().getStringExtra(NEWS_PICTURE_URL);
		this.setContentView(R.layout.newsdetail_activity);
		this.init();
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

	
	private void init() {



		// Configure Navigation Bar #2 component
		toolbar = this.findViewById(R.id.toolbar);
		
		// Configure WriteComment component
		writeCommentSearchView = this.findViewById(R.id.write_comment_search_view);
		
		// Configure Share component
		shareButton = this.findViewById(R.id.share_button);
		shareButton.setOnClickListener((view) -> {
	this.onShareButtonPressed();
});
		
		// Configure Star component
		starButton = this.findViewById(R.id.star_button);
		starButton.setOnClickListener((view) -> {
	this.onStarButtonPressed();
});
		
		// Configure Collect component
		collectButton = this.findViewById(R.id.collect_button);
		collectButton.setOnClickListener((view) -> {
	this.onCollectButtonPressed();
});

		// Configure Title component

		titleTextView = this.findViewById(R.id.title_text_view);
		//titleTextView.setText(news.title);

		if(newserror)
			titleTextView.setText("empty news");
		else
			titleTextView.setText(news.title);

		// Configure Content component
		contentTextView = this.findViewById(R.id.content_text_view);
		//contentTextView.setText(news.content);
		// Configure Time component
		timeTextView = this.findViewById(R.id.time_text_view);
		//timeTextView.setText(news.publish_time.toString());
		// Configure Image component
		newsPhotoImageView = this.findViewById(R.id.news_photo_image_view);

		// Configure DisplayableComment component
		commentGroupConstraintLayout = this.findViewById(R.id.comment_group_constraint_layout);
		
		// Configure comment component
		commentConstraintLayout = this.findViewById(R.id.comment_constraint_layout);
		
		// Configure commentName component
		commentNameTextView = this.findViewById(R.id.comment_name_text_view);
		
		// Configure commentContent component
		commentContentTextView = this.findViewById(R.id.comment_content_text_view);
		
		// Configure commentTime component
		commentTimeTextView = this.findViewById(R.id.comment_time_text_view);
		
		this.setupToolbar();
	}
	
	public void setupToolbar() {
	
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Additional Toolbar setup code can go here.
	}
	
	public void onShareButtonPressed() {
	
	}
	
	public void onStarButtonPressed() {
	
	}
	
	public void onCollectButtonPressed() {
	
	}
	
	public void onGroupPressed() {
	
		this.finish();
	}
}
