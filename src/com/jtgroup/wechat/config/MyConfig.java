package com.jtgroup.wechat.config;

import java.util.Properties;




import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.template.Engine;
import com.jtgroup.wechat.controller.WeChatController;
import com.jtgroup.wechat.model.UserDao;

public class MyConfig extends JFinalConfig {

	/**
	 * 
	 * @return
	 */
	@Override
	public void configConstant(Constants me) {
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public void configEngine(Engine arg0) {

	}

	/**
	 * 
	 * @return
	 */
	@Override
	public void configHandler(Handlers arg0) {

	}

	/**
	 * 
	 * @return
	 */
	@Override
	public void configInterceptor(Interceptors inter) {

	}

	/**
	 * 
	 * @return
	 */
	@Override
	public void configPlugin(Plugins p) {
		Properties prop = loadPropertyFile("config.properties");
		C3p0Plugin cp = new C3p0Plugin(getProperty("jdbc_url"),
				getProperty("jdbc_username"), getProperty("jdbc_password"));
		p.add(cp);
		ActiveRecordPlugin ap = new ActiveRecordPlugin(cp);
		p.add(ap);

	ap.addMapping("t_user", UserDao.class);
		
	}

	/**
	 * controller请求
	 * 
	 * @return
	 */
	@Override
	public void configRoute(Routes r) {
		// r.add("/",DefaultsController.class);
        r.add("wechat",WeChatController.class);
	
	}

}
