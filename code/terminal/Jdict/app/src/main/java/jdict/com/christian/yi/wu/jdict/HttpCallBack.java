package jdict.com.christian.yi.wu.jdict;

/**
 * Created by Administrator on 2017/8/14.
 */

public interface HttpCallBack {

    void onSuccess(String result);

    void onFailure(String exception);
}
