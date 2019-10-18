package edu.online.military.subject.service.impl;

import com.jfinal.plugin.activerecord.Record;
import edu.online.military.subject.dao.SubjectDao;
import edu.online.military.subject.service.ISubjectService;

import java.util.List;

public class SubjectServiceImpl implements ISubjectService {
    private SubjectDao subjectDao = new SubjectDao();

    /**
     * 查询学科下的所有类目
     * @param subjectId
     * @return
     */
    @Override
    public List<Record> querySubject(String subjectId) {
        return subjectDao.querySubject(subjectId);
    }

    /**
     * 查询课程视频url
     * @param courseId
     * @return
     */
    @Override
    public Record queryUrlOfCourse(String courseId) {
        return subjectDao.queryUrlOfCourse(courseId);
    }

    /**
     * 查询类目下的所有课程
     * @param subjectId
     * @return
     */
    @Override
    public List<Record> queryCourse(String subjectId, String majorId) {
        return subjectDao.queryCourse(subjectId, majorId);
    }

    /**
     * 查询学习进度
     * @param cardId
     * @return
     */
    @Override
    public List<Record> queryStudyProgress(String cardId) {
        return subjectDao.queryStudyProgress(cardId);
    }

    @Override
    public List<Record> queryMajor() {
        return subjectDao.queryMajor();
    }
}
