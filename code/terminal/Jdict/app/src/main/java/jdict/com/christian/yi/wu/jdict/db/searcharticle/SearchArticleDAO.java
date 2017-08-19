package jdict.com.christian.yi.wu.jdict.db.searcharticle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import jdict.com.christian.yi.wu.jdict.utility.HttpPostAsyncTask;

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
     * clear database
     */
    private void clearDB() {

        db = dbhelper.getWritableDatabase();

        db.delete("tbl_updated_jbook", null, null);
        db.delete("tbl_finished_jbook", null, null);
        db.delete("tbl_jchapter", null, null);
    }

    /**
     * update database
     *
     * @param finishedBookList finished book list
     * @param updatedBookList updated book list
     * @param chapterList chapter list
     * @note call the function after the execution of clearDB
     */
    private void updateDB(ArrayList<JBook> finishedBookList, ArrayList<JBook> updatedBookList, ArrayList<JChapter> chapterList) {

        db = dbhelper.getWritableDatabase();

        for (JBook book : finishedBookList) {

            ContentValues cv = new ContentValues();
            cv.put("id", book.getId());
            cv.put("author", book.getAuthor());
            cv.put("title", book.getTitle());
            cv.put("img_url", book.getImg_url());
            cv.put("summary", book.getSummary());
            db.insert("tbl_finshed_jbook", null, cv);
        }

        for (JBook book : updatedBookList) {

            ContentValues cv = new ContentValues();
            cv.put("id", book.getId());
            cv.put("author", book.getAuthor());
            cv.put("title", book.getTitle());
            cv.put("img_url", book.getImg_url());
            cv.put("summary", book.getSummary());
            db.insert("tbl_updated_jbook", null, cv);
        }

        for (JChapter chapter : chapterList) {

            ContentValues cv = new ContentValues();
            cv.put("id", chapter.getId());
            cv.put("author", chapter.getBookid());
            cv.put("title", chapter.getTitle());
            cv.put("img_url", chapter.getSequenceid());
            cv.put("summary", chapter.getTitle());
            db.insert("tbl_jchapter", null, cv);
        }
    }

    /**
     *
     * @param finishedBookList finished book list, to fill
     * @param updatedBookList updated book list, to fill
     * @param chapterList chapter list, to fill
     * @note params must be initialized in advance
     */
    private void queryDB(ArrayList<JBook> finishedBookList, ArrayList<JBook> updatedBookList, ArrayList<JChapter> chapterList) {

        db = dbhelper.getWritableDatabase();
        Cursor cursor = null;

        cursor = db.query("tbl_finished_jbook", null, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                JBook jBook = new JBook();
                jBook.setId(cursor.getInt(cursor.getColumnIndex("id")));
                jBook.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                jBook.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
                jBook.setImg_url(cursor.getString(cursor.getColumnIndex("img_url")));
                jBook.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                finishedBookList.add(jBook);
            }
        }
        cursor.close();

        cursor = db.query("tbl_updated_jbook", null, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                JBook jBook = new JBook();
                jBook.setId(cursor.getInt(cursor.getColumnIndex("id")));
                jBook.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                jBook.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
                jBook.setImg_url(cursor.getString(cursor.getColumnIndex("img_url")));
                jBook.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                updatedBookList.add(jBook);
            }
        }
        cursor.close();

        cursor = db.query("tbl_jchapter", null, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                JChapter jChapter = new JChapter();
                jChapter.setId(cursor.getInt(cursor.getColumnIndex("id")));
                jChapter.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                jChapter.setBookid(cursor.getInt(cursor.getColumnIndex("bookid")));
                jChapter.setSequenceid(cursor.getString(cursor.getColumnIndex("sequenceid")));
                chapterList.add(jChapter);
            }
        }
        cursor.close();
    }
}

