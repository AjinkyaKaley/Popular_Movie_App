package com.example.ajinkya.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ajinkya on 7/25/2015.
 * This class create the skeleton
 * for the image and imageurls
 * it implements parcelabe to inorder to send 
 * this object across activities using intents
 * 
 */
public class ImageSetup implements Parcelable {
    String image, desc, title, popularity, release_date,lang;

    public ImageSetup(String image, String desc, String title, String popularity, String release_date, String lang){
        this.image = image;
        this.desc = desc;
        this.title = title;
        this.popularity = popularity;
        this.release_date = release_date;
        this.lang = lang;
    }

    /**
     * used by parceable
     * @param p
     */
    private ImageSetup(Parcel p){
        image = p.readString();
        desc = p.readString();
        title = p.readString();
        popularity = p.readString();
        release_date = p.readString();
        lang = p.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(desc);
        dest.writeString(title);
        dest.writeString(popularity);
        dest.writeString(release_date);
        dest.writeString(lang);
    }

    public final static Parcelable.Creator<ImageSetup> CREATOR = new Parcelable.Creator<ImageSetup>(){

        @Override
        public ImageSetup createFromParcel(Parcel source) {
            return new ImageSetup(source);
        }

        @Override
        public ImageSetup[] newArray(int size) {
            return new ImageSetup[size];
        }
    };


}
