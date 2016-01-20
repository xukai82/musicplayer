package com.example.kaixu.myapplication;

public class SongItem {
    private int id;
    private String author;
    private String title;
    private String permalink_url;
    private String artwork_url;

    public SongItem(int id, String author, String title, String permalink_url, String artwork_url) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.permalink_url = permalink_url;
        this.artwork_url = artwork_url;
    }

    public String getPermalinkURL () {
        return this.permalink_url;
    }

    public String getArtworkURL () {
        return this.artwork_url;
    }

    @Override
    public String toString() {
        return author + "\n" + title;
    }
}
