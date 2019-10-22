package edu.online.military.personalInfo.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public interface IPersonInfoService {

    boolean updateInfo(JSONObject param);

    Record queryInfo(String cardId);

    List<Record> queryAllProvince();

    List<Record> queryAllCity();
}
