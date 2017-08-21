package jdict.com.christian.yi.wu.jdict.searchword;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

import jdict.com.christian.yi.wu.jdict.utility.HttpCallBack;
import jdict.com.christian.yi.wu.jdict.utility.MD5Encoder;


/**
 * Request translation service from Baidu
 * @author wu.yi.christian
 */
public class SearchWordRequestUtils {

    private static final String UTF8 = "utf-8";

    private static final String APP_ID = "20170814000072958"; // app id

    private static final String SECRET_KEY = "TbdD_qxVs5HRc3mGHdEO";   // token

    private static final String baseURL = "http://api.fanyi.baidu.com/api/trans/vip/translate";    //http prefix (fixed)

    private static final Random random = new Random();    //random number for generating MD5

    public SearchWordRequestUtils() {

    }

    /**
     *
     * @param query word to be queried
     * @param from source languange
     * @param to target language
     * @param callBack callback for processing http exception
     */
    public void translate(final String query, final String from, final String to, final HttpCallBack callBack) throws Exception {

        // salt for generating md5 randomly
        int salt = random.nextInt(10000);

        // generate md5 for (APP_ID + query (utf-8 required) + salt + SECRET_KEY)
        StringBuilder signBuilder = new StringBuilder();

        signBuilder.append(APP_ID).append(new String(query.getBytes(), UTF8)).append(salt).append(SECRET_KEY);

        final String sign = MD5Encoder.encode(signBuilder.toString());

        // encode into url
        final URL urlFinal = new URL(baseURL + "?q=" + URLEncoder.encode(query, UTF8) +
                "&from=" + from + "&to=" + to + "&appid=" + APP_ID + "&salt=" + salt + "&sign=" + sign);

        System.out.println("urlFinal: " +urlFinal);

        // visit baidu server asynchronously
        new AsyncTask<Void, Integer, String>() {

            @Override
            protected String doInBackground(Void... params) {

                String text = null;

                HttpURLConnection conn = null;

                try {
                    conn = (HttpURLConnection) urlFinal.openConnection();

                    conn.setRequestMethod("GET");

                    //timeout
                    conn.setConnectTimeout(8000);

                    // connect to inputstream
                    InputStream is = conn.getInputStream();

                    InputStreamReader isr = new InputStreamReader(is);

                    BufferedReader br = new BufferedReader(isr);

                    // read feedback
                    String line;

                    StringBuilder builder = new StringBuilder();

                    while ((line = br.readLine()) != null) {

                        builder.append(line).append("\n");
                    }

                    // disconnect
                    br.close();

                    isr.close();

                    is.close();

                    // decode result encoded in json
                    JSONObject resultJson = new JSONObject(builder.toString());

                    // retrieve result
                    JSONArray jsonArray = (JSONArray) resultJson.get("trans_result");

                    JSONObject dstJson = (JSONObject) jsonArray.get(0);

                    text = dstJson.getString("dst");

                    text = URLDecoder.decode(text, UTF8);

                } catch (IOException e) {
                    e.printStackTrace();
                    callBack.onFailure(e.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onFailure(e.toString());
                } finally {// finalize
                    if (conn != null) {
                        conn.disconnect();
                    }
                }

                return text;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                callBack.onSuccess(s);
            }
        }.execute();
    }

}
