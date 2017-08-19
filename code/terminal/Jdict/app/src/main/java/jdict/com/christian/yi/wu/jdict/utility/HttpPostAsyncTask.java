package jdict.com.christian.yi.wu.jdict.utility;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jdict.com.christian.yi.wu.jdict.db.searcharticle.JBook;

/**
 * Created by Administrator on 2017/8/19.
 */

public class HttpPostAsyncTask extends AsyncTask<MyPair.StringPair, Integer, String> {

    HttpURLConnection conn = null;

    URL url = null;

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
        }

        // receive a response
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

            return builder.toString();

        } catch (IOException e) {

            e.printStackTrace();

            return e.toString();

        } finally {

            conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ArrayList<JBook> books = new ArrayList<>();

        try {

            JSONArray jArr = new JSONArray(s);

            for (int i = 0; i < jArr.length(); ++i) {

                JSONObject json_data = jArr.getJSONObject(i);

                JBook book = new JBook();

                book.setId(json_data.getInt("id"));

                book.setAuthor(json_data.getString("author"));

                book.setTitle(json_data.getString("title"));

                book.setImg_url(json_data.getString("img_url"));

                book.setSummary(json_data.getString("summary"));

                book.setFinished(json_data.getInt("finished"));

                books.add(book);
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}
