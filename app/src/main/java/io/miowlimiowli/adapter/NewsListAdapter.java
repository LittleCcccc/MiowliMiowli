/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.adapter;

import android.content.Context;
import android.widget.TextView;
import android.widget.Button;
import java.util.*;

import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import io.miowlimiowli.R;
import io.miowlimiowli.manager.DisplayableNews;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;


public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final int CELL_VIEW_HOLDER_VIEW_TYPE = 1;
	public static final int ADVERTISEMENT_VIEW_HOLDER_VIEW_TYPE = 2;

	private Context mContext;
	private OnItemClickListener mOnItemClickListener;
	private List<DisplayableNews> mNews = new ArrayList<DisplayableNews>();

	public DisplayableNews getNews(int position){
		return mNews.get(position);
	}
	public NewsListAdapter(Context context){mContext = context;}

	public void setData(List<DisplayableNews> data){
		mNews = new ArrayList<>(data);
		this.notifyDataSetChanged();
	}



	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.mOnItemClickListener=onItemClickListener;
	}

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
		if(viewHolder instanceof CellViewHolder){
			DisplayableNews news = mNews.get(position);
			final CellViewHolder cell = (CellViewHolder)viewHolder;
			cell.setTitle(news.title);
		}
		// Here you can bind RecyclerView item data.
	}

	@Override
	public int getItemViewType(int position) {
		if(position == mNews.size()+1)
			return 2;
		else return 1;
	}
	
	@Override
	public int getItemCount() {
		return mNews.size();
	}

	/**
	 * 点击Listener
	 */
	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}


	/**
	* 新闻单元格
	 */
	public class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		private ImageView newsPhotoImageView;
		private TextView newsTitleTextView;
		private TextView newsTimeTextView;

		public CellViewHolder(View itemView) {
			super(itemView);
			init();
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			if (mOnItemClickListener != null) {
				mOnItemClickListener.onItemClick(view, this.getLayoutPosition());
			}
		}

		public void setTitle(String s)
		{
			newsTitleTextView.setText(s);
		}

		public void setTime(String s)
		{
			newsTimeTextView.setText(s);
		}

		public void setPhoto(){

		}
		
		public void init() {
		
			// Configure bg-news-image component
			newsPhotoImageView = this.itemView.findViewById(R.id.news_photo_image_view);
			
			// Configure Title component
			newsTitleTextView = this.itemView.findViewById(R.id.news_title_text_view);
			
			// Configure Time component
			newsTimeTextView = this.itemView.findViewById(R.id.news_time_text_view);
			

		}
		

	}

	public class AdvertisementViewHolder extends RecyclerView.ViewHolder {
		private ImageView adPhotoImageView;
		private TextView adTitleTextView;
		private TextView adDetailTextView;
		private TextView adTimeTextView;
		public AdvertisementViewHolder(View itemView) {
			super(itemView);
			init();
		}
		public void setTitle(String s)
		{

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
