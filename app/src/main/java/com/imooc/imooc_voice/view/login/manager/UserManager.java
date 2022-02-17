package com.imooc.imooc_voice.view.login.manager;

import com.imooc.imooc_voice.view.login.user.User;

public class UserManager {

    private static volatile UserManager mInstance;

    private User mUser;

    public static UserManager getInstance() {
        if (mInstance == null) {
            synchronized (UserManager.class) {
                if (mInstance == null) {
                    mInstance = new UserManager();
                }
            }
        }

        return mInstance;
    }

    /**
     * 保存用户信息到内存
     *
     * @param user 用户信息
     */
    public void saveUser(User user) {
        mUser = user;
        saveLocal(user);
    }

    /**
     * 持久化用户信息
     *
     * @param user 用户信息
     */
    private void saveLocal(User user) {

    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public User getUser() {
        return mUser;
    }

    /**
     * 从本地获取
     *
     * @return 用户信息
     */
    private User getLocal() {
        return null;
    }

    /**
     * 是否登陆
     *
     * @return true-是; false-否
     */
    public boolean hasLogin() {
        return getUser() != null;
    }

    public void removeUser() {
        mUser = null;
        removeLocal();
    }

    /**
     * 从库中删除用户信息
     */
    private void removeLocal() {

    }
}
