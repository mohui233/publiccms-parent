package org.publiccms.common.constants;

import org.springframework.context.ApplicationContext;

/**
 *
 * CommonConstants
 * 
 */
public class CommonConstants {

    /**
     * CMS文件路径
     */
    public static String CMS_FILEPATH;

    /**
     * 安装锁
     */
    public static final String INSTALL_LOCK_FILENAME = "/install.lock";
    
    /**
     * 配置文件
     */
    public static final String CMS_CONFIG_FILE = "cms.properties";

    /**
     * 应用上下文
     */
    public static ApplicationContext applicationContext;

    /**
     * @return
     */
    public static final String getDefaultPage() {
        return "index.html";
    }

    /**
     * @return
     */
    public static final String getSessionUser() {
        return "PUBLICCMS_USER";
    }

    /**
     * @return
     */
    public static final String getSessionUserTime() {
        return "PUBLICCMS_USER_TIME";
    }

    /**
     * @return
     */
    public static final String getSessionAdmin() {
        return "PUBLICCMS_ADMIN";
    }

    /**
     * @return
     */
    public static final String getCookiesUser() {
        return "PUBLICCMS_USER";
    }

    /**
     * @return
     */
    public static final String getCookiesUserSplit() {
        return "##";
    }

    /**
     * @return
     */
    public static final String getDefaultPageBreakTag() {
        return "_page_break_tag_";
    }

    /**
     * @return
     */
    public static final String getXPowered() {
        return "X-Powered-PublicCMS";
    }
}