package com.jeremyfiggins.onedroptest;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.twitter.sdk.android.core.Twitter;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {
    private final String TWEET_LIST_FRAGMENT_TAG = "TWEET_LIST";
    private final String KEYWORD_FRAGMENT_TAG = "KEYWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchTermFragment searchTermFragment = (SearchTermFragment) fragmentManager.findFragmentByTag(KEYWORD_FRAGMENT_TAG);
        if (searchTermFragment == null) {
            searchTermFragment = new SearchTermFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, searchTermFragment, KEYWORD_FRAGMENT_TAG).commit();
        }

        TweetListFragment tweetListFragment = (TweetListFragment) getSupportFragmentManager().findFragmentByTag(TWEET_LIST_FRAGMENT_TAG);
        if (tweetListFragment != null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, tweetListFragment, TWEET_LIST_FRAGMENT_TAG).commit();
        }
    }

    public void showTweetsMatching(String searchTerm) {
        TweetListFragment tweetListFragment = new TweetListFragment();
        tweetListFragment.setSearchTerm(searchTerm);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tweetListFragment, TWEET_LIST_FRAGMENT_TAG).addToBackStack(null).commit();
    }
}
