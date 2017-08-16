package jdict.com.christian.yi.wu.jdict.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/14.
 */

public class SearchWordDAO {

    private SearchWordDatabaseHelper dbhelper;

    private SQLiteDatabase db;

    public SearchWordDAO(Context context) {

        dbhelper = SearchWordDatabaseHelper.newInstance(context);
    }

    public void dbInsertWord(String content, String meaning, String language) {

        db = dbhelper.getWritableDatabase();

        db.beginTransaction();

        ContentValues cv = new ContentValues();

        cv.put("content", content);

        cv.put("meaning", meaning);

        cv.put("language", language);

        db.insert("t_word", null, cv);

        db.setTransactionSuccessful();

        db.endTransaction();
    }

    public Word dbQueryWord(String content, String language) {

        db = dbhelper.getWritableDatabase();

        String selection = "content = ? and language = ?";

        String[] selectionArgs = new String[]{content, language};

        Cursor cursor = db.query("t_word", null, selection, selectionArgs, null, null, null);

        Word word = null;

        if (cursor.moveToNext()) {

            word = new Word();

            word.setId(cursor.getInt(cursor.getColumnIndex("id")));

            word.setContent(cursor.getString(cursor.getColumnIndex("content")));

            word.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));

            word.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
        }

        cursor.close();

        return word;
    }

    public void dbDeleteWord(int id) {

        db = dbhelper.getWritableDatabase();

        db.beginTransaction();

        String whereClause = "id = ?";

        String[] whereArgs = new String[]{Integer.toString(id)};

        db.delete("t_word",whereClause, whereArgs);

        db.setTransactionSuccessful();

        db.endTransaction();

        System.out.println("here3");
    }

    public ArrayList<Word> dbQueryAll() {

        db = dbhelper.getWritableDatabase();

        Cursor cursor = db.query("t_word", null, null, null, null, null,null);

        ArrayList<Word> wordList = new ArrayList<Word>();

        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {

            Word word = new Word();

            word.setId(cursor.getInt(cursor.getColumnIndex("id")));

            word.setContent(cursor.getString(cursor.getColumnIndex("content")));

            word.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));

            word.setLanguage(cursor.getString(cursor.getColumnIndex("language")));

            wordList.add(word);
        }

        cursor.close();


        System.out.println("here4");

        return wordList;
    }
}
