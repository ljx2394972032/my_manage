package com.vigekoo.manage.sys.redis;

import com.vigekoo.manage.cache.RedisUtils;
import com.vigekoo.manage.sys.entity.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ljx
 * @Description:  ()
 * @date 2017-7-27 16:11
 */
@Component
public class SysConfigRedis {

    private static final String NAME = "SysConfig:";

    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysConfig config) {
        if (config == null) {
            return;
        }

        String id = NAME + config.getId();
        redisUtils.set(id, config);

        String key = NAME + config.getKey();
        redisUtils.set(key, config);
    }

    public void delete(SysConfig config) {
        if (config == null) {
            return;
        }

        redisUtils.delete(NAME + config.getId());
        redisUtils.delete(NAME + config.getKey());
    }

    public SysConfig get(Object key) {
        return redisUtils.get(NAME + key, SysConfig.class);
    }

}
