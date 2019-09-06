/**
 *  Created by LittleRedCap.
 */

package io.miowlimiowli.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.miowlimiowli.R;
import io.miowlimiowli.manager.DisplayableNews;
import io.miowlimiowli.manager.Manager;


public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final int CELL_VIEW_HOLDER_VIEW_TYPE = 1;
	public static final int ADVERTISEMENT_VIEW_HOLDER_VIEW_TYPE = 2;

	private Context mContext;
	private NewsClickListener mNewsClickListener;
	private List<DisplayableNews> mNews = new ArrayList<DisplayableNews>();

	public DisplayableNews getNews(int position){
		return mNews.get(position);
	}
	public NewsListAdapter(Context context){mContext = context;}

	public void setData(List<DisplayableNews> data){
		mNews = new ArrayList<>(data);
		this.notifyDataSetChanged();
	}

	public void appendData(List<DisplayableNews> data){
		int pos = mNews.size();
		mNews.addAll(data);
		this.notifyItemRangeChanged(pos, getItemCount());
	}




	public void setNewsClickListener(NewsClickListener newsClickListener){
		this.mNewsClickListener=newsClickListener;
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
		if(viewHolder instanceof CellViewHolder) {
			DisplayableNews news = mNews.get(position);
			final CellViewHolder cell = (CellViewHolder) viewHolder;
			cell.setTitle(news.title);
			String url="";
			Date date = news.publish_time;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String time = formatter.format(date);
			cell.setTime(news.publisher_name + " " + time + " " + news.readcount + "阅读" + " " + news.likecount + "喜爱");
			if(Manager.getInstance().nopic || news.image_urls.isEmpty())
				cell.newsPhotoImageView.setVisibility(View.GONE);
			else{
				url = news.image_urls.get(0);
				Glide.with(viewHolder.itemView.getContext())
						.load(url)
						.apply(new RequestOptions().dontTransform().placeholder(R.drawable.placeholder))
						.into(((CellViewHolder) viewHolder).newsPhotoImageView);
			}
			if (news.isread)
				cell.newsTitleTextView.setTextColor(mContext.getResources().getColor(R.color.black_light));
	}
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
	public interface NewsClickListener {
		void onNewsClick(View view, int position);
	}


	/**
	* 新闻单元格
	 */
	public class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		ImageView newsPhotoImageView;
		TextView newsTitleTextView;
		TextView newsTimeTextView;


		public CellViewHolder(View itemView) {
			super(itemView);
			init();
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			newsTitleTextView.setTextColor(mContext.getResources().getColor(R.color.black_light));
			if (mNewsClickListener != null) {
				mNewsClickListener.onNewsClick(view, this.getLayoutPosition());
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
		
		public void init() {
			newsPhotoImageView = this.itemView.findViewById(R.id.news_photo_image_view);
			newsTitleTextView = this.itemView.findViewById(R.id.news_title_text_view);
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
