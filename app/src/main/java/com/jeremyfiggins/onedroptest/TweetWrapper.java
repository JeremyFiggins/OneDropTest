package com.jeremyfiggins.onedroptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.twitter.sdk.android.core.models.Tweet;

import java.net.URL;
import java.net.URLConnection;

public class TweetWrapper {
    private String text;
    private Bitmap userProfileImage;

    void load(Tweet tweet) throws Exception {
        this.text = tweet.text;
        userProfileImage = loadBitmap(tweet.user.profileImageUrl);
    }

    public String getText() {
        return text;
    }

    Bitmap getUserProfileImage() {
        return userProfileImage;
    }

    Bitmap loadBitmap(String spec) throws Exception {
        URL url = new URL(spec);
        URLConnection connection = url.openConnection();
        return BitmapFactory.decodeStream(connection.getInputStream());
    }
}
