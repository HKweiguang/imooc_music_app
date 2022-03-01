package com.imooc.lib_base.service.mine;

public class MinePluginConfig {

    public static final String PLUGIN_NAME = "ft_mine";

    public static final String PACKAGE_NAME = "com.imooc.ft_mine";

    public static final String KEY_INTERFACE = PACKAGE_NAME + ".interface";

    /**
     * 插件对外暴露的页面
     */
    public static class PAGE {
        public static final String PAGE_MINE = PACKAGE_NAME + ".view.MineFragment";
    }
}
