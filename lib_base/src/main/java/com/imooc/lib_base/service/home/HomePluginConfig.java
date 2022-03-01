package com.imooc.lib_base.service.home;

public class HomePluginConfig {

    public static final String PLUGIN_NAME = "ft_home";

    public static final String PACKAGE_NAME = "com.imooc.ft_home";

    public static final String KEY_INTERFACE = PACKAGE_NAME + ".interface";

    /**
     * 插件对外暴露的页面
     */
    public static class PAGE {
        public static final String PAGE_HOME = PACKAGE_NAME + ".view.home.HomeActivity";
    }
}
