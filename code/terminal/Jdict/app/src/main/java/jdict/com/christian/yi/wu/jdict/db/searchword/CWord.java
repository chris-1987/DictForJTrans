package jdict.com.christian.yi.wu.jdict.db.searchword;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/14.
 */

public class CWord {

    private int mId;

    private String mHanzi;

    private String mHiragana;

    private int mAdduser;

    private Date mAddtime;

    private String mSource;

    public Date getAddtime() {
        return mAddtime;
    }

    public void setAddtime(Date addtime) {
        this.mAddtime = addtime;
    }

    public int getAdduser() {
        return mAdduser;
    }

    public void setAdduser(int adduser) {
        this.mAdduser = adduser;
    }

    public String getHanzi() {
        return mHanzi;
    }

    public void setHanzi(String hanzi) {
        this.mHanzi = hanzi;
    }

    public String getHiragana() {
        return mHiragana;
    }

    public void setmHiragana(String hiragana) {
        this.mHiragana = hiragana;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;

    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        this.mSource = source;
    }

    @Override
    public String toString() {
        return "CWord{" +
                "mAddtime=" + mAddtime +
                ", mId=" + mId +
                ", mHanzi='" + mHanzi + '\'' +
                ", mHiragana='" + mHiragana + '\'' +
                ", mAdduser=" + mAdduser +
                ", mNative=" + mSource +
                '}';
    }
}
