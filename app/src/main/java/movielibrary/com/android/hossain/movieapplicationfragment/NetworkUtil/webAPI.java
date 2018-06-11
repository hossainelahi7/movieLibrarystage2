package movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class webAPI {

    public static void openWebPage(String url, Context mContext) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }
}
