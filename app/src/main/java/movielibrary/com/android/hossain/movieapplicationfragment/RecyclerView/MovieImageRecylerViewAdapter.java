package movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.navigation.Navigation;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil.MovieDBAPI;
import movielibrary.com.android.hossain.movieapplicationfragment.R;

public class MovieImageRecylerViewAdapter extends RecyclerView.Adapter<MovieImageRecylerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<MovieInfo> mMovieData;
    private int size = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.movie_image_recycle_adapter_view, parent, false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = ((displayMetrics.widthPixels)/3);
        int height = width * 3/ 2;
        view.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieImageRecylerViewAdapter.ViewHolder holder, int position) {
        Picasso.with(mContext).
                load(MovieDBAPI.getApiImageUrl(mMovieData.get(position).posterpath))
                .placeholder(R.mipmap.loading_image_place_holder)
                .error(R.mipmap.error_loading_image)
                .into(holder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) size= 0;
        else size = mMovieData.size();
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView mMovieImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = itemView.findViewById(R.id.movie_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
//            SharedPreferences pref = mContext.getSharedPreferences(mContext.getResources().getString(R.string.app_name),Context.MODE_PRIVATE);
//            pref.edit().putInt("MOVIE_ID", mMovieData.get(position).movie_id);
//            Intent detailsIntent = new Intent(mContext, DetailsActivity.class);
//            detailsIntent.putExtra(DetailsActivity.ENTRY_TYPE, mMovieData[position].type);
//            detailsIntent.putExtra(DetailsActivity.ENTRY_ID, mMovieData[position].id);
//            mContext.startActivity(detailsIntent);
            Bundle bundle = new Bundle();

            bundle.putInt("MOVIE_ID", mMovieData.get(position).movie_id);
            Navigation.findNavController(view).navigate(R.id.movie_detailed_view, bundle);
        }
    }

    public void setMoviedata(List<MovieInfo> movieData){
        mMovieData = movieData;
        size = mMovieData.size();
        notifyDataSetChanged();
    }
}
