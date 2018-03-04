package com.sleticalboy.doup.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.sleticalboy.doup.bean.weather.DaoMaster;

/**
 * Created on 18-3-4.
 *
 * @author sleticalboy
 * @version 1.0
 * @description 数据库升级管理类，可选使用[如果先要自己编写数据库升级脚本，可使用此类]，非必须
 * @see DBController#customInitDB(Context, boolean)
 */
public final class DBHelper extends DaoMaster.OpenHelper {

    public DBHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        // 自定义升级脚本
    }
}
