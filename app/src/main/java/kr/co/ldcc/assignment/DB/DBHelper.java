package kr.co.ldcc.assignment.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "Vod.db", null, 1);
    }


    // Test.db가 없으면 새롭게 만든다.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("test","데이터베이스가 생성되었습니다.");

        String sql = "create table Reply("
                +"id integer primary key autoincrement, "
                +"replyData text not null, "
                +"cId integer not null, "
                +"writer text not null"
//                +"userid integer not null, "
//                +"floatData real not null, "
//                +"dateData date not null"
                +")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}