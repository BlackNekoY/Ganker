package com.slim.me.ganker.manager;


import com.slim.me.ganker.App;
import com.slim.me.ganker.constant.Constants;
import com.slim.me.ganker.data.MeizhiData;
import com.slim.me.ganker.data.entity.DaoMaster;
import com.slim.me.ganker.data.entity.DaoSession;
import com.slim.me.ganker.data.entity.GankDao;
import com.slim.me.ganker.data.entity.Meizhi;
import com.slim.me.ganker.data.entity.MeizhiDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Slim on 2017/3/5.
 */
public class DatabaseManager implements IManager{

    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private Database mDatabase;
    private DaoSession mSession;

    @Override
    public void onInit() {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(App.sContext, Constants.DATABASE_NAME);
        mDatabase = mDevOpenHelper.getWritableDb();
        mSession = new DaoMaster(mDatabase).newSession();
    }

    public MeizhiDao getMeizhiDao() {
        return mSession.getMeizhiDao();
    }

    public GankDao getGankDao() {
        return mSession.getGankDao();
    }

}
