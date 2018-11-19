package com.jtgroup.wechat.controller;

import com.jfinal.core.Controller;
import com.jtgroup.wechat.utils.CheckoutUtil;

/**  
 * @Title:  WeChatController.java   
 * @Description:    TODO(΢�Ż�������)   
 * @author: 
 * @date:   2018��11��13�� 
 * @Copyright: 2018 guochuang
 */
public class WeChatController extends Controller {

	/**
	 * ΢�ŷ�������֤
	 * @return
	 */
	public void  oauth(){
		  String signature = this.getPara("signature");
          // ʱ���
          String timestamp = this.getPara("timestamp");
          // �����
          String nonce = this.getPara("nonce");
          // ����ַ���
          String echostr = this.getPara("echostr");
          if(!isParaBlank("signature")&& CheckoutUtil.checkSignature(signature, timestamp, nonce)){
        	  renderText(echostr);
          }else{
        	  renderText("faild");
          }
	}
	
	/**
	 * ΢�ŷ�������֤
	 * @return
	 */
	public void  bigdataOauth(){
		  String signature = this.getPara("signature");
          // ʱ���
          String timestamp = this.getPara("timestamp");
          // �����
          String nonce = this.getPara("nonce");
          // ����ַ���
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
