package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.UiThreadTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by davi on 10/3/15.
 */
public class AsyncTaskTester extends AndroidTestCase implements AsyncJokeDownloader.IDownloadJoke{
    AsyncJokeDownloader asyncJokeDownloader;
    CountDownLatch signal;
    String output = "";

    @UiThreadTest
    public void testDownload() throws InterruptedException{
        asyncJokeDownloader = new AsyncJokeDownloader(this);
        signal = new CountDownLatch(1);
        asyncJokeDownloader.download();
        signal.await(30, TimeUnit.SECONDS);
        assertTrue(!output.equals(""));
    }

    @Override
    public void downloadCompleted(String s)
    {
        output = s;
        signal.countDown();
    }
}
