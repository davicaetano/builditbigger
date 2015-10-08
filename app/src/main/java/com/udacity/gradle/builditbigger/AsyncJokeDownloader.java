package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.davi.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class AsyncJokeDownloader{

    private IDownloadJoke downloadListener;

    public AsyncJokeDownloader(IDownloadJoke downloadListener){
        this.downloadListener = downloadListener;
    }

    public void download(){
        new EndpointsAsyncTask().execute();
    }

    public interface IDownloadJoke{
        void downloadCompleted(String s);
    }

    class EndpointsAsyncTask extends AsyncTask<Void, Void, String>{
        private MyApi myApiService = null;

        @Override
        protected String doInBackground(Void... params){
            if (myApiService == null){
                // Only do this once

                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        //.setRootUrl("https://backendserver.appspot.com/_ah/api/")
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                        .setRootUrl("http://localhost:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer()
                        {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException
                            {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            try{
                return myApiService.joke().execute().getData();
            } catch (IOException e){

                //Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                return "";
                //return e.getMessage();
            }
        }
        @Override
        protected void onPostExecute(String result){
            downloadListener.downloadCompleted(result);
        }
    }
}
