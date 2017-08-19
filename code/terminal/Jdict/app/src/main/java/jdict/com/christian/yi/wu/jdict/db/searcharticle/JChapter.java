package jdict.com.christian.yi.wu.jdict.db.searcharticle;

/**
 * Created by Administrator on 2017/8/19.
 */

public class JChapter {

    private int id;

    private int bookid;

    private String title;

    private String sequenceid;

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSequenceid() {
        return sequenceid;
    }

    public void setSequenceid(String sequenceid) {
        this.sequenceid = sequenceid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "JChapter{" +
                "bookid=" + bookid +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", sequenceid='" + sequenceid + '\'' +
                '}';
    }
}
