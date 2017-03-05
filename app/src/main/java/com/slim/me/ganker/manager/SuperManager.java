package com.slim.me.ganker.manager;


/**
 * Created by Slim on 2017/3/5.
 */

public class SuperManager {

    public static final int API_MANAGER = 0;
    public static final int DATABASE_MANAGER = 1;

    private static final int MANAGER_COUNT = DATABASE_MANAGER + 1;

    private static IManager[] sManagers = new IManager[MANAGER_COUNT];

    public static synchronized IManager getAppManager(int type) {
        if(type < 0 || type >= MANAGER_COUNT) {
            throw new IllegalArgumentException("wrong manager type:" + type);
        }

        IManager manager = sManagers[type];
        if(manager == null) {
            switch (type) {
                case API_MANAGER:
                    manager = new ApiManager();
                    break;
                case DATABASE_MANAGER:
                    manager = new DatabaseManager();
                    break;
                default:
                    throw new IllegalArgumentException("wrong manager type:" + type);
            }
            manager.onInit();
            sManagers[type] = manager;
        }
        return manager;
    }

}
