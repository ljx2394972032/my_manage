package com.vigekoo.manage.constants;


public class CommonConstant {

    private CommonConstant(){}

    // 盐
    public static final String SALT = "#Vigekoo";

    // 请求头
    public static final String TOKEN = "token";
    // 缓存键
    public static final String TOKEN_CAHCE = "apiToken:";

    //被封号的用户id 缓存键，主要用于封号以后及时生效
    public static final String FROZEN_ID = "frozenId:";

}
