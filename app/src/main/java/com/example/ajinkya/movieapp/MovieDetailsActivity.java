package com.example.ajinkya.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailsActivity extends ActionBarActivity {

    public static String base_url = "http://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            PlaceholderFragment p = new PlaceholderFragment();
            View rootView = inflater.inflate(R.layout.fragment_movie_details_activity, container, false);
            Log.e("MovieDetailsActivity:","Or it could be here, maybe cozof fragme_movie");
            Intent i = getActivity().getIntent();
            ImageSetup imagesetup = i.getExtras().getParcelable(Intent.EXTRA_TEXT);
            String movie_title = imagesetup.title;
            ((TextView)rootView.findViewById(R.id.movie_name)).setText(movie_title);

            p.setImage( rootView ,imagesetup.image);
            p.setDescription(rootView,imagesetup.desc);
            p.setReleaseYear(rootView,imagesetup.release_date);
            p.setRatings(rootView,imagesetup.popularity);
            p.setLanguage(rootView, imagesetup.lang);
            return rootView;
        }

        private void setLanguage(View rootView, String lang) {
            ((TextView)rootView.findViewById(R.id.language)).setText(lang);
        }

        private void setReleaseYear(View rootview, String release_date) {
            String[] date = release_date.split("-");
            ((TextView)rootview.findViewById(R.id.release_year)).setText(date[0]);
        }

        public void setImage(View rootview, String url){
            ImageView imageview = (ImageView)rootview.findViewById(R.id.imageView2);
            Picasso.with(getActivity()).load(base_url+url).into(imageview);
        }

        public void setDescription(View rootview, String text_desc){

            ((TextView)rootview.findViewById(R.id.desc)).setText(text_desc);
            ((TextView) rootview.findViewById(R.id.desc)).setMovementMethod(new ScrollingMovementMethod());
        }

        public void setRatings(View rootview, String ratings){
            ((TextView)rootview.findViewById(R.id.ratings)).setText(ratings);
        }

    }
}
