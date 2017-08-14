package jdict.com.christian.yi.wu.jdict.db;

/**
 * Created by Administrator on 2017/8/14.
 */

public class Word {

    private int mId;

    private String mContent;

    private String mMeaning;

    private String mLanguage;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
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
}
