package jdict.com.christian.yi.wu.jdict.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/8/14.
 */

public class SearchWordDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_PERSON_WORDLIST = "create table if not exists t_word("
           + "id integer primary key autoincrement, "
            + "content text,"
            + "meaning text,"
            + "language text)";

    private static final String DATABASE_NAME = "word.db";

    private static final int VERSION = 1;

    private static SearchWordDatabaseHelper dbHelper = null;

    private SearchWordDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }

    public static SearchWordDatabaseHelper newInstance(Context context) {

        if (dbHelper == null) {

            dbHelper = new SearchWordDatabaseHelper(context);
        }

        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_PERSON_WORDLIST);
    }

    @Override

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //TODO

        System.out.println("update db");
    }

}
