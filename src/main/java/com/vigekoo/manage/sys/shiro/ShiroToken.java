package com.vigekoo.manage.sys.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author ljx
 * @Description:  (token)
 * @date 2017-6-23 15:07
 */
public class ShiroToken implements AuthenticationToken {

    private String token;

    public ShiroToken(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
