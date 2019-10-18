package edu.online.military.demo.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import edu.online.military.demo.service.DemoService;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.UUID;


public class DemoController extends Controller {

    private DemoService service = new DemoService();
    public void getMessage(){
       String id =  this.getPara("id");
        System.out.println(id);
        List<Record> list = service.getMessage();

        String cookieValue = UUID.randomUUID().toString().replaceAll("-", "");
        Cookie cookie = new Cookie("cookiekey", cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(-1);//关闭浏览器失效
        getResponse().addCookie(cookie);
        setAttr("list",list);
        setAttr("success",true);
        renderJson();
    }
}
