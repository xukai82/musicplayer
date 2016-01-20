package com.example.kaixu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SongListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, OnTaskCompleted {

    private ListView listView;
    private SongAdapter mAdapter;
    private ArrayList<SongItem> songs = new ArrayList<SongItem>();

    public static final String ARG_SONG_URL = "songLink";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        mAdapter = new SongAdapter(this, songs);
        listView.setAdapter(mAdapter);

        new SongAsyncTask(this).execute("https://api.soundcloud.com/tracks.json?client_id=1866493892c9aa26892c34627dbcdcf3");
    }


    public void onTaskCompleted (ArrayList<SongItem> songs) {
        this.songs = songs;
        mAdapter.setSongs(songs);
        mAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView parent, View view, int pos,long id) {
        Intent detailIntent = new Intent(this, SongDetailActivity.class);
        detailIntent.putExtra(ARG_SONG_URL, songs.get(pos).getPermalinkURL());
        startActivity(detailIntent);
    }

}
