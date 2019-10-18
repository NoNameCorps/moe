package edu.online.military.personalInfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import edu.online.military.framework.CommonController;
import edu.online.military.framework.OnlineUserInfo;
import edu.online.military.personalInfo.service.impl.PersonInfoServiceImpl;
import edu.online.military.personalInfo.service.IPersonInfoService;

import java.util.List;

public class PersonalInfoController extends CommonController {
    private IPersonInfoService personInfoService = new PersonInfoServiceImpl();

    public void saveInfo() {
        JSONObject params = getFetchReqParams(this.getRequest());
        boolean ret = personInfoService.saveInfo(params);
        String info = ret ? "操作成功" : "保存失败";
        setAttr("result", ret);
        setAttr("info", info);
        renderJson();
    }

    public void queryInfo() {
        OnlineUserInfo userInfo =  (OnlineUserInfo)getSession().getAttribute("operatorVo");
        String cardId = userInfo.getCard_id();
        Record student = personInfoService.queryInfo(cardId);
        setAttr("result",true);
        setAttr("info","操作成功");
        setAttr("personalinfo",student);
        renderJson();
    }

    public void queryAllProvince(){
        List<Record> provinceList = personInfoService.queryAllProvince();
        setAttr("result", true);
        setAttr("info","操作成功");
        setAttr("provincelist", provinceList);
        renderJson();
    }

    public void queryAllCity(){
        List<Record> cityList = personInfoService.queryAllCity();
        setAttr("result", true);
        setAttr("info","操作成功");
        setAttr("citylist", cityList);
        renderJson();
    }
}
