package jdict.com.christian.yi.wu.jdict.db;

/**
 * Created by Administrator on 2017/8/16.
 */

public class JWord {

    private int mId;

    private String mMeaning;

    private String mHiragana;

    private String mKatakana;

    private String mKannji;

    private int mAdduser;

    private String mSource;


    public String getKannji() {
        return mKannji;
    }

    public void setKannji(String kannji) {
        this.mKannji = kannji;
    }

    public int getAdduser() {
        return mAdduser;
    }

    public void setAdduser(int adduser) {
        this.mAdduser = adduser;
    }

    public String getHiragana() {
        return mHiragana;
    }

    public void setHiragana(String hiragana) {
        this.mHiragana = hiragana;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getKatakana() {
        return mKatakana;
    }

    public void setKatakana(String katakana) {
        this.mKatakana = katakana;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public void setMeaning(String meaning) {
        this.mMeaning = meaning;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        this.mSource = source;
    }

    @Override
    public String toString() {
        return "JWord{" +
                "mAdduser=" + mAdduser +
                ", mId=" + mId +
                ", mMeaning='" + mMeaning + '\'' +
                ", mHiragana='" + mHiragana + '\'' +
                ", mKatakana='" + mKatakana + '\'' +
                ", mKannji='" + mKannji + '\'' +
                ", mLocal=" + mSource +
                '}';
    }
}
