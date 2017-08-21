package jdict.com.christian.yi.wu.jdict.db.searcharticle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/8/19.
 */

public class SearchArticleDAO {

    private SearchArticleDatabaseHelper dbhelper;
    private SQLiteDatabase db;

    public SearchArticleDAO(Context context) {
        dbhelper = SearchArticleDatabaseHelper.newInstance(context);
    }

    /**
     * do: clear cached book
     */
    public void clearCachedBook() {
        db = dbhelper.getWritableDatabase();
        db.beginTransaction();
        db.delete("tbl_cache_jbook", null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * do: clear cached chapters
     */
    public void clearCachedChapter() {
        db = dbhelper.getWritableDatabase();
        db.beginTransaction();
        db.delete("tbl_cache_jchapter", null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * do: update tbl_cache_jbook
     *
     * @param bookList book list, to cache
     * @note call the function after the execution of clearCachedBook
     */
    public void updateCachedBook(ArrayList<JBook> bookList) {

        db = dbhelper.getWritableDatabase();
        db.beginTransaction();

        for (JBook book : bookList) {
            ContentValues cv = new ContentValues();
            cv.put("id", book.getId());
            cv.put("author", book.getAuthor());
            cv.put("title", book.getTitle());
            cv.put("img_url", book.getImg_url());
            cv.put("summary", book.getSummary());
            cv.put("finished", book.getFinished());
            db.insert("tbl_cache_jbook", null, cv);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * do: update tbl_cache_jchapter
     *
     * @param chapterList chapter list, to cache
     */
    public void updateCachedChapter(ArrayList<JChapter> chapterList) {
        db = dbhelper.getWritableDatabase();
        db.beginTransaction();
        for (JChapter chapter : chapterList) {
            ContentValues cv = new ContentValues();
            cv.put("id", chapter.getId());
            cv.put("bookid", chapter.getBookid());
            cv.put("sequenceid", chapter.getSequenceid());
            cv.put("title", chapter.getTitle());
            cv.put("file_url", chapter.getFile_url());
            cv.put("addtime", chapter.getAddtime());
            db.insert("tbl_cache_jchapter", null, cv);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * @param finishedBookList   finished book list, to fill
     * @param unfinishedBookList updated book list, to fill
     * @param chapterList        chapter list, to fill
     * @note params must be initialized in advance
     */
    public void queryDB(ArrayList<JBook> finishedBookList, ArrayList<JBook> unfinishedBookList, ArrayList<JChapter> chapterList) {

        db = dbhelper.getWritableDatabase();
        db.beginTransaction();

        Cursor cursor = null;
        cursor = db.query("tbl_cache_jbook", null, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                JBook jBook = new JBook();
                jBook.setId(cursor.getInt(cursor.getColumnIndex("id")));
                jBook.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                jBook.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
                jBook.setImg_url(cursor.getString(cursor.getColumnIndex("img_url")));
                jBook.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                jBook.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
                if (jBook.getFinished() == 1) {
                    finishedBookList.add(jBook);
                } else {
                    unfinishedBookList.add(jBook);
                }
            }
        }
        cursor.close();

        cursor = db.query("tbl_cache_jchapter", null, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                JChapter jChapter = new JChapter();
                jChapter.setId(cursor.getInt(cursor.getColumnIndex("id")));
                jChapter.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                jChapter.setBookid(cursor.getInt(cursor.getColumnIndex("bookid")));
                jChapter.setSequenceid(cursor.getInt(cursor.getColumnIndex("sequenceid")));
                jChapter.setFile_url(cursor.getString(cursor.getColumnIndex("file_url")));
                chapterList.add(jChapter);
            }
        }
        cursor.close();

        db.setTransactionSuccessful();
        db.endTransaction();
    }
}

