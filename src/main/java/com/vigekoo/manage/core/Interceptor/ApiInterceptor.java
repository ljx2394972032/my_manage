package com.vigekoo.manage.core.Interceptor;

import com.vigekoo.manage.cache.RedisUtils;
import com.vigekoo.manage.constants.CommonConstant;
import com.vigekoo.manage.core.response.moreMsg.ResponseCodeMoreMsg;
import com.vigekoo.manage.core.response.moreMsg.ResponseMoreMsg;
import com.vigekoo.manage.core.sessionStore.UserSessionStore;
import com.vigekoo.manage.utils.StringUtil;
import com.vigekoo.manage.utils.functionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie,token");
        //获取token
        String token = request.getHeader(CommonConstant.TOKEN);
        if (StringUtil.isBlank(token)) {
            functionUtil.echoJson(ResponseMoreMsg.ERR(ResponseCodeMoreMsg.TOKEN_MISS).build(), response);
            return false;
        }
        //获取缓存中的 userId
        String userId = redisUtils.get(CommonConstant.TOKEN_CAHCE + token);
        if (userId == null) {
            functionUtil.echoJson(ResponseMoreMsg.ERR(ResponseCodeMoreMsg.NOT_LOGIN).build(), response);
            return false;
        }
        if (redisUtils.get(CommonConstant.FROZEN_ID + Long.valueOf(userId)) != null) {
            functionUtil.echoJson(ResponseMoreMsg.ERR(ResponseCodeMoreMsg.FROZEN).build(), response);
            return false;
        }
        UserSessionStore.setUserId(Long.valueOf(userId));
        return true;
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
