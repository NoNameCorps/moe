package edu.online.military.personalInfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import edu.online.military.personalInfo.dao.PersonInfoDao;
import edu.online.military.personalInfo.service.IPersonInfoService;
import edu.online.military.utils.CommonUtil;

import java.util.List;
import java.util.Map;

public class PersonInfoServiceImpl implements IPersonInfoService {
    private PersonInfoDao personInfoDao = new PersonInfoDao();

    @Override
    public boolean saveInfo(JSONObject param) {
        Record student = transJSONToRecord(param);
        return personInfoDao.saveInfo(student);
    }

    @Override
    public Record queryInfo(String cardId) {
        return personInfoDao.queryInfo(cardId);
    }

    @Override
    public List<Record> queryAllProvince() {
        return personInfoDao.queryAllProvince();
    }

    @Override
    public List<Record> queryAllCity() {
        return personInfoDao.queryAllCity();
    }


    private Record transJSONToRecord(JSONObject param){
        Record record = new Record();
        for(Map.Entry<String, Object> entry : param.entrySet()){
            String key = entry.getKey();
            String value = (String)entry.getValue();
            record.set(key,value);
        }
        return record;
    }
}
