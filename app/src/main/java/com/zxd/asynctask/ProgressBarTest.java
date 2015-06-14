package com.zxd.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

/**
 * Created by zxd on 2015/6/10.
 */
public class ProgressBarTest extends Activity {

    private ProgressBar mProgressBar;
    private MyAsyncTask mMyAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.progressbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mMyAsyncTask = new MyAsyncTask();
        mMyAsyncTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMyAsyncTask.cancel(true);
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                if (isCancelled()) {
                    break;
                }
                publishProgress(i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (isCancelled()) {
                return;
            }
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
        }
    }
}
