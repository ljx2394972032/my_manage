package com.vigekoo.manage.utils;

import java.util.UUID;

public class MyUUID {
    private MyUUID(){}
    /**
     * 带线的UUID
     *
     * @return
     */
    public static String lineUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 不带线的UUID，32位
     *
     * @return
     */
    public static String nolineUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
