package jdict.com.christian.yi.wu.jdict.db.searcharticle;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/19.
 */

public class JBook {

    private int id;

    private String author;

    private String title;

    private String img_url;

    private String summary;

    private int finished;

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "JBook{" +
                "author='" + author + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", img_url='" + img_url + '\'' +
                ", summary='" + summary + '\'' +
                ", finished=" + finished +
                '}';
    }
}
