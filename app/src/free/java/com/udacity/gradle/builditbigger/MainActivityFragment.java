package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.JokeShowActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivityFragment extends Fragment implements AsyncJokeDownloader.IDownloadJoke{
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    private boolean isLoading;
    private AsyncJokeDownloader asyncJokeDownloader;
    private ProgressDialog progress;
    AdView mAdView;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_main, container, false);


        Button button = (Button) root.findViewById(R.id.joke_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showJoke();
            }
        });

        asyncJokeDownloader = new AsyncJokeDownloader(this);

        mAdView = (AdView) root.findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if(progress == null)
        {
            progress = new ProgressDialog(getContext());
            progress.setTitle(getString(R.string.loading));
            progress.setMessage(getString(R.string.wait));
        }

        return root;
    }
    private void showJoke(){
        if (!progress.isShowing())
        {
            progress.show();
        }
        isLoading = true;
        if(isNetworkConnected())
        {
            mInterstitialAd = new InterstitialAd(getContext());
            mInterstitialAd.setAdUnitId(getContext().getString(R.string.test_interstitial_ad_unit_id));
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener()
            {
                @Override
                public void onAdLoaded()
                {
                    if (mInterstitialAd.isLoaded())
                    {
                        mInterstitialAd.show();
                    }
                }

                @Override
                public void onAdClosed()
                {
                    if (asyncJokeDownloader != null)
                    {
                        asyncJokeDownloader.download();
                    }
                }
            });
        }else
        {
            progress.cancel();
            isLoading = false;
            Toast.makeText(getContext(),getString(R.string.connection_error),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void downloadCompleted(String s)
    {
        progress.cancel();
        isLoading = false;
        if(!s.equals(""))
        {
            Intent intent = new Intent(getContext(), JokeShowActivity.class);
            intent.putExtra(getString(R.string.joke_key), s);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getContext().startActivity(intent);
        }else{
            Toast.makeText(getContext(),getString(R.string.joke_error),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        progress.cancel();
        outState.putBoolean(getString(R.string.isLoading_key), isLoading);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
        {
            if (savedInstanceState.containsKey(getString(R.string.isLoading_key)))
            {
                isLoading = savedInstanceState.getBoolean(getString(R.string.isLoading_key), true);
            }
            if (isLoading)
            {
                progress.show();
            }
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }
}