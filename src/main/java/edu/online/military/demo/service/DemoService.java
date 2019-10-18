package edu.online.military.demo.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class DemoService {
    public List<Record> getMessage(){
        String sql = "select * from student";
        return Db.find(sql);
    }
}
