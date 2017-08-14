package jdict.com.christian.yi.wu.jdict.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/8/14.
 */

public class SearchWordDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_PERSON_CWORDLIST = "create table t_word("
           + "id integer primary key autoincrement, "
            + "content text,"
            + "meaning text,"
            + "language text)";

    public SearchWordDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_PERSON_CWORDLIST);
    }

    @Override

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //TODO
    }

}
