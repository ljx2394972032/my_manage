package com.vigekoo.manage.core.response.moreMsg;

/**
 * Created by ASUS on 2017/8/21.
 */
public enum ResponseCodeMoreMsg {

    SYS_ERR(500, "系统错误", "System error!"),
    SUCC(200, "成功", "Success!"),

    TOKEN_MISS(1001, "缺少token", "lack of token!"),
    NOT_LOGIN(1002, "请先登录", "Please login first!"),
    FROZEN(1003, "账号被冻结", "Account is frozen!"),

    PARAMERROR(501, "参数错误", "Parameter error!"),

    EMAIL_ERR(601, "邮箱格式不正确", "Incorrect mailbox format!"),
    PHONE_ERR(602, "手机号码格式不正确", "Mobile phone number is not correct!");

    private Integer code;
    private String msg_cn;
    private String msg_en;

    ResponseCodeMoreMsg(Integer code, String msg_cn, String msg_en) {
        this.code = code;
        this.msg_cn = msg_cn;
        this.msg_en = msg_en;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsgCN() {
        return msg_cn;
    }

    public String getMsgEN() {
        return msg_en;
    }
}
