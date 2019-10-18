package edu.online.military.subject.service;

import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public interface ISubjectService {

    List<Record> querySubject(String subjectId);

    Record queryUrlOfCourse(String courseId);

    List<Record> queryCourse(String subjectId,String majorId);

    List<Record> queryStudyProgress(String cardId);

    List<Record> queryMajor();
}
