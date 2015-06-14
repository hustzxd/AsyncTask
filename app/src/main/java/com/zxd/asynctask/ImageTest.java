package com.zxd.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * Created by zxd on 2015/6/10.
 */
public class ImageTest extends Activity {

    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static String URL =
            "http://g.hiphotos.baidu.com/image/w%3D310/sign=935fee0fe7dde711e7d245f797efcef4/838ba61ea8d3fd1f0709fc30324e251f95ca5fb3.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image);

        mImageView = (ImageView) findViewById(R.id.image);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        new MyAsyncTask().execute(URL);
    }

    class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProgressBar.setVisibility(View.GONE);
            mImageView.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //获取传递进来的参数
            String url = params[0];
            Bitmap bitmap = null;
            URLConnection connection;
            InputStream is;
            try {

                connection = new URL(url).openConnection();
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                //通过decodeStream方法解析输入流
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
