package com.jeremyfiggins.onedroptest;

import android.graphics.Bitmap;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import org.junit.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TweetWrapperTest {
    @Test
    public void testGetText() throws Exception {
        String expected = "This is a tweet";
        TweetWrapper tweetWrapper = Mockito.spy(new TweetWrapper());
        Mockito.doReturn(null).when(tweetWrapper).loadBitmap(Mockito.anyString());
        Tweet tweet = getTweetForTest(expected, "http://www.testimage.com/");

        tweetWrapper.load(tweet);
        String actual = tweetWrapper.getText();

        assertEquals(expected, actual);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testGetTextNull() throws Exception {
        String expected = null;
        TweetWrapper tweetWrapper = Mockito.spy(new TweetWrapper());
        Mockito.doReturn(null).when(tweetWrapper).loadBitmap(Mockito.anyString());
        Tweet tweet = getTweetForTest(expected, "http://www.testimage.com/");

        tweetWrapper.load(tweet);
        String actual = tweetWrapper.getText();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetUserProfileImage() throws Exception {
        String url = "http://www.testimage.com/";
        TweetWrapper tweetWrapper = Mockito.spy(new TweetWrapper());
        Bitmap expected = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
        Mockito.doReturn(expected).when(tweetWrapper).loadBitmap(url);
        Tweet tweet = getTweetForTest(null, url);

        tweetWrapper.load(tweet);
        Bitmap actual = tweetWrapper.getUserProfileImage();

        assertEquals(expected, actual);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testGetUserProfileImageBadURL() throws Exception {
        String url = null;
        TweetWrapper tweetWrapper = new TweetWrapper();
        Tweet tweet = getTweetForTest(null, url);

        try {
            tweetWrapper.load(tweet);
        } catch (MalformedURLException malformedURLException) {
            return;
        }
        fail();
    }

    private Tweet getTweetForTest(String text, String userProfileImageURL) {
        User user = new User(false, null, false, false,
                null, null, null, 0, false,
                0, 0, false, 0L, null, false,
                null, 0, null, null, null,
                null, null, false,
                null, userProfileImageURL, null, null,
                null, null, null, false,
                false, null, false, null, 0,
                null, null, 0, false, null, null);
        return new Tweet(null, null, null, null,
                null, null, false, null, 0L, null,
                null, 0L, null, 0L,
                null, null, null, false, null, 0L,
                null, null, 0, false, null,
                null, text, null, false, user, false,
                null, null, null);
    }

}