package com.sleticalboy.doup.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.sleticalboy.doup.bean.weather.DaoMaster;

import org.greenrobot.greendao.AbstractDaoSession;

/**
 * Created on 18-3-4.
 *
 * @author sleticalboy
 * @version 1.0
 * @description 数据库管理类, 必须先在应用的 Application 类中进行初始化后才能使用
 * http://www.processon.com/diagraming
 */
public final class DBController {

    private static AbstractDaoSession sDaoSession;

    public static DBController getInstance() {
        synchronized (Holder.CONTROLLER) {
            return Holder.CONTROLLER;
        }
    }

    public static AbstractDaoSession getDaoSession() {
        return sDaoSession;
    }

    /**
     * 默认初始化数据库操作
     *
     * @param context
     * @param encrypted 是否对数据库进行加密
     */
    public void defaultInitDB(Context context, boolean encrypted) {
        init(new DaoMaster.DevOpenHelper(context, getName(encrypted)), encrypted);
    }

    private void init(DaoMaster.OpenHelper helper, boolean encrypted) {
        sDaoSession = new DaoMaster(
                encrypted
                        ? helper.getEncryptedReadableDb("super-secret")
                        : helper.getWritableDb()
        ).newSession();
    }

    @NonNull
    private String getName(boolean encrypted) {
        return encrypted ? "encrypt-db" : "common-db";
    }

    /**
     * 如果想要自定义数据库升级脚本可使用次方法初始化数据库
     *
     * @param context
     * @param encrypted 是否对数据库进行加密
     * @see DBHelper#onUpgrade(SQLiteDatabase, int, int)
     */
    public void customInitDB(Context context, boolean encrypted) {
        init(new DBHelper(context, getName(encrypted)), encrypted);
    }

    private static final class Holder {
        private static final DBController CONTROLLER = new DBController();
    }
}
