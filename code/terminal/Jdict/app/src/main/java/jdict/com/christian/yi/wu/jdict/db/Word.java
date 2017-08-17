package jdict.com.christian.yi.wu.jdict.db;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/14.
 */

public class Word {

    private int mId;

    private String mContent;

    private String mMeaning;

    private Date mAddtime;

    public int getId() {

        return mId;
    }

    public void setId(int id) {

        mId = id;
    }

    public Date getAddtime() {

        return mAddtime;
    }

    public void setAddtime(Date addtime) {

        mAddtime = addtime;
    }

    public String getContent() {

        return mContent;
    }

    public void setContent(String content) {

        mContent = content;
    }

    public String getMeaning() {

        return mMeaning;
    }

    public void setMeaning(String meaning) {

        mMeaning = meaning;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mContent='" + mContent + '\'' +
                ", mId=" + mId +
                ", mLanguage='" + mAddtime + '\'' +
                ", mMeaning='" + mMeaning + '\'' +
                '}';
    }
}
