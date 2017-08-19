package jdict.com.christian.yi.wu.jdict.db.searchword;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/8/14.
 */

public class SearchWordDatabaseHelper extends SQLiteOpenHelper {

    // chinese->japanese, retrieve from remote server
    private static final String CREATE_TBL_CWORD = "create table if not exists tbl_cword("
           + "id integer primary key autoincrement, "
            + "hanzi varchar(255) not null,"
            + "adduser integer not null,"
            + "addtime timestamp not null,"
            + "hiragana varchar(255) not null)";

    // japanese->japanese, retrieve from remote server
    private static final String CREATE_TBL_JWORD = "create table if not exists tbl_jword("
            + "id integer primary key autoincrement,"
            + "meaning text not null,"
            + "adduser integer not null,"
            + "addtime timestamp not null,"
            + "hiragana varchar(255) not null,"
            + "katakana varchar(255) not null,"
            + "kannji varchar(255) not null)";

    // chinese->japanese, retrieve from fanyi.baidu.com
    private static final String CREATE_TBL_CACHE_CWORD = "create table if not exists tbl_cache_cword("
            + "id integer primary key autoincrement, "
            + "content varchar(255) not null,"
            + "addtime timestamp default current_timestamp,"
            + "meaning varchar(255) not null)";

    // japanese->chinese, retrieve from fanyi.baidu.com
    private static final String CREATE_TBL_CACHE_JWORD = "create table if not exists tbl_cache_jword("
            + "id integer primary key autoincrement,"
            + "content varchar(255) not null,"
            + "addtime timestamp default current_timestamp,"
            + "meaning varchar(255) not null)";

    /**
     * japanese word (hiragana, katakana, meaning)
     */
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

        sqLiteDatabase.execSQL(CREATE_TBL_CWORD);

        sqLiteDatabase.execSQL(CREATE_TBL_JWORD);

        sqLiteDatabase.execSQL(CREATE_TBL_CACHE_CWORD);

        sqLiteDatabase.execSQL(CREATE_TBL_CACHE_JWORD);
    }

    @Override

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //TODO
        System.out.println("todo: update db");
    }

}
