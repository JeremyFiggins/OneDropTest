package com.jeremyfiggins.onedroptest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

public class LimitlessListView extends ListView implements AbsListView.OnScrollListener {
    private TwitterSearchAdapter twitterSearchAdapter;

    public LimitlessListView(Context context) {
        super(context);
        setOnScrollListener(this);
    }

    public LimitlessListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
    }

    public LimitlessListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnScrollListener(this);
    }

    void setAdapter(TwitterSearchAdapter twitterSearchAdapter) {
        this.twitterSearchAdapter = twitterSearchAdapter;
        super.setAdapter(twitterSearchAdapter);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int state) {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = visibleItemCount + firstVisibleItem;
        if (twitterSearchAdapter != null && lastVisibleItem >= totalItemCount) {
            twitterSearchAdapter.loadUpTo(lastVisibleItem);
        }
    }
}
