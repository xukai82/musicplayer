package com.example.kaixu.myapplication;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SongAsyncTask extends AsyncTask<String, Void, ArrayList<SongItem>> {

    private OnTaskCompleted tc;

    public SongAsyncTask (OnTaskCompleted tc) {
        this.tc = tc;
    }

    @Override
    protected ArrayList<SongItem> doInBackground(String... param) {
        ArrayList<SongItem> songs = new ArrayList<SongItem>();

        HttpClient client = new DefaultHttpClient();
        HttpGet getReq = new HttpGet(param[0]);
        try {
            HttpResponse response = client.execute(getReq);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity);

            JSONArray jsonArr = new JSONArray(data);
            for(int i = 0; i < jsonArr.length(); i++){
                JSONObject track = jsonArr.getJSONObject(i);
                String title = track.getString("title");
                String permalink_url = track.getString("permalink_url");
                String artwork_url = track.getString("artwork_url");

                JSONObject userObj = track.getJSONObject("user");
                String username = userObj.getString("username");

                SongItem song = new SongItem(i, title, username, permalink_url, artwork_url);
                songs.add(song);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songs;
    }

    @Override
    protected void onPostExecute(ArrayList<SongItem> songs) {
        this.tc.onTaskCompleted(songs);
    }


}