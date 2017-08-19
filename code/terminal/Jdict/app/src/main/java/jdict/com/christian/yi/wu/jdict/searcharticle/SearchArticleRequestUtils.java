package jdict.com.christian.yi.wu.jdict.searcharticle;

import java.util.Date;

import jdict.com.christian.yi.wu.jdict.utility.HttpCallBack;
import jdict.com.christian.yi.wu.jdict.utility.HttpPostAsyncTask;
import jdict.com.christian.yi.wu.jdict.utility.MyPair;

public class SearchArticleRequestUtils {

    private static final String UTF8 = "utf-8";

    private static final String baseURL = "http://222.200.182.93:8080/jdict/script";

    private static final String REFRESH_SEARCH_ARTICLE_FRAGMENT_extendURL = "/search_article.php";

    public SearchArticleRequestUtils() {

    }

    public void refreshSearchArticleFragment(String version, final HttpCallBack callBack) throws Exception {

        int paramNum = 4;

        MyPair.StringPair[] params = new MyPair.StringPair[paramNum];

        // number of params
        params[0] = new MyPair.StringPair("paramNum", Integer.toString(paramNum));

        // request url
        final String urlFinal = baseURL + REFRESH_SEARCH_ARTICLE_FRAGMENT_extendURL;
        params[1] = new MyPair.StringPair("url", urlFinal);

        // request params
        params[2] = new MyPair.StringPair("act", "refreshSearchArticleFragment");
        params[3] = new MyPair.StringPair("ver", version); // pack version

        // execute httpRequest
        HttpPostAsyncTask asyncTask = new HttpPostAsyncTask();
        asyncTask.execute(params);
    }
}
