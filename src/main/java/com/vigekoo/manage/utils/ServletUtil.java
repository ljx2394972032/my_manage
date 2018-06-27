package com.vigekoo.manage.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by ljx on 16/12/18.
 */
public class ServletUtil {

    protected static final Logger log = LoggerFactory.getLogger(ServletUtil.class);

    //得到参数
    //得到IP地址
    public static String getIp() {
        HttpServletRequest request = getRequest();
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error(e.getMessage());
                }
                if(inet!=null) {
                    ipAddress = inet.getHostAddress();
                }
                else{
                    return "unknown";
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(',') > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(','));
            }
        }
//        if (ipAddress.contains(":")) ipAddress = ipAddress.substring(0, ipAddress.lastIndexOf(":"));
        return ipAddress;
    }


    //得到request
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    //得到response
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    //设置Session
    public static void setSession(String name, Object obj) {
        HttpServletRequest request = getRequest();
        request.getSession().setAttribute(name, obj);
    }

    //获取Session
    public static Object getSession(String name) {
        HttpServletRequest request = getRequest();
        return request.getSession().getAttribute(name);
    }

    //得到参数
    public static Object getParam(String name) {
        HttpServletRequest request = getRequest();
        return request.getParameter(name);
    }


    //设置Attribute参数
    public static void setRequest(String name, Object value) {
        HttpServletRequest request = getRequest();
        request.setAttribute(name, value);
    }

    //得到Attribute参数
    public static Object getAttribute(String name) {
        HttpServletRequest request = getRequest();
        return request.getAttribute(name);
    }

    public static void sendText(String text) {
        Writer writer = null;
        try {
            HttpServletResponse response = getResponse();
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static boolean isMultipartContent(HttpServletRequest request) {
        if (!"post".equalsIgnoreCase(request.getMethod())) {
            return false;
        }

        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }


    private ServletUtil() {
        throw new AssertionError();
    }

    /**
     * post请求
     *
     * @param urlstr
     * @param postdata
     * @return
     */
    public static String post(String urlstr, String postdata) {
        String ret;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(postdata.getBytes("UTF-8"));
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuilder sb = new StringBuilder("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
            ret = sb.toString();
        } catch (Exception e) {
            ret = null;
        }
        return ret;
    }

    /**
     * GET 请求
     *
     * @param urlstr
     * @return
     */
    public static String get(String urlstr) {
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
        return result.toString();
    }
}
