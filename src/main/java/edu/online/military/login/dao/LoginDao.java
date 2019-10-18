package edu.online.military.login.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class LoginDao {
    public Record getUserByCardId(String cardId){
        String sql = "select * from student t where t.card_id=?";
        return Db.findFirst(sql,cardId);
    }
}
