package jdict.com.christian.yi.wu.jdict.db.searcharticle;

/**
 * Created by Administrator on 2017/8/19.
 */

public class JChapter {

    private int id;

    private int bookid;

    private String title;

    private int sequenceid;

    private String file_url;

    private long addtime;

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

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

    public int getSequenceid() {
        return sequenceid;
    }

    public void setSequenceid(int sequenceid) {
        this.sequenceid = sequenceid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "JChapter{" +
                "addtime=" + addtime +
                ", id=" + id +
                ", bookid=" + bookid +
                ", title='" + title + '\'' +
                ", sequenceid=" + sequenceid +
                ", file_url='" + file_url + '\'' +
                '}';
    }
}
