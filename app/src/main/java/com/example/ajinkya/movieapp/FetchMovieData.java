package com.example.ajinkya.movieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class does the background work
 * of fetching the data from the themoviedb.org
 * 
 * @author ajinkya kale
 * */
 
public class FetchMovieData extends AsyncTask<String, Void, ImageSetup[]> {
    ImageSetup [] temp;
    String json_movie_data = null;
    private final String LOG_TAG = FetchMovieData.class.getSimpleName();
    
    // Default const
    public FetchMovieData() {
    }


    /**
     *  This method parses the Json object
     *  and extracts the URL of the poster
     * */
     
    public ImageSetup[] extract_poster_url(String json_data) throws JSONException{
        
        // Tags
        
        final String result_array = "results";      
        final String movie_Poster_tag = "poster_path";
        final String movie_desc = "overview";
        final String title = "original_title";
        final String popularity = "vote_average";
        final String release_date = "release_date";
        final String language = "original_language";


        ImageSetup [] result_poster_path;
        JSONObject movie_data = new JSONObject(json_data);
        JSONArray movies_info = movie_data.getJSONArray(result_array);
        result_poster_path = new ImageSetup[movies_info.length()];

        for(int i =0;i<movies_info.length();i++){
            JSONObject temp_movie_object = movies_info.getJSONObject(i);
            String poster_address = temp_movie_object.getString(movie_Poster_tag);
            String desc = temp_movie_object.getString(movie_desc);
            String og_title = temp_movie_object.getString(title);
            String popular = temp_movie_object.getString(popularity);
            String release = temp_movie_object.getString(release_date);
            String lang = temp_movie_object.getString(language);
            result_poster_path[i] = new ImageSetup(poster_address,desc, og_title,popular, release, lang);
        }
        return result_poster_path;
    }

    /**
     * 
     * This method populates the data on display activity
     * basically it binds the aysn thread with main thread
     * */
     
    @Override
    protected void onPostExecute(ImageSetup[] final_data){
        if(final_data!=null){
            ImageFragment.adapter.clear();
            for(ImageSetup i : final_data){
                ImageFragment.adapter.add(i);
            }
        }
    }
    
    /**
     *  This is the method from aysntask class
     *  this method performs api calls in background
     *  and fetches the data
     **/
    
    @Override
    protected ImageSetup[] doInBackground(String... params) {

        HttpURLConnection url_connection = null;
        BufferedReader buffer_reader = null;
        
        // Base URL
        final String Base_URL = "http://api.themoviedb.org/3/discover/movie";
        final String private_key = "api_key";
        final String API_KEY = "d4b134d2a30791b4f50cc14303096628"; // Api Key
        final String sort = "sort_by";

        Uri built_uri = Uri.parse(Base_URL).buildUpon()
                .appendQueryParameter(sort, params[0])
                .appendQueryParameter(private_key,API_KEY).build();

        // Building the URL
        
        try {
            URL url = new URL(built_uri.toString());
            url_connection = (HttpURLConnection) url.openConnection();
            url_connection.setRequestMethod("GET");
            url_connection.connect();

            // Read the input stream into a String
            Log.e(LOG_TAG,"Hello there");
            InputStream inputStream = url_connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            buffer_reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = buffer_reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            
            json_movie_data = buffer.toString();
            Log.v(LOG_TAG, "Movie data : " + json_movie_data); // Debug purpose

        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);

            json_movie_data = null;
        }
        
        // If connection fails
        finally{
            if (url_connection != null) {
                url_connection.disconnect();
            }
            if (buffer_reader != null) {
                try {
                    buffer_reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
            
            // This code returns the poster url 
            try{
                Log.v(LOG_TAG,json_movie_data);
                temp = extract_poster_url(json_movie_data);
                return temp;
            }
            catch (Exception e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }
            return null;
        }

    }

}
