/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.activity;

import android.content.Intent;

import io.miowlimiowli.R;
import io.miowlimiowli.adapter.CommentAdapter;
import io.miowlimiowli.fragment.CommentFragment;
import io.miowlimiowli.manager.DisplayableComment;
import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.manager.Manager;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NewsdetailActivity extends AppCompatActivity {

	private TextView readcountTextView;
	private ImageButton commentButton;
	private EditText commentEditTextView;
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
	private LinearLayout commentGroupConstraintLayout;
	private ConstraintLayout commentConstraintLayout;
	private TextView commentNameTextView;
	private TextView commentContentTextView;
	private TextView commentTimeTextView;
	private RecyclerView cmtRecyclerView;
	private CommentAdapter cmtAdapter;

	public static String NEWS_ID = "NEWS_ID";

	public DisplayableNews news;
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
				news=displayableNews;
				titleTextView.setText(news.title);
				contentTextView.setText(news.content);
				Date date = news.publish_time;
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String time = formatter.format(date);
				timeTextView.setText(time);
				if (!news.image_urls.isEmpty()) {
					String url = news.image_urls.get(0);
					Glide.with(NewsdetailActivity.this)
							.load(url)
							.apply(new RequestOptions().dontTransform().placeholder(R.drawable.placeholder))
							.into(newsPhotoImageView);
				}

				news.setIsread(true);
				readcountTextView.setText(news.readcount.toString() + "阅读");



		}});
		//String news_picture_url = getIntent().getStringExtra(NEWS_PICTURE_URL);
		this.setContentView(R.layout.newsdetail_activity);
		this.init();
	}

	private void init() {

		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		cmtRecyclerView = this.findViewById(R.id.comment_recycler_view);
		cmtAdapter = new CommentAdapter(this);
		cmtRecyclerView.setLayoutManager(llm);
		cmtRecyclerView.setAdapter(cmtAdapter);

		commentButton = this.findViewById(R.id.comment_button);
		commentEditTextView = this.findViewById(R.id.comment_textview);
		commentButton.setOnClickListener((view) -> {
					this.onCommentButtonPressed();
				});

		readcountTextView = this.findViewById(R.id.readcount);

		// Configure Navigation Bar #2 component
		toolbar = this.findViewById(R.id.toolbar);

		
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



		// Configure Content component
		contentTextView = this.findViewById(R.id.content_text_view);
		//contentTextView.setText(news.content);
		// Configure Time component
		timeTextView = this.findViewById(R.id.time_text_view);
		//timeTextView.setText(news.publish_time.toString());
		// Configure Image component
		newsPhotoImageView = this.findViewById(R.id.news_photo_image_view);
		
		// Configure comment component
		commentConstraintLayout = this.findViewById(R.id.comment_constraint_layout);
		
		// Configure commentName component
		commentNameTextView = this.findViewById(R.id.comment_name_text_view);
		
		// Configure commentContent component
		commentContentTextView = this.findViewById(R.id.comment_content_text_view);
		
		// Configure commentTime component
		commentTimeTextView = this.findViewById(R.id.comment_time_text_view);


	
	}
	
	public void onShareButtonPressed() {

	}
	
	public void onStarButtonPressed() {
	
	}
	
	public void onCollectButtonPressed() {
	
	}

	public void onCommentButtonPressed()
	{
		System.out.println("bao bao e le");
		String comment = commentEditTextView.getText().toString();
		Single<DisplayableComment> single = Manager.getInstance().add_comment(news.id,comment,new Date());
		Disposable d = single.subscribe((cmt)->{
			List<DisplayableComment> list = new ArrayList<>();
			list.add(cmt);
			System.out.println("bbbl");
			System.out.println(list.size());
			System.out.println(cmt.content);
			cmtAdapter.appendData(list);
		});
	}
}
