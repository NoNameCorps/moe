package edu.online.military.subject.dao;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class SubjectDao {
    public List<Record> querySubject(String subjectId){
        String sql = "SELECT * from `subject` t where t.parentid=?";
        return Db.find(sql, subjectId);
    }

    public Record queryUrlOfCourse(String courseId){
        String sql = "select t.url from course t where t.course_id=?";
        return Db.queryFirst(sql,courseId);
    }

    public List<Record> queryCourse(String subjectId,String majorId){
        String sql = "select * from course t where t.subject_id=?";
        if(StrKit.notBlank(majorId)){
            sql = "select * from course t where t.subject_id in(select t.subject_id from `subject` t where t.parentid=?)";
            return Db.find(sql,majorId);
        }else{
            return Db.find(sql,subjectId);
        }
    }

    public List<Record> queryStudyProgress(String cardId){
        String sql = "SELECT t.*,s.subject_name from progress t LEFT JOIN `subject` s on t.subject_id=s.subject_id where t.card_id=?";
        return Db.find(sql,cardId);
    }

    public List<Record> queryMajor(){
        String sql = "SELECT t.* from `subject` t where t.nlevel=1";
        return Db.find(sql);
    }
}
