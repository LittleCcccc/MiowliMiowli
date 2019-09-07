package io.miowlimiowli.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.miowlimiowli.R;
import io.miowlimiowli.manager.DisplayableComment;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DisplayableComment> mComment = new ArrayList<DisplayableComment>();
    private CommentAdapter.CommentClickListener mCommentClickListener;
    private Context mContext;
    public DisplayableComment getComment(int position){
        return mComment.get(position);
    }

    public CommentAdapter(Context context){mContext = context;}

    public void setData(List<DisplayableComment> data){
        mComment = new ArrayList<>(data);
        this.notifyDataSetChanged();
    }
    public void appendData(List<DisplayableComment> data){
        int pos = mComment.size();
        mComment.addAll(data);
        this.notifyItemRangeChanged(pos, getItemCount());
    }


    public void setCommentClickListener(CommentClickListener commentClickListener){
        this.mCommentClickListener=commentClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        return new CommentAdapter.CellViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        DisplayableComment comment = mComment.get(position);
        final CommentAdapter.CellViewHolder cell = (CommentAdapter.CellViewHolder) viewHolder;
        cell.setTitle(comment.user.nickname);
        String url="";
        Date date = comment.publish_date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String time = formatter.format(date);
        cell.setTime(time);
        Drawable avt = comment.user.avator;
        cell.setImage(avt);
        cell.setContent(comment.content);
        
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mComment.size()+1)
            return 2;
        else return 1;
    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }

    /**
     * 点击Listener
     */
    public interface CommentClickListener {
        void onCommentClick(View view, int position);
    }


    /**
     * 评论单元格
     */
    public class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView comment_avatar;
        TextView comment_name_text_view;
        TextView comment_content_text_view;
        TextView comment_time_text_view;


        public CellViewHolder(View itemView) {
            super(itemView);
            init();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mCommentClickListener != null) {
                mCommentClickListener.onCommentClick(view, this.getLayoutPosition());
            }
        }

        public void setTitle(String s)
        {
            comment_name_text_view.setText(s);
        }

        public void setTime(String s)
        {
            comment_time_text_view.setText(s);
        }

        public void setContent(String s)
        {
            comment_content_text_view.setText(s);
        }

        public void setImage(Drawable avt)
        {
            comment_avatar.setImageDrawable(avt);
        }

        public void init() {
            comment_avatar = this.itemView.findViewById(R.id.comment_avatar);
            comment_name_text_view = this.itemView.findViewById(R.id.comment_name_text_view);
            comment_content_text_view = this.itemView.findViewById(R.id.comment_content_text_view);
            comment_time_text_view = this.itemView.findViewById(R.id.comment_time_text_view);
        }


    }

}
