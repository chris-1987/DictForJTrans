package jdict.com.christian.yi.wu.jdict.db;

/**
 * Created by Administrator on 2017/8/16.
 */

public class WordView {

    private String mContent;

    private String mMeaning;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public void setMeaning(String meaning) {
        this.mMeaning = meaning;
    }


    @Override
    public String toString() {
        return "WordView{" +
                "mContent='" + mContent + '\'' +
                ", mMeaning='" + mMeaning + '\'' +
                '}';
    }
}
