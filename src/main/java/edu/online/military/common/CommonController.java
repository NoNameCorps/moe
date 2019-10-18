package edu.online.military.common;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;
import edu.online.military.framework.BaseController;
import edu.online.military.utils.CommonUtil;

import java.io.*;

public class CommonController extends BaseController {
    /**
     * 文件上传公共方法
     */
    public void uploadFile() {
        String unitid = getPara("unitid");
        UploadFile file = getFile("file");
        File source = file.getFile();
        String fileName = file.getFileName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        fileName = CommonUtil.getUUID() + extension;
        JSONObject json = new JSONObject();
        try {
            FileInputStream fis = new FileInputStream(source);
            String targetDirStr = PropKit.get("ftp.path") + "/" + unitid;
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
            json.put("error", 0);
            json.put("url", targetDirStr + "/" + fileName);
            source.delete();
        } catch (FileNotFoundException e) {
            json.put("error", 1);
            json.put("message", "上传出现错误，请稍后再上传");
        } catch (IOException e) {
            json.put("error", 1);
            json.put("message", "文件写入服务器出现错误，请稍后再上传");
        }
        renderJson(json.toJSONString());

    }

}
