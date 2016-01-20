package com.example.kaixu.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SongAdapter extends BaseAdapter {

    private HashMap<Integer, Bitmap> bitmapCache = new HashMap<Integer, Bitmap>();

    private ArrayList<SongItem> songs;

    private Context cxt;

    public SongAdapter(Context context, ArrayList<SongItem> songs) {
        cxt = context;
        this.songs = songs;
    }

    public void setSongs (ArrayList<SongItem> songs) {
        this.songs = songs;
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SongItem song = songs.get(position);
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(cxt).inflate(R.layout.song_item, parent, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            holder.textView = (TextView) convertView.findViewById(R.id.textView1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(song.toString());
        holder.imageView.setTag(position);
        if (!song.getArtworkURL().equals("null")) {
            if(bitmapCache.get(position) == null) {
                new ImageDownloader(holder.imageView).execute(song.getArtworkURL());
            }
            else {
                holder.imageView.setImageBitmap(bitmapCache.get(position));
            }
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher);
        }
        // load image
//        Picasso.with(cxt).load(song.artwork_url).fit().centerCrop().into(imageView);

        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        private int mPosition;
        private ImageView mImageView;

        public ImageDownloader (ImageView imageView) {
            this.mImageView = imageView;
            mPosition = (Integer)imageView.getTag();

        }

        @Override
        protected Bitmap doInBackground(String... param) {
            return downloadBitmap(param[0]);
        }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onPostExecute(Bitmap result) {

            int pos = (Integer)mImageView.getTag();
            if(mPosition == pos) {
                bitmapCache.put(mPosition, result);
                mImageView.setImageBitmap(result);
            }

        }

        private Bitmap downloadBitmap(String url) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);
            try {
                HttpResponse response = client.execute(getRequest);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    return null;
                }

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    try {
                        inputStream = entity.getContent();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        return bitmap;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entity.consumeContent();
                    }
                }
            } catch (Exception e) {

            }

            return null;
        }
    }
}