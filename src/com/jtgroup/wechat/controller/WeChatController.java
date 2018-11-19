package com.jtgroup.wechat.controller;

import com.jfinal.core.Controller;
import com.jtgroup.wechat.utils.CheckoutUtil;

/**  
 * @Title:  WeChatController.java   
 * @Description:    TODO(微信基础配置)   
 * @author: 
 * @date:   2018年11月13日 
 * @Copyright: 2018 guochuang
 */
public class WeChatController extends Controller {

	/**
	 * 微信服务器认证
	 * @return
	 */
	public void  oauth(){
		  String signature = this.getPara("signature");
          // 时间戳
          String timestamp = this.getPara("timestamp");
          // 随机数
          String nonce = this.getPara("nonce");
          // 随机字符串
          String echostr = this.getPara("echostr");
          if(!isParaBlank("signature")&& CheckoutUtil.checkSignature(signature, timestamp, nonce)){
        	  renderText(echostr);
          }else{
        	  renderText("faild");
          }
	}
	
	/**
	 * 微信服务器认证
	 * @return
	 */
	public void  bigdataOauth(){
		  String signature = this.getPara("signature");
          // 时间戳
          String timestamp = this.getPara("timestamp");
          // 随机数
          String nonce = this.getPara("nonce");
          // 随机字符串
          String echostr = this.getPara("echostr");
          if(!isParaBlank("signature")&& CheckoutUtil.checkSignature(signature, timestamp, nonce)){
        	  renderText(echostr);
          }else{
        	  renderText("faild");
          }
	}
	
	public void text(){
		renderText("success");
	}
}
