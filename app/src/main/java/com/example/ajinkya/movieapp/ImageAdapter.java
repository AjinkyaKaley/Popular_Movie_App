package com.example.ajinkya.movieapp;

/**
 * Created by Ajinkya on 7/12/2015.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends ArrayAdapter<ImageSetup> {
    private Context mContext;
    private String base_url = "http://image.tmdb.org/t/p/w185/";

    public static ImageSetup[] mThumbIds;

    // Constructor
    public ImageAdapter(Context c, ImageSetup[] list){
        super(c,0, list);
        mContext = c;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageSetup image_url = getItem(position);

        SquareImageView imageView;
        if (convertView == null) {
            imageView = new SquareImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (SquareImageView) convertView;
        }
        String url = image_url.image;
        //Log.v("IMAGE ADAPTER CLASS: ",url);
        Picasso.with(mContext).load(base_url+url).into(imageView);

        return imageView;
    }

}