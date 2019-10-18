package edu.online.military.framework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserCache {
    private static Map<String, OnlineUserInfo> cookieIdToOnlineUserInfoMap =
            new ConcurrentHashMap<String, OnlineUserInfo>();

    public static Map<String, OnlineUserInfo> getCookieIdToOnlineUserInfoMap() {
        return cookieIdToOnlineUserInfoMap;
    }

}
