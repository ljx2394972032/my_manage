package com.vigekoo.manage.core.sessionStore;


import com.vigekoo.manage.utils.ServletUtil;

public class UserSessionStore {

    private static final String USER_ID = "userId";

    private UserSessionStore() {
    }


    public static void setUserId(Long userId) {
        ServletUtil.setRequest(USER_ID, userId);
    }

    public static Long getUserId() {
        Long userId = (Long) ServletUtil.getAttribute(USER_ID);
        if (userId == null) {
            userId = 0L;
        }
        return userId;
    }
}
