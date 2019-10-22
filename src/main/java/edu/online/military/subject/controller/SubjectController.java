package edu.online.military.subject.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Record;
import edu.online.military.framework.BaseController;
import edu.online.military.framework.OnlineUserInfo;
import edu.online.military.subject.service.ISubjectService;
import edu.online.military.subject.service.impl.SubjectServiceImpl;

import java.util.List;

public class SubjectController extends BaseController {
    private ISubjectService subjectService = new SubjectServiceImpl();
    public void querySubject() {
        JSONObject params = getFetchReqParams(this.getRequest());
        String subjectId = params.getString("subject_id");
        List<Record> subjectList = subjectService.querySubject(subjectId);
        setAttr("result",true);
        setAttr("info","操作成功");
        setAttr("categorylist",subjectList);
        renderJson();
    }

    /**
     * 查询课程视频url
     */
    public void queryUrlOfCourse() {
        JSONObject params = getFetchReqParams(this.getRequest());
        String courseId = params.getString("course_id");
        Record record =  subjectService.queryUrlOfCourse(courseId);
        String url = record==null?"":record.getStr("url");
        setAttr("result",true);
        setAttr("info","操作成功");
        setAttr("url",url);
        renderJson();
    }

    /**
     * 查询类目下的所有课程
     */
    public void queryCourse() {
        JSONObject params = getFetchReqParams(this.getRequest());
        String subjectId = params.getString("subject_id");
        String majorId = params.getString("major_id");
        List<Record> courseList = subjectService.queryCourse(subjectId,majorId);
        setAttr("result",true);
        setAttr("info","操作成功");
        setAttr("courselist",courseList);
        renderJson();
    }

    /**
     * 查询学习进度
     */
    public void queryStudyProgress() {
        OnlineUserInfo userInfo =  (OnlineUserInfo)getSession().getAttribute("operatorVo");
        String cardId = userInfo.getCard_id();
        List<Record> progressList = subjectService.queryStudyProgress(cardId);
        setAttr("result",true);
        setAttr("info","操作成功");
        setAttr("progresslist",progressList);
        renderJson();
    }

    /**
     * 查询一级学科
     */
    public void queryMajor(){
        List<Record> marjorList = subjectService.queryMajor();
        setAttr("result",true);
        setAttr("info","操作成功");
        setAttr("majorlist",marjorList);
        renderJson();
    }
}
