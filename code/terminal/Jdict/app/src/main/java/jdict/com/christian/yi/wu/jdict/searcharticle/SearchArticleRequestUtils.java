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
import jdict.com.christian.yi.wu.jdict.utility.PullByHttpPost;

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
     *
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
        PullByHttpPost asyncTask = new PullByHttpPost(callBack);
        asyncTask.execute(params);
    }

    /**
     * do:download images for books cached in local table
     *
     * @param callBack
     */
    public void downloadImgForBook(ArrayList<JBook> finishedBookList, ArrayList<JBook> unfinishedBookList, final HttpCallBack callBack) {

        String imgUrlList = new String();
        String delimiter = ",";
        for (JBook book : finishedBookList) {
            imgUrlList += book.getImg_url();
            imgUrlList += delimiter;
        }
        for (JBook book : unfinishedBookList) {
            imgUrlList += book.getImg_url();
            imgUrlList += delimiter;
        }
        imgUrlList = imgUrlList.substring(0, imgUrlList.lastIndexOf(delimiter)); // get rid of the last delimiter
        Log.d("imgUrlList", imgUrlList.toString());

        int paramNum = 5; // (paramNum  + serverUrl + act + imgUrlList + delimiter)
        MyPair.StringPair[] params = new MyPair.StringPair[paramNum];
        params[0] = new MyPair.StringPair("paramNum", Integer.toString(paramNum));
        final String urlFinal = baseURL + extendURL_ARTICLE;
        params[1] = new MyPair.StringPair("url", urlFinal);
        params[2] = new MyPair.StringPair("act", "downloadBookImage");
        params[3] = new MyPair.StringPair("imgUrlList", imgUrlList);
        params[4] = new MyPair.StringPair("delimiter", delimiter);

        // execute httpRequest
        PullByHttpPost asyncTask = new PullByHttpPost(callBack);
        asyncTask.execute(params);
    }

    /**
     * do:retrieve finished book
     *
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
        PullByHttpPost asyncTask = new PullByHttpPost(callBack);
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
        PullByHttpPost asyncTask = new PullByHttpPost(callBack);
        asyncTask.execute(params);
    }

}



