package com.vigekoo.manage.utils;


import com.vigekoo.manage.core.response.moreMsg.ResponseMoreMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class functionUtil {
    private functionUtil(){}

    protected final static Logger log = LoggerFactory.getLogger(functionUtil.class);

    public static void echoJson(ResponseMoreMsg responseMoreMsg, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(responseMoreMsg.toJson());
            out.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) out.close();
        }
    }
}
