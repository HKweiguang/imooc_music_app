package com.imooc.lib_audio.mediaplayer.db;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;

import com.imooc.lib_audio.app.AudioHelper;
import com.imooc.lib_audio.mediaplayer.model.AudioBean;
import com.imooc.lib_audio.mediaplayer.model.Favourite;

@SuppressLint("StaticFieldLeak")
public class GreenDaoHelper {

    private static final String DB_BAME = "music_db";
    // 数据库帮助类，创建、升级数据库
    private static DaoMaster.DevOpenHelper mHelper;
    // 最终创建好的数据库
    private static SQLiteDatabase mDb;
    // 管理数据库
    private static DaoMaster mDaoMaster;
    // 管理各种实体Dao,不让业务层拿到session直接去操作数据库，统一由此类提供方法
    private static DaoSession mDaoSession;

    /**
     * 设置greenDao
     */
    public static void initDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(AudioHelper.getContext(), DB_BAME);
        mDb = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * 添加感兴趣
     */
    public static void addFavourite(AudioBean bean) {
        FavouriteDao dao = mDaoSession.getFavouriteDao();
        Favourite favourite = new Favourite();
        favourite.setAudioId(bean.id);
        favourite.setAudioBean(bean);
        dao.insertOrReplace(favourite);
    }

    /**
     * 移除感兴趣
     */
    public static void removeFavourite(AudioBean audioBean) {
        FavouriteDao dao = mDaoSession.getFavouriteDao();
        dao.delete(selectFavourite(audioBean));
    }

    /**
     * 查找感兴趣
     */
    public static Favourite selectFavourite(AudioBean audioBean) {
        FavouriteDao dao = mDaoSession.getFavouriteDao();
        return dao.queryBuilder().where(FavouriteDao.Properties.AudioId.eq(audioBean.id)).unique();
    }
}
