package jdict.com.christian.yi.wu.jdict.searcharticle;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jdict.com.christian.yi.wu.jdict.db.searcharticle.JBook;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.JChapter;
import jdict.com.christian.yi.wu.jdict.db.searcharticle.SearchArticleDAO;
import jdict.com.christian.yi.wu.jdict.utility.HttpCallBack;
import jdict.com.christian.yi.wu.jdict.utility.MyPair;

public class SearchArticleRequestUtils {

    private static final String UTF8 = "utf-8";

    private static final String baseURL = "http://222.200.182.93:8080/jdict/script";

    private static final String extendURL_ARTICLE = "/search_article.php";

    private static int offsetArticleFinished = 0;

    private static final int NUM_ARTICLE_FINISHED = 6;

    private static int offsetArticleUpdated = 0;

    private static final int NUM_ARTICLE_UPDATED = 6;

    private static long cacheBookTime = new Date(0).getTime() / 1000; // in seconds

    public SearchArticleRequestUtils() {

    }

    /**
     * do:refresh books cached in local table
     * @param callBack call back func
     */
    public void refreshCachedBook(final HttpCallBack callBack) {

        // send a request to the remote server
        int paramNum = 4; // (paramNum, url, act, cacheDate);

        MyPair.StringPair[] params = new MyPair.StringPair[paramNum];

        // number of params
        params[0] = new MyPair.StringPair("paramNum", Integer.toString(paramNum));
        final String urlFinal = baseURL + extendURL_ARTICLE;
        params[1] = new MyPair.StringPair("url", urlFinal);
        params[2] = new MyPair.StringPair("act", "refreshCachedBook");
        params[3] = new MyPair.StringPair("cacheBookTime", Long.toString(cacheBookTime));

        // execute httpRequest
        PullBookByHttpPost asyncTask = new PullBookByHttpPost(callBack);
        asyncTask.execute(params);
    }

    /**
     * do:download images for books cached in local table
     * @param callBack
     */
    public void downloadImgForCachedBook(String imgUrlList, String delimiter, final HttpCallBack callBack) {

        int paramNum = 4;

        MyPair.StringPair[] params = new MyPair.StringPair[paramNum];

        // number of params
        params[0] = new MyPair.StringPair("paramNum", Integer.toString(paramNum));
        final String urlFinal = baseURL + extendURL_ARTICLE;
        params[1] = new MyPair.StringPair("url", urlFinal);
        params[2] = new MyPair.StringPair("imgUrlList", "imgUrlList");
        params[3] = new MyPair.StringPair("delimiter", delimiter);

        // execute httpRequest
        PullBookByHttpPost asyncTask = new PullBookByHttpPost(callBack);
        asyncTask.execute(params);


    }

    /**
     * do:retrieve finished book
     * @param callBack callback func
     */
    public void retrieveFinishedBook(final HttpCallBack callBack) {

        int paramNum = 5;

        MyPair.StringPair[] params = new MyPair.StringPair[paramNum];

        // number of params
        params[0] = new MyPair.StringPair("paramNum", Integer.toString(paramNum));
        final String urlFinal = baseURL + extendURL_ARTICLE;
        params[1] = new MyPair.StringPair("url", urlFinal);
        params[2] = new MyPair.StringPair("act", "retrieveFinishedBook");
        params[3] = new MyPair.StringPair("offset", Integer.toString(offsetArticleFinished));
        params[4] = new MyPair.StringPair("num", Integer.toString(NUM_ARTICLE_FINISHED));

        // execute httpRequest
        PullBookByHttpPost asyncTask = new PullBookByHttpPost(callBack);
        asyncTask.execute(params);
    }


    public void retrieveUpdatedBook(final HttpCallBack callBack) {

        int paramNum = 5;

        MyPair.StringPair[] params = new MyPair.StringPair[paramNum];

        // number of params
        params[0] = new MyPair.StringPair("paramNum", Integer.toString(paramNum));
        final String urlFinal = baseURL + extendURL_ARTICLE;
        params[1] = new MyPair.StringPair("url", urlFinal);
        params[2] = new MyPair.StringPair("act", "retrieveUpdatedBook");
        params[3] = new MyPair.StringPair("offset", Integer.toString(offsetArticleUpdated));
        params[4] = new MyPair.StringPair("num", Integer.toString(NUM_ARTICLE_UPDATED));

        // execute httpRequest
        PullChapterByHttpPost asyncTask = new PullChapterByHttpPost(callBack);
        asyncTask.execute(params);
    }

    class PullBookByHttpPost extends AsyncTask<MyPair.StringPair, Integer, String> {

        private HttpURLConnection conn = null;

        private URL url = null;

        private final HttpCallBack callBack;

        public PullBookByHttpPost(final HttpCallBack callBack) {

            this.callBack = callBack;
        }

        @Override
        protected String doInBackground(MyPair.StringPair... params) {

            // send a request
            try {
                int paramNum = Integer.parseInt(params[0].second); // first param indicates number of paramss
                url = new URL(params[1].second); // second param indicate url

                // connect to server using POST method
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(8000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // wrap request body and send it to remote server
                Uri.Builder builder = new Uri.Builder();
                for (int i = 2; i < paramNum; ++i) {
                    builder.appendQueryParameter(params[i].first, params[i].second);
                }
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(builder.build().getEncodedQuery());
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
                callBack.onFailure(e.toString());
            }

            // receive a response
            String result = null;

            try {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                br.close();
                is.close();
                result = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                callBack.onFailure(e.toString());
            } finally {
                conn.disconnect();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           callBack.onSuccess(s);
        }
    }

    class PullChapterByHttpPost extends AsyncTask<MyPair.StringPair, Integer, String> {

        private HttpURLConnection conn = null;

        private URL url = null;

        private final HttpCallBack callBack;

        public PullChapterByHttpPost(final HttpCallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        protected String doInBackground(MyPair.StringPair... params) {

            // send a request
            try {
                int paramNum = Integer.parseInt(params[0].second); // first param indicates number of parameters
                url = new URL(params[1].second); // second param indicate url

                // connect to server using POST method
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(8000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // wrap request body and send it to remote server
                Uri.Builder builder = new Uri.Builder();
                for (int i = 2; i < paramNum; ++i) {
                    builder.appendQueryParameter(params[i].first, params[i].second);
                }
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(builder.build().getEncodedQuery());
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
                callBack.onFailure(e.toString());
            }

            // receive a response
            String result = null;
            try {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                br.close();
                is.close();
                result = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                callBack.onFailure(e.toString());
            } finally {
                conn.disconnect();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            callBack.onSuccess(s);
        }
    }

}



