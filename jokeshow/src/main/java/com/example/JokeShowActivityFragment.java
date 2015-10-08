package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class JokeShowActivityFragment extends Fragment
{

    public JokeShowActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_joke_show, container, false);
        Intent intent = getActivity().getIntent();
        String joke = intent.getStringExtra(getString(R.string.joke_key));
        TextView textView = (TextView)rootView.findViewById(R.id.joke_show_text);
        textView.setText(joke);
        return rootView;
    }
}
