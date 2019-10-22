package edu.online.military.login.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import edu.online.military.framework.BaseController;
import edu.online.military.framework.OnlineUserInfo;
import edu.online.military.login.service.ILoginService;
import edu.online.military.login.service.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class LoginController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private ILoginService loginService = new LoginServiceImpl();

    public void login() {
        JSONObject params = getFetchReqParams(this.getRequest());
        String userId = params.getString("card_id");
        String password = params.getString("password");
        Record user = loginService.getUserByCardId(userId);
        //校验用户名密码
        //若不通过，返回
        if (user == null) {
            setAttr("result", false);
            setAttr("info", "用户不存在！");
        } else if (!user.get("password").equals(password)) {
            setAttr("result", false);
            setAttr("info", "密码错误！");
            //若通过
            //cookie  session
        } else {
            OnlineUserInfo userInfo = new OnlineUserInfo();
            userInfo.setCard_id(user.getStr("card_id"));
            userInfo.setClientIp(getIpAddr(getRequest()));
            userInfo.setId(user.getStr("id"));
            setSessionAttr("operatorVo",userInfo);
            getSession().setMaxInactiveInterval(30*60);
            //getSession().setMaxInactiveInterval(10);
            setAttr("result", true);
            setAttr("info", "登录成功！");
            setAttr("name", user.get("name"));
        }
        renderJson();
    }
    private String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("获取用户访问客户端ip异常：" + e.fillInStackTrace());
                    return "";
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
