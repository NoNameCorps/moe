package edu.online.military.personalInfo.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import edu.online.military.utils.CommonUtil;

import java.util.List;

public class PersonInfoDao {
    public boolean updateInfo(Record student){
        /*if(CommonUtil.isBlank(student.get("id"))){
            student.set("id",CommonUtil.getUUID());
            return Db.save("student",student);
        }*/
        return Db.update("student",student);
    }

    public Record queryInfo(String cardId){
        String sql = "select * from student t where t.card_id=?";
        return Db.findFirst(sql,cardId);
    }

    public List<Record> queryAllProvince(){
        String sql = "select * from provinces t where t.leveltype=1";
        return Db.find(sql);
    }

    public List<Record> queryAllCity(){
        String sql = "select t.* from provinces t  where t.leveltype=2";
        return Db.find(sql);
    }
}
