package lyu.klt.graduationdesign.module.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
* @ClassName: KangSQLiteOpenHelper 
* @Description: TODO(App数据库操作类) 
* @author 康良涛 
* @date 2016年12月3日 下午3:57:07 
*
 */
public class KangSQLiteOpenHelper extends SQLiteOpenHelper {
    public KangSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	//db.execSQL("DROP TABLE loginData");
        db.execSQL("create TABLE loginData(userName TEXT PRIMARY KEY NOT NULL,userPassword TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
