package com.jeremyfiggins.onedroptest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TweetListFragment extends Fragment {
    private TwitterSearchAdapter twitterSearchAdapter;
    private String searchTerm;

    public TweetListFragment() {
        super();
        setRetainInstance(true);
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.tweet_list_fragment, container, false);
        if (twitterSearchAdapter == null) {
            twitterSearchAdapter = new TwitterSearchAdapter(layoutInflater, searchTerm);
        }
        LimitlessListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(twitterSearchAdapter);
        @SuppressWarnings("ConstantConditions")
        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.listview_footer, listView, false);
        listView.addFooterView(footerView);
        return view;
    }
}
