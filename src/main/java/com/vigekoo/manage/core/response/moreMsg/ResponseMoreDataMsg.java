package com.vigekoo.manage.core.response.moreMsg;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Author : ljx
 * @Description : 多个date返回
 * @Date : 2018/1/23 13:13
 */
public class ResponseMoreDataMsg implements Serializable {

    private Integer code;
    private String msg_cn;
    private String msg_en;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsgCN() {
        return msg_cn;
    }

    public void setMsgCN(String msg_cn) {
        this.msg_cn = msg_cn;
    }

    public String getMsgEN() {
        return msg_en;
    }

    public void setMsgEN(String msg_en) {
        this.msg_en = msg_en;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    //多个date
    public static Builder SUCC() {
        return new Builder(ResponseCodeMoreMsg.SUCC);
    }

    public static Builder SUCC(String msg_cn, String msg_en) {
        return new Builder(200, msg_cn, msg_en);
    }

    public static Builder SYS_ERR() {
        return new Builder(ResponseCodeMoreMsg.SYS_ERR);
    }

    public static Builder ERR(ResponseCodeMoreMsg responseCode) {
        return new Builder(responseCode);
    }

    public static Builder ERR(Integer code, String msg_cn, String msg_en) {
        return new Builder(code, msg_cn, msg_en);
    }

    public static Builder ERR(String msg_cn, String msg_en) {
        return new Builder(500, msg_cn, msg_en);
    }

    public ResponseMoreDataMsg(Builder builder) {
        this.code = builder.code;
        this.msg_cn = builder.msg_cn;
        this.msg_en = builder.msg_en;
        this.data = builder.data;
    }

    public static class Builder {

        private Integer code;
        private String msg_cn;
        private String msg_en;
        private Object data;

        HashMap<String, Object> map = new HashMap();

        public Builder(ResponseCodeMoreMsg responseCode) {
            this.code = responseCode.getCode();
            this.msg_cn = responseCode.getMsgCN();
            this.msg_en = responseCode.getMsgEN();
        }

        public Builder(Integer code, String msg_cn, String msg_en) {
            this.code = code;
            this.msg_cn = msg_cn;
            this.msg_en = msg_en;
        }

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder message(String msg_cn, String msg_en) {
            this.msg_cn = msg_cn;
            this.msg_en = msg_en;
            return this;
        }

        public Builder data(Object data) {
            map.put("data", data);
            this.data = data;
            return this;
        }

        public Builder data(String str, Object data) {
            map.put(str, data);
            this.data = map;
            return this;
        }

        public ResponseMoreDataMsg build() {
            return new ResponseMoreDataMsg(this);
        }
    }
}