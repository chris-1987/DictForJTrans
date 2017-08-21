package jdict.com.christian.yi.wu.jdict.utility;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/8/21.
 */


public class PullByHttpPost extends AsyncTask<MyPair.StringPair, Integer, String> {

    private HttpURLConnection conn = null;

    private URL url = null;

    private final HttpCallBack callBack;

    public PullByHttpPost(final HttpCallBack callBack) {

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