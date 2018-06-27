package com.vigekoo.manage.sys.redis;

import com.vigekoo.manage.cache.RedisUtils;
import com.vigekoo.manage.sys.entity.SysUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ljx
 * @Description:  ()
 * @date 2017-7-27 16:30
 */
@Component
public class SysUserTokenRedis {

    private static final String NAME = "SysUserToken:";

    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysUserToken userToken) {
        if (userToken == null) {
            return;
        }

        String userId = NAME + userToken.getUserId();
        redisUtils.set(userId, userToken);

        String token = NAME + userToken.getToken();
        redisUtils.set(token, userToken);
    }

    public void delete(SysUserToken userToken) {
        if (userToken == null) {
            return;
        }

        redisUtils.delete(NAME + userToken.getUserId());
        redisUtils.delete(NAME + userToken.getToken());
    }

    public SysUserToken get(Object key) {
        return redisUtils.get(NAME + key, SysUserToken.class);
    }

}
