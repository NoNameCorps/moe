package edu.online.military.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CommonUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    public static Boolean isBlank(String s){
        if(s==null){
            return true;
        }else if(s.trim().isEmpty()){
            return true;
        }
        return false;
    }



}
