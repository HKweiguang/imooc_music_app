package com.imooc.lib_base.service.login;

public class LoginPluginConfig {

    public static final String PLUGIN_NAME = "ft_login";

    public static final String PACKAGE_NAME = "com.imooc.ft_login";

    public static final String KEY_INTERFACE = PACKAGE_NAME + ".interface";

    /**
     * 插件对外暴露的页面
     */
    public static class PAGE {
        public static final String PAGE_LOGIN = PACKAGE_NAME + ".view.LoginActivity";
    }

    /**
     * 插件对外暴露的所有Action
     */
    public static class ACTION {
        public static final String KEY_DATA = PACKAGE_NAME + ".key.data";

        public static final String LOGIN_SUCCESS_ACTION = PACKAGE_NAME + ".action.LOGIN_SUCCESS";
    }
}
