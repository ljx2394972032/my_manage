package com.vigekoo.manage.utils;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {
    private GsonUtils() {
    }

    protected static final Logger log = LoggerFactory.getLogger(GsonUtils.class);

    /**
     * bean转json
     *
     * @param bean 实体Class
     **/
    public static String beanToJson(Object bean) {
        try {
            return new Gson().toJson(bean);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * json 转bean
     *
     * @param jsonStr json字符串
     * @param bean    实体Class
     **/
    public static <T> T jsonToBean(String jsonStr, Class<T> bean) {
        try {
            return new Gson().fromJson(jsonStr, bean);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 根据Key获取Json中的Value
     *
     * @param jsonStr json字符串
     * @param key     Key值
     **/
    public static String getValueByKey(String jsonStr, String key) {
        String value = null;
        try {
            value = new JSONObject(jsonStr).get(key).toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return value;
    }

    /**
     * 根据Key修改Json中的Value 或者添加K-V
     *
     * @param jsonStr json字符串
     * @param key     Key值
     **/
    public static String putKeyValue(String jsonStr, String key, String val) {
        JSONObject put = null;
        try {
            put = new JSONObject(jsonStr).put(key, val);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return put.toString();
    }

    /**
     * 获取Bean实体 List集合
     *
     * @param jsonStr
     * @param type    new TypeToken<List<T>>() {}.getType()
     **/
    public static <T> List<T> getBeanList(String jsonStr, Type type) {
        try {
            return new Gson().fromJson(jsonStr, type);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}