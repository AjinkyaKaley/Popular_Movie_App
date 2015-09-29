package com.example.ajinkya.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by Ajinkya on 7/12/2015.
 * UPDATE: debugged and fixed oncreate view
 * 
 */

public class ImageFragment extends Fragment {
    public static ArrayAdapter<ImageSetup> adapter = null;
    FetchMovieData fetch = new FetchMovieData();
    ImageSetup[] data_on_start;
    public ImageFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.fetchmoviedatamenu, menu);
    }

    /**
     *  This functions starts the doinBackground func
     *  it also passes the parameter fetched from user
     *  using shared prefrences
     * */
    @Override
    public void onStart(){
        super.onStart();
        FetchMovieData f = new FetchMovieData();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String vote_pref = pref.getString(getString(R.string.list_pref_key),getString(R.string.default_value));

        f.execute(vote_pref);
       // update_the_data(vote_pref);
    }

    
    private void update_the_data(String s) {
        FetchMovieData f = new FetchMovieData();
        f.execute(s);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        adapter = new ImageAdapter(getActivity(), data_on_start);

        GridView grid_view = (GridView)rootView.findViewById(R.id.gridView);
        grid_view.setAdapter(adapter);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageSetup i = adapter.getItem(position);
                String text = i.title;
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT,i);
                Log.e("ImageFragment:", "error coul be here");
                startActivity(intent);

            }
        });


        return rootView;
    }
}
