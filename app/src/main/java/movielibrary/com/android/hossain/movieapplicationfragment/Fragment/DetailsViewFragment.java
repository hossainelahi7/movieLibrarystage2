package movielibrary.com.android.hossain.movieapplicationfragment.Fragment;


import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;
import movielibrary.com.android.hossain.movieapplicationfragment.MainActivity;
import movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil.MovieDBAPI;
import movielibrary.com.android.hossain.movieapplicationfragment.R;
import movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView.MovieReviewRecylerAdapter;
import movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView.MovieVideoRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsViewFragment extends Fragment {

    private Context mContext;
    private LifecycleOwner owner;
    private int MovieID;
    private TextView titleView;
    private ImageView imageView;
    private TextView descriptionView;
    private TextView popularityView;
    private TextView reviewView;
    private TextView releaseDateView;
    private RecyclerView mReviewRecyclerView;
    private RecyclerView mVideoRecyclerView;
    private MovieReviewRecylerAdapter reviewRecyler;
    private MovieVideoRecyclerAdapter videoRecycler;
    private Button saveButton;
    private MovieInfo mMovieData ;
    private List<ReviewData> mReviewData;
    private List<VideoData> mVideoData;


    public DetailsViewFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("DETAILS", String.valueOf(getArguments().getInt("MOVIE_ID")));
        mContext = getContext();
        owner = this;
        MovieID = getArguments().getInt("MOVIE_ID");
        titleView = this.getActivity().findViewById(R.id.movie_title);
        imageView = this.getActivity().findViewById(R.id.entry_image);
        descriptionView = this.getActivity().findViewById(R.id.entry_description);
        popularityView = this.getActivity().findViewById(R.id.entry_popularity);
        reviewView = this.getActivity().findViewById(R.id.entry_user_rating);
        releaseDateView = this.getActivity().findViewById(R.id.entry_release_date);
        mReviewRecyclerView = this.getActivity().findViewById(R.id.review_list_recycle_view);
        mVideoRecyclerView = this.getActivity().findViewById(R.id.video_list_recycle_view);
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager videoLayoutManager = new LinearLayoutManager(mContext);
        reviewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        videoLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mReviewRecyclerView.setLayoutManager(reviewLayoutManager);
        mVideoRecyclerView.setLayoutManager(videoLayoutManager);
        reviewRecyler = new MovieReviewRecylerAdapter();
        videoRecycler = new MovieVideoRecyclerAdapter();
        mReviewRecyclerView.setAdapter(reviewRecyler);
        mVideoRecyclerView.setAdapter(videoRecycler);
        saveButton  = this.getActivity().findViewById(R.id.save_as_favorite_button);
        new LoadMovieInfo().execute(MovieID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MarkASFavorite().execute();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDetach();
    }

    private class MarkASFavorite extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            if (mMovieData != null){
                mMovieData.user_choice = (mMovieData.user_choice == 0)? 1 : 0;
                MainActivity.movieDB.insert(mMovieData);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            saveButton.setText((mMovieData.user_choice == 0)? getString(R.string.mark_as_fav) : getString(R.string.remove_from_fav));
        }
    }


    private class LoadMovieInfo extends AsyncTask<Integer, Void, MovieInfo> {

        @Override
        protected MovieInfo doInBackground(Integer... integers) {
            if ( integers.length>0){
                mMovieData = MainActivity.movieDB.getMovieInfo(integers[0]);
                mVideoData = MainActivity.movieDB.getVideoofMovie(mMovieData.movie_id);
                mReviewData = MainActivity.movieDB.getReviewofMovie(mMovieData.movie_id);
            }
            return mMovieData;
        }

        @Override
        protected void onPostExecute(MovieInfo movieInfo) {
            super.onPostExecute(movieInfo);
            if(movieInfo != null)
                Picasso.with(mContext).
                        load(MovieDBAPI.getApiImageUrl(movieInfo.posterpath)).
                        placeholder(R.mipmap.loading_image_place_holder).
                        error(R.mipmap.error_loading_image).
                        into(imageView);
            titleView.setText(movieInfo.movie_title);
            descriptionView.setText(movieInfo.overview);
            popularityView.setText(movieInfo.popularity.toString());
            reviewView.setText(movieInfo.avg_vote.toString());
            releaseDateView.setText(movieInfo.release_data.toString());
            saveButton.setText((movieInfo.user_choice == 0)? getString(R.string.mark_as_fav) : getString(R.string.remove_from_fav));
            reviewRecyler.setData(mReviewData);
            videoRecycler.setData(mVideoData);
        }
    }


}
