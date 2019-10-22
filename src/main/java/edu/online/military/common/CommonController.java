package edu.online.military.common;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;
import edu.online.military.framework.BaseController;
import edu.online.military.utils.CommonUtil;
import edu.online.military.utils.SeforgeFtpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class CommonController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SeforgeFtpUtils.class);

    /**
     * 文件上传公共方法
     */
    public void uploadFile() {
        String studentId = getPara("id");
        String dir = getPara("dir");
        UploadFile file = getFile("file");
        File source = file.getFile();
        String fileName = file.getFileName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        fileName = studentId + extension;
        JSONObject json = new JSONObject();
        try {
            FileInputStream fis = new FileInputStream(source);
            String targetDirStr = "/" + dir + "/" ;
            File targetDir = new File(PathKit.getWebRootPath() + targetDirStr);
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            File target = new File(targetDir, fileName);
            if (!target.exists()) {
                target.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(target);
            byte[] bts = new byte[300];
            int num = 0;
            while ((num = fis.read(bts, 0, bts.length)) != -1) {
                fos.write(bts, 0, num);
            }
            fos.close();
            fis.close();
            json.put("result", true);
            log.error( PathKit.getWebRootPath() + targetDirStr + fileName);
            log.error( PathKit.getWebRootPath());
            json.put("url", PathKit.getWebRootPath() + targetDirStr + fileName);
            json.put("pic", PropKit.get("http.path")+targetDirStr + fileName);
            json.put("info", "上传成功");
            source.delete();
        } catch (FileNotFoundException e) {
            json.put("result", false);
            json.put("info", "上传出现错误，请稍后再上传");
        } catch (IOException e) {
            json.put("result", false);
            json.put("info", "文件写入服务器出现错误，请稍后再上传");
        }
        renderJson(json.toJSONString());
    }

}
