package com.example.herve.baseapp.app;

import com.herve.library.commonlibrary.BaseApplication;
import com.herve.library.orm_library.greendao.dao.DaoMaster;
import com.herve.library.orm_library.greendao.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created           :Herve on 2016/10/23.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/23
 * @ projectName     :BaseApp
 * @ version
 */
public class App extends BaseApplication {
  private static App app;
  private DaoSession daoSession;

  @Override
  public void onCreate() {
    super.onCreate();
    /*GreenDao数据库*/
    setDatabase();
  }

  public static App getApp() {
    return app;
  }

  /**
   * 设置greenDao
   */
  private void setDatabase() {
    // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
    // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
    // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
    // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
    // regular SQLite database
    DaoMaster.DevOpenHelper helper =
        new DaoMaster.DevOpenHelper(this, "GreenDao-db");
    Database db = helper.getWritableDb();
    //db = mHelper.getReadableDatabase().delete();
    //db = mHelper.getReadableDb().execSQL();
    // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
    daoSession = new DaoMaster(db).newSession();
    //QueryBuilder.LOG_SQL = true;
    // 输出带有具体数值的sql日志
    //QueryBuilder.LOG_VALUES = true;
  }

  public DaoSession getDaoSession() {
    return daoSession;
  }
}
