/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.adapter;

import android.content.Intent;
import android.view.View;
import java.util.*;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.support.constraint.ConstraintLayout;
import io.miowlimiowli.R;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.view.LayoutInflater;


public class ListActivityRecommandRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final int NEWS_TWO_VIEW_HOLDER_VIEW_TYPE = 1;
	public static final int ADVERTISEMENT_THREE_VIEW_HOLDER_VIEW_TYPE = 2;
	
	public static final List<Integer> MOCK_DATA = Arrays.asList(NEWS_TWO_VIEW_HOLDER_VIEW_TYPE, ADVERTISEMENT_THREE_VIEW_HOLDER_VIEW_TYPE, NEWS_TWO_VIEW_HOLDER_VIEW_TYPE, ADVERTISEMENT_THREE_VIEW_HOLDER_VIEW_TYPE, NEWS_TWO_VIEW_HOLDER_VIEW_TYPE, ADVERTISEMENT_THREE_VIEW_HOLDER_VIEW_TYPE);
	
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	
		switch (viewType) {
			case NEWS_TWO_VIEW_HOLDER_VIEW_TYPE: 
				return new NewsTwoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_two_view_holder, parent, false));
			case ADVERTISEMENT_THREE_VIEW_HOLDER_VIEW_TYPE: 
				return new AdvertisementThreeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.advertisement_three_view_holder, parent, false));
		}
		
		throw new RuntimeException("Unsupported view type");
	}
	
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
	
		// Here you can bind RecyclerView item data.
	}
	
	@Override
	public int getItemViewType(int position) {
	
		return MOCK_DATA.get(position);
	}
	
	@Override
	public int getItemCount() {
	
		return MOCK_DATA.size();
	}
	
	
	public static class NewsTwoViewHolder extends RecyclerView.ViewHolder {
		private ConstraintLayout newsViewConstraintLayout;
		private ImageView newsPhotoImageView;
		private TextView newsTitleTextView;
		private TextView newsTimeTextView;
		private Button newsParentButton;
		public NewsTwoViewHolder(View itemView) {
			super(itemView);
			init();
		}
		
		public void init() {
		
			// Configure View component
			newsViewConstraintLayout = this.itemView.findViewById(R.id.news_view_constraint_layout);
			
			// Configure bg-news-image component
			newsPhotoImageView = this.itemView.findViewById(R.id.news_photo_image_view);
			
			// Configure Title component
			newsTitleTextView = this.itemView.findViewById(R.id.news_title_text_view);
			
			// Configure Time component
			newsTimeTextView = this.itemView.findViewById(R.id.news_time_text_view);

		}


	}
	
	
	public static class AdvertisementThreeViewHolder extends RecyclerView.ViewHolder {
		private ImageView adPhotoImageView;
		private TextView adTitleTextView;
		private TextView adDetailTextView;
		private TextView adTimeTextView;
		public AdvertisementThreeViewHolder(View itemView) {
			super(itemView);
			init();
		}
		
		public void init() {
		
			// Configure bg-event-image component
			adPhotoImageView = this.itemView.findViewById(R.id.ad_photo_image_view);
			
			// Configure AdTItle component
			adTitleTextView = this.itemView.findViewById(R.id.ad_title_text_view);
			
			// Configure AdDetail component
			adDetailTextView = this.itemView.findViewById(R.id.ad_detail_text_view);
			
			// Configure AdTime component
			adTimeTextView = this.itemView.findViewById(R.id.ad_time_text_view);
		}
	}
}
