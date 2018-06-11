package movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;
import movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil.webAPI;
import movielibrary.com.android.hossain.movieapplicationfragment.R;

public class MovieReviewRecylerAdapter extends RecyclerView.Adapter<MovieReviewRecylerAdapter.ViewHolder> {

    Context mContext;
    List<ReviewData> mReview;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycle_review_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameView.setText((position+1)+". "+mReview.get(position).review_author);
        holder.reviewView.setText(mReview.get(position).review_content);
        holder.urlView.setText(mReview.get(position).review_urls);
        holder.urlView.setTextColor(Color.BLUE);
    }

    @Override
    public int getItemCount() {
        if (mReview != null)
            return mReview.size();
        return 0;
    }

    public void setData(List<ReviewData> reviewListData){
        mReview = reviewListData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameView, reviewView, urlView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.movie_reviewer_name);
            reviewView = itemView.findViewById(R.id.movie_review);
            urlView = itemView.findViewById(R.id.movie_reviewer_url);
            urlView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    webAPI.openWebPage(mReview.get(position).review_urls, mContext);
                }
            });
        }
    }
}
