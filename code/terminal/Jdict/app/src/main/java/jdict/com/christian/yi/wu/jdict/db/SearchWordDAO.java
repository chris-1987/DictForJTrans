package jdict.com.christian.yi.wu.jdict.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/14.
 */

public class SearchWordDAO {

    private SearchWordDatabaseHelper dbhelper;

    private SQLiteDatabase db;

    public SearchWordDAO(Context context) {

        dbhelper = SearchWordDatabaseHelper.newInstance(context);
    }

    /**
     * cache in tbl_cache_cword
     *
     * @param content content related to the word (hanzi by default)
     * @param meaning meaning of the word
     * @param language zh or jp
     */
    public void dbCacheWord(String content, String meaning, String language) {

        db = dbhelper.getWritableDatabase();

        db.beginTransaction();

        ContentValues cv = new ContentValues();

        cv.put("content", content);

        cv.put("meaning", meaning);

        switch (language) {
            case "zh":
                db.insert("tbl_cache_cword", null, cv);
                break;
            case "jp":
                db.insert("tbl_cache_jword", null, cv);
                break;
        }

        db.setTransactionSuccessful();

        db.endTransaction();
    }

    /**
     * search wordview for the target word
     *
     * @param content  hanzi for "zh" and katakana for "jp"
     * @param language zh or jp
     * @return wordview list for the target word
     */
    public ArrayList<WordView> dbQueryWord(String content, String language) {

        db = dbhelper.getWritableDatabase();

        ArrayList<WordView> wordViewList = new ArrayList<WordView>();

        // search content in the specified cache table
        {
            String selection = "content = ?";

            String[] selectionArgs = new String[]{content};

            Cursor cursor = null;

            switch (language) {

                case "zh":
                    cursor = db.query("tbl_cache_cword", null, selection, selectionArgs, null, null, null);
                    break;

                case "jp":
                    cursor = db.query("tbl_cache_jword", null, selection, selectionArgs, null, null, null);
                    break;
            }

            for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {

                WordView wordView = new WordView();

                wordView.setContent(cursor.getString(cursor.getColumnIndex("content")));

                wordView.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));

                wordViewList.add(wordView);
            }

            cursor.close();
        }

        // search for tbl_cword
        {
            Cursor cursor = null;

            String selection = null;

            String[] selectionArgs = null;

            switch (language) {

                case "zh":

                    selection = "hanzi = ?";

                    selectionArgs = new String[]{content};

                    cursor = db.query("tbl_cword", null, selection, selectionArgs, null, null, null);

                    for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {

                        WordView wordView = new WordView();

                        wordView.setContent(cursor.getString(cursor.getColumnIndex("hanzi")));

                        wordView.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));

                        wordViewList.add(wordView);
                    }

                    break;

                case "jp":

                    selection = "hiragana = ? or kannji = ?";

                    selectionArgs = new String[]{content, content};

                    cursor = db.query("tbl_jword", null, selection, selectionArgs, null, null, null);

                    for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {

                        WordView wordView = new WordView();

                        wordView.setContent(cursor.getString(cursor.getColumnIndex("hiragana")) + " " + cursor.getString(cursor.getColumnIndex("kannji")));

                        wordView.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));

                        wordViewList.add(wordView);
                    }

                    break;
            }

            cursor.close();
        }

        return wordViewList;
    }


    /**
     * clear the specified cache table
     */
    public void dbClearCacheCWord(String language) {

        db = dbhelper.getWritableDatabase();

        db.beginTransaction();

        switch (language) {

            case "zh":
                db.delete("tbl_cache_cword", null, null);
                break;
            case "jp":
                db.delete("tbl_cache_jword", null, null);
                break;
        }

        db.setTransactionSuccessful();

        db.endTransaction();
    }

    /**
     * @return cwords cached in the specified cache table
     */
    public ArrayList<WordView> dbQueryCachedWords(String language) {

        db = dbhelper.getWritableDatabase();

        Cursor cursor = null;

        switch (language) {

            case "zh":
                cursor = db.query("tbl_cache_cword", null, null, null, null, null, null);
                break;

            case "jp":
                cursor = db.query("tbl_cache_jword", null, null, null, null, null, null);
                break;
        }

        ArrayList<WordView> wordViewList = new ArrayList<WordView>();

        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {

            WordView wordView = new WordView();

            wordView.setContent(cursor.getString(cursor.getColumnIndex("content")));

            wordView.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));

            wordViewList.add(wordView);
        }

        cursor.close();

        return wordViewList;
    }

    /**
     * search in approximate mode
     * @param content prefix
     * @param language zh or jp
     * @return records match content*
     */
    public ArrayList<WordView> dbQuerySuggestion(String content, String language) {

        db = dbhelper.getWritableDatabase();

        ArrayList<WordView> wordViewList = new ArrayList<WordView>();

        // search content in the specified cache table
        {
            String selection = "content like '" + content + "%'";

            String[] selectionArgs = new String[]{content};

            Cursor cursor = null;

            switch (language) {

                case "zh":
                    cursor = db.query("tbl_cache_cword", null, selection, null, null, null, null);
                    break;

                case "jp":
                    cursor = db.query("tbl_cache_jword", null, selection, null, null, null, null);
                    break;
            }

            for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {

                WordView wordView = new WordView();

                wordView.setContent(cursor.getString(cursor.getColumnIndex("content")));

                wordView.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));

                wordViewList.add(wordView);
            }

            cursor.close();
        }

//        // search for tbl_cword
//        {
//            Cursor cursor = null;
//
//            String selection = null;
//
//            String[] selectionArgs = null;
//
//            switch (language) {
//
//                case "zh":
//
//                    selection = "hanzi like ?%";
//
//                    selectionArgs = new String[]{content};
//
//                    cursor = db.query("tbl_cword", null, selection, selectionArgs, null, null, null);
//
//                    for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
//
//                        WordView wordView = new WordView();
//
//                        wordView.setContent(cursor.getString(cursor.getColumnIndex("hanzi")));
//
//                        wordView.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));
//
//                        wordViewList.add(wordView);
//                    }
//
//                    cursor.close();
//
//                    break;
//
//                case "jp":
//
//                    selection = "hiragana like ?% or kannji like ?%";
//
//                    selectionArgs = new String[]{content, content};
//
//                    cursor = db.query("tbl_jword", null, selection, selectionArgs, null, null, null);
//
//                    for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
//
//                        WordView wordView = new WordView();
//
//                        wordView.setContent(cursor.getString(cursor.getColumnIndex("hiragana")) + " " + cursor.getString(cursor.getColumnIndex("kannji")));
//
//                        wordView.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));
//
//                        wordViewList.add(wordView);
//                    }
//
//                    cursor.close();
//
//                    break;
//            }
//        }

        return wordViewList;
    }
}
