package edu.online.military.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void addCookie(HttpServletResponse response,String cookieId){
        Cookie cookie = new Cookie("moe-cookieid", cookieId);
        cookie.setPath("/");
        cookie.setMaxAge(-1);//关闭浏览器失效
        response.addCookie(cookie);
    }
}
