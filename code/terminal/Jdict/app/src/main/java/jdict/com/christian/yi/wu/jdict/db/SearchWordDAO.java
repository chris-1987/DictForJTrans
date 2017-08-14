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

        dbhelper = new SearchWordDatabaseHelper(context, null, null, 1);
    }

    public void dbInsertWord(String content, String meaning, String language) {

        db = dbhelper.getWritableDatabase();

       ContentValues cv = new ContentValues();

        cv.put("content", content);

        cv.put("meaning", meaning);

        cv.put("language", language);

        db.insert("t_word", "content", cv);

        db.close();
    }

    public Word dbQueryWord(String content, String language) {

        Word word = null;

        db = dbhelper.getWritableDatabase();

        String sql = "select * from t_word where content = ? and language = ?";

        String[] selectionArgs = new String[]{content, language};

        Cursor cursor = db.rawQuery(sql, selectionArgs);

        if (cursor.moveToNext()) {

            word = new Word();

            word.setId(cursor.getInt(cursor.getColumnIndex("id")));

            word.setContent(cursor.getString(cursor.getColumnIndex("content")));

            word.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));
        }

        db.close();

        return word;
    }

    public void dbDeleteWord(int id) {

        db = dbhelper.getWritableDatabase();

        String sql = "delete from t_word where id = ?";

        Object[] bindArgs = new Object[]{id};

        db.execSQL(sql, bindArgs);

        db.close();
    }

    public ArrayList<Word> dbQueryAll() {

        db = dbhelper.getWritableDatabase();

        String sql = "select * from t_word";

        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<Word> wordList = new ArrayList<Word>();

        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {

            Word word = new Word();

            word.setId(cursor.getInt(cursor.getColumnIndex("id")));

            word.setContent(cursor.getString(cursor.getColumnIndex("content")));

            word.setMeaning(cursor.getString(cursor.getColumnIndex("meaning")));

            word.setLanguage(cursor.getString(cursor.getColumnIndex("language")));

            wordList.add(word);
        }

        db.close();

        return wordList;
    }
}
