/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.adapter;

import android.widget.TextView;
import android.widget.Button;
import java.util.*;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import io.miowlimiowli.R;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;


public class RecommandActivityRecommandRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final int CELL_VIEW_HOLDER_VIEW_TYPE = 1;
	public static final int ADVERTISEMENT_VIEW_HOLDER_VIEW_TYPE = 2;
	
	public static final List<Integer> MOCK_DATA = Arrays.asList(CELL_VIEW_HOLDER_VIEW_TYPE, ADVERTISEMENT_VIEW_HOLDER_VIEW_TYPE, CELL_VIEW_HOLDER_VIEW_TYPE, ADVERTISEMENT_VIEW_HOLDER_VIEW_TYPE, CELL_VIEW_HOLDER_VIEW_TYPE, ADVERTISEMENT_VIEW_HOLDER_VIEW_TYPE);
	
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	
		switch (viewType) {
			case CELL_VIEW_HOLDER_VIEW_TYPE: 
				return new CellViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_view_holder, parent, false));
			case ADVERTISEMENT_VIEW_HOLDER_VIEW_TYPE: 
				return new AdvertisementViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.advertisement_view_holder, parent, false));
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
	
	
	public static class CellViewHolder extends RecyclerView.ViewHolder {
		private ImageView newsPhotoImageView;
		private TextView newsTitleTextView;
		private TextView newsTimeTextView;
		private Button newsParentButton;
		public CellViewHolder(View itemView) {
			super(itemView);
			init();
		}
		
		public void init() {
		
			// Configure bg-news-image component
			newsPhotoImageView = this.itemView.findViewById(R.id.news_photo_image_view);
			
			// Configure Title component
			newsTitleTextView = this.itemView.findViewById(R.id.news_title_text_view);
			
			// Configure Time component
			newsTimeTextView = this.itemView.findViewById(R.id.news_time_text_view);
			
			// Configure Button component
			newsParentButton = this.itemView.findViewById(R.id.news_parent_button);
			newsParentButton.setOnClickListener((view) -> {
	this.onNewsButtonPressed();
});
		}
		
		public void onNewsButtonPressed() {
		
		//	this.startNewsdetailActivity();
		}
	}
	
	
	public static class AdvertisementViewHolder extends RecyclerView.ViewHolder {
		private ImageView adPhotoImageView;
		private TextView adTitleTextView;
		private TextView adDetailTextView;
		private TextView adTimeTextView;
		public AdvertisementViewHolder(View itemView) {
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
