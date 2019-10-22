package edu.online.military;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import edu.online.military.common.CommonController;
import edu.online.military.demo.controller.DemoController;
import edu.online.military.handler.LoginHandler;
import edu.online.military.login.controller.LoginController;
import edu.online.military.personalInfo.controller.PersonalInfoController;
import edu.online.military.subject.controller.SubjectController;


public class MoeConfig extends JFinalConfig {
    public static void main(String[] args) {
        JFinal.start("web", 8299, "/", 5);
    }
    @Override
    public void configConstant(Constants me) {
        PropKit.use("moe.properties");
        me.setDevMode(PropKit.getBoolean("devMode", false));
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/MOE/logon", LoginController.class);
        me.add("/MOE/information", PersonalInfoController.class);
        me.add("/MOE/home", SubjectController.class);
        me.add("/MOE/common", CommonController.class);
       /* me.add("/logon", LoginController.class);
        me.add("/information", PersonalInfoController.class);
        me.add("/home", SubjectController.class);
        me.add("/common", CommonController.class);*/
    }

    @Override
    public void configEngine(Engine engine) {

    }

    @Override
    public void configPlugin(Plugins me) {
// 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        me.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        me.add(arp);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    @Override
    public void configHandler(Handlers handlers) {
        handlers.add(new LoginHandler());
    }
}
