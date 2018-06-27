package com.vigekoo.manage.core.response.oneMsg;

/**
 * Created by ASUS on 2017/8/21.
 */
public enum ResponseCode {

    SYS_ERR(500, "系统错误"),
    SUCC(200, "成功"),

    PARAMERROR(501, "参数不合法"),
    EMAIL_ERR(502, "邮箱格式不正确");

    private Integer code;
    private String msg;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
