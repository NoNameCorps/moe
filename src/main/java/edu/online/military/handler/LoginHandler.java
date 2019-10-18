package edu.online.military.handler;

import com.jfinal.handler.Handler;
import edu.online.military.framework.OnlineUserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandler extends Handler{
    @Override
    public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean[] booleans) {
        if(s.endsWith("login")){
            if(next != null){
                next.handle(s, httpServletRequest, httpServletResponse, booleans);
            }
        }else{
            OnlineUserInfo userInfo = (OnlineUserInfo)httpServletRequest.getSession().getAttribute("operatorVo");
            if (userInfo == null) {
                httpServletResponse.setStatus(210);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("utf-8");
                booleans[0] = true;
            }else {
                if(next != null){
                    next.handle(s, httpServletRequest, httpServletResponse, booleans);
                }
            }
        }

    }
}
