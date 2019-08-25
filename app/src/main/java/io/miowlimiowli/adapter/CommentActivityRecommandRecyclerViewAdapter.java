/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.adapter;

import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;
import android.text.Spannable;
import io.miowlimiowli.R;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.*;
import android.view.LayoutInflater;
import android.text.SpannableString;
import android.widget.ImageView;
import io.supernova.uitoolkit.text.FontSpan;
import android.support.constraint.ConstraintLayout;
import android.text.style.RelativeSizeSpan;


public class CommentActivityRecommandRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final int NEWS_VIEW_HOLDER_VIEW_TYPE = 1;
	public static final int ADVERTISEMENT_TWO_VIEW_HOLDER_VIEW_TYPE = 2;
	
	public static final List<Integer> MOCK_DATA = Arrays.asList(NEWS_VIEW_HOLDER_VIEW_TYPE, ADVERTISEMENT_TWO_VIEW_HOLDER_VIEW_TYPE, NEWS_VIEW_HOLDER_VIEW_TYPE, ADVERTISEMENT_TWO_VIEW_HOLDER_VIEW_TYPE, NEWS_VIEW_HOLDER_VIEW_TYPE, ADVERTISEMENT_TWO_VIEW_HOLDER_VIEW_TYPE);
	
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	
		switch (viewType) {
			case NEWS_VIEW_HOLDER_VIEW_TYPE: 
				return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_view_holder, parent, false));
			case ADVERTISEMENT_TWO_VIEW_HOLDER_VIEW_TYPE: 
				return new AdvertisementTwoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.advertisement_two_view_holder, parent, false));
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
	
	
	public static class NewsViewHolder extends RecyclerView.ViewHolder {
		private TextView timeTextView;
		public NewsViewHolder(View itemView) {
			super(itemView);
			init();
		}
		
		public void init() {
		
			// Configure Time component
			timeTextView = this.itemView.findViewById(R.id.time_text_view);
			SpannableString timeTextViewText = new SpannableString(this.itemView.getContext().getString(R.string.news_view_holder_time_text_view_text));
			timeTextViewText.setSpan(new FontSpan(ResourcesCompat.getFont(this.itemView.getContext(), R.font.font_sfnstext)), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			timeTextViewText.setSpan(new FontSpan(ResourcesCompat.getFont(this.itemView.getContext(), R.font.font_sfnstext)), 9, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			timeTextView.setText(timeTextViewText);
		}
	}
	
	
	public static class AdvertisementTwoViewHolder extends RecyclerView.ViewHolder {
		private TextView pinktextTextView;
		public AdvertisementTwoViewHolder(View itemView) {
			super(itemView);
			init();
		}
		
		public void init() {
		
			// Configure Pinktext component
			pinktextTextView = this.itemView.findViewById(R.id.pinktext_text_view);
		}
	}
}
