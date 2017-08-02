package com.jeremyfiggins.onedroptest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchTermFragment extends Fragment {
    public SearchTermFragment() {
        super();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.search_term_fragment, container, false);
        final EditText searchField = view.findViewById(R.id.search_field);
        searchField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchField.setCursorVisible(true);
            }
        });
        final Button submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).showTweetsMatching(searchField.getText().toString());
            }
        });
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                submitButton.setEnabled(!TextUtils.isEmpty(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //do nothing
            }
        });
        return view;
    }
}
