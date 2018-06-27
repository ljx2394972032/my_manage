package com.vigekoo.manage.core.response.oneMsg;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Author : ljx
 * @Description : 多个date返回
 * @Date : 2018/1/23 13:13
 */
public class ResponseMoreData implements Serializable {

    private Integer code;
    private String msg;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
        return new Builder(ResponseCode.SUCC);
    }

    public static Builder SUCC(String msg) {
        return new Builder(200, msg);
    }

    public static Builder SYS_ERR() {
        return new Builder(ResponseCode.SYS_ERR);
    }

    public static Builder ERR(ResponseCode responseCode) {
        return new Builder(responseCode);
    }

    public static Builder ERR(Integer code, String msg) {
        return new Builder(code, msg);
    }

    public static Builder ERR(String msg) {
        return new Builder(500, msg);
    }

    public ResponseMoreData(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = builder.data;
    }

    public static class Builder {

        private Integer code;
        private String msg;
        private Object data;

        HashMap<String, Object> map = new HashMap();

        public Builder(ResponseCode responseCode) {
            this.code = responseCode.getCode();
            this.msg = responseCode.getMsg();
        }

        public Builder(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder message(String msg) {
            this.msg = msg;
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

        public ResponseMoreData build() {
            return new ResponseMoreData(this);
        }
    }
}