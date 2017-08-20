package jdict.com.christian.yi.wu.jdict.db.searcharticle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/19.
 */

public class SearchArticleDatabaseHelper extends SQLiteOpenHelper {

    //
    private static final String CREATE_TBL_CACHE_JBOOK = "create table if not exists tbl_cache_jbook("
            + "id integer primary key autoincrement, "
            + "author varchar(255) not null,"
            + "title varchar(255) not null,"
            + "finished integer not null,"
            + "img_url varchar(255),"
            + "summary varchar(1000))";

    private static final String CREATE_TBL_CACHE_JCHAPTER = "create table if not exists tbl_cache_jchapter("
            + "id integer primary key autoincrement, "
            + "title varchar(255) not null,"
            + "file_url varchar(1000),"
            + "bookid integer not null,"
            + "sequenceid integer not null)";

    private static final String DATABASE_NAME = "article.db";

    private static final int VERSION = 1;

    private static SearchArticleDatabaseHelper dbHelper = null;

    public SearchArticleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static SearchArticleDatabaseHelper newInstance(Context context) {

        if (dbHelper == null) {

            dbHelper = new SearchArticleDatabaseHelper(context);
        }

        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TBL_CACHE_JBOOK);

        sqLiteDatabase.execSQL(CREATE_TBL_CACHE_JCHAPTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //TODO
    }
}
