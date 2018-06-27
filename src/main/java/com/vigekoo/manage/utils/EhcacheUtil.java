package com.vigekoo.manage.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author : ljx
 * @Description : ehcache 缓存工具类
 * cacheName在ehcache.xml中配置
 * @Date : 2018/3/1 17:01
 */
public class EhcacheUtil {
    private EhcacheUtil(){}

    protected static final Logger log = LoggerFactory.getLogger(EhcacheUtil.class);

    static String configfile = "ehcache/ehcache.xml";

    static CacheManager manager = CacheManager.create(EhcacheUtil.class.getClassLoader().getResourceAsStream(configfile));

    public static Object get(String cacheName, Object key) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            Element element = cache.get(key);
            if (element != null) {
                return element.getObjectValue();
            }
        }
        return null;
    }

    public static void put(String cacheName, Object key, Object value) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            cache.put(new Element(key, value));
        }
    }

    public static void removeAll(String cacheName) {
        try {
            manager.getCache(cacheName).removeAll();
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        }
    }

    public static boolean remove(String cacheName, Object key) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            return cache.remove(key);
        }
        return false;
    }
}
