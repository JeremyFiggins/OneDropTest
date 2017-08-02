package com.jeremyfiggins.onedroptest;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

@SuppressWarnings("FieldCanBeLocal")
public class TwitterSearchAdapter extends BaseAdapter {
    private final int NUM_EXTRA_TWEETS_TO_RETRIEVE = 10;
    private final long SLEEP_BETWEEN_QUERIES_MS = 2000;
    private final List<TweetWrapper> cachedTweets;
    private int maxIndex = 10;
    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;
    private SearchService searchService;
    private String searchTerms;

    TwitterSearchAdapter(LayoutInflater layoutInflater, String searchTerms) {
        this.layoutInflater = layoutInflater;
        this.searchTerms = searchTerms;
        cachedTweets = new ArrayList<>();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                keepCachedListPopulated();
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    public int getCount() {
        return cachedTweets.size();
    }

    @Override
    public Object getItem(int position) {
        return cachedTweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView body;
        ImageView userProfileImage;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TweetWrapper tweetWrapper = (TweetWrapper) getItem(position);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.listview_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.body = view.findViewById(R.id.body);
            viewHolder.userProfileImage = view.findViewById(R.id.userProfileImage);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.body.setText(tweetWrapper.getText());
        viewHolder.userProfileImage.setImageBitmap(tweetWrapper.getUserProfileImage());
        return view;
    }

    void loadUpTo(int index) {
        index += NUM_EXTRA_TWEETS_TO_RETRIEVE;
        maxIndex = index > maxIndex ? index : maxIndex;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private void keepCachedListPopulated() {
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                if (maxIndex > cachedTweets.size()) {
                    getListItem();
                }
                try {
                    Thread.sleep(SLEEP_BETWEEN_QUERIES_MS);
                } catch (InterruptedException e) {
                    //do nothing
                }
            }
        } catch (Exception exception) {
            //In case of an exception retrieving the tweets, we'll
            //assume that we have an unrecoverable error and we should
            //not continue to query the server.
            exception.printStackTrace();
        }
    }

    private void getListItem() throws Exception {
        TwitterCore twitterCore = TwitterCore.getInstance();
        TwitterApiClient twitterApiClient = twitterCore.getGuestApiClient();
        searchService = twitterApiClient.getSearchService();
        Call<Search> tweets = searchService.tweets(searchTerms, null, null, null, null, null, null, null, null, true);
        tweets.enqueue(new Callback<Search>() {
            @Override
            public void success(Result<Search> searchResult) {
                parseResults(searchResult);
            }

            @Override
            public void failure(TwitterException error) {
                throw error;
            }
        });
    }

    private void parseResults(final Result<Search> searchResult) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Tweet> tweets = searchResult.data.tweets;
                for (Tweet tweet : tweets) {
                    try {
                        final TweetWrapper tweetWrapper = new TweetWrapper();
                        tweetWrapper.load(tweet);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                cachedTweets.add(tweetWrapper);
                                notifyDataSetChanged();
                            }
                        });
                    } catch (Exception exception) {
                        //do nothing
                    }
                }
            }
        };
        new Thread(runnable).start();
    }
}
