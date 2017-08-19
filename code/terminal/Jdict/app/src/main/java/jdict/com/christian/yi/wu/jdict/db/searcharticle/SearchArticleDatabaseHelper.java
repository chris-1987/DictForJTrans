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
    private static final String CREATE_TBL_UPDATED_JBOOK = "create table if not exists tbl_updated_jbook("
            + "id integer primary key autoincrement, "
            + "author varchar(255) not null,"
            + "title varchar(255) not null,"
            + "img_url varchar(255),"
            + "summary varchar(1000))";

    private static final String CREATE_TBL_FINISHED_JBOOK = "create table if not exists tbl_finished_jbook("
            + "id integer primary key autoincrement, "
            + "author varchar(255) not null,"
            + "title varchar(255) not null,"
            + "img_url varchar(255),"
            + "summary varchar(1000)";

    private static final String CREATE_TBL_JCHAPTER = "create table if not exists tbl_jchapter("
            + "id integer primary key autoincrement, "
            + "title varchar(255) not null,"
            + "summary varchar(1000),"
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

        sqLiteDatabase.execSQL(CREATE_TBL_UPDATED_JBOOK);

        sqLiteDatabase.execSQL(CREATE_TBL_FINISHED_JBOOK);

        sqLiteDatabase.execSQL(CREATE_TBL_JCHAPTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //TODO
    }
}
