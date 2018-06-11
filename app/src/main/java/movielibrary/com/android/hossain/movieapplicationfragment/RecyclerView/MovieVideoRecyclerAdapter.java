package movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;
import movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil.YoutubeAPI;
import movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil.webAPI;
import movielibrary.com.android.hossain.movieapplicationfragment.R;

public class MovieVideoRecyclerAdapter extends RecyclerView.Adapter<MovieVideoRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<VideoData> mVideoListData;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycle_video_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mVideoTextView.setText(mVideoListData.get(position).video_name);
    }

    @Override
    public int getItemCount() {
        if (mVideoListData != null && mVideoListData.size()>0)
            return mVideoListData.size();
        return 0;
    }

    public void setData(List<VideoData> videoListData){
        mVideoListData = videoListData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mVideoTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mVideoTextView = itemView.findViewById(R.id.video_click_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String url = YoutubeAPI.getYoutubeUrl(mVideoListData.get(position).video_key).toString();
                    Log.d("YOUTUBEURL:", url);
                    webAPI.openWebPage(url, mContext);
                }
            });
        }
    }

}
