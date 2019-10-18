package edu.online.military.framework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;
import edu.online.military.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

public abstract class BaseController extends Controller{
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    public JSONObject getFetchReqParams(HttpServletRequest req){
        BufferedReader reader = null;
        JSONObject json = new JSONObject();
        try{
            StringBuilder sb = new StringBuilder();
            reader = req.getReader();
            char[] buff = new char[1024];
            int len;
            while((len = reader.read(buff)) != -1){
                sb.append(buff, 0, len);
            }
            if(sb.length() > 0){
                json = (JSONObject) JSON.parseObject(sb.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return json;
    }
}
