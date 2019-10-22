package edu.online.military.personalInfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import edu.online.military.framework.BaseController;
import edu.online.military.framework.OnlineUserInfo;
import edu.online.military.personalInfo.service.impl.PersonInfoServiceImpl;
import edu.online.military.personalInfo.service.IPersonInfoService;
import edu.online.military.utils.SeforgeFtpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PersonalInfoController extends BaseController {
    private IPersonInfoService personInfoService = new PersonInfoServiceImpl();
    private static final Logger log = LoggerFactory.getLogger(PersonalInfoController.class);

    public void updateInfo() throws FileNotFoundException {
        JSONObject params = getFetchReqParams(this.getRequest());
        String pic = params.getString("pic");
        if(StrKit.notBlank(pic)){//带有图片地址，需判断是否有修改，
            String targetDirStr = "/avatar/" ;
            String fileName = pic.substring(pic.lastIndexOf("/")+1);
            File targetDir = new File(PathKit.getWebRootPath() + targetDirStr);
            File target = new File(targetDir, fileName);
            if (!target.exists()) {//已上传，无修改
            }else{//修改过，上传新的
                FileInputStream fis = new FileInputStream(target);
                Boolean uploadRet = SeforgeFtpUtils.uploadFile(PropKit.get("ftp.path"),PropKit.getInt("ftp.port"),PropKit.get("ftp.username"),PropKit.get("ftp.password"),targetDirStr,fileName,fis);
                if(uploadRet){
                    target.delete();//上传成功，删除临时文件
                }else {
                    log.error("头像上传失败！");
                }
            }
        }else{//未传图片地址，不必上传ftp

        }
        boolean ret = personInfoService.updateInfo(params);
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
