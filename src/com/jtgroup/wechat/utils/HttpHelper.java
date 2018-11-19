package com.jtgroup.wechat.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;




/**
 * 网络请求封装 HttpURLConnection
 * 
 * @author 宋保衡
 */
public class HttpHelper {

//	public static final String IP = "http://192.168.1.103:";
	public static final String IP = "http://www.wsslaw.cn:";

	public static final String APIPORT = "80/";
	
	public static final String FEEDBACK = IP + APIPORT + "zcapi/wxfeedback/addBusiness";//增加内容
	public static final String ADDUSER = IP + APIPORT + "zcapi/wxuser/addUser";//增加个人信息
//	public static final String ADDUSER = IP + APIPORT + "zcapi/wxuser/addUser";//查询个人信息


	
	private static final int CONNECTION_TIMEOUT = 1000 * 5; // Http连接超时时间
	private static final String ENCODING = "utf-8";// 编码格式
	private static HttpHelper httpHelper = null;

	public static HttpHelper getHttpHelper() {
		if (httpHelper == null) {

			httpHelper = new HttpHelper();
		}

		return httpHelper;
	}
	/**
	 * 通过POST方式发送请求
	 * 
	 * @param urlPath
	 *            URL地址
	 * @param params
	 *            参数
	 * @return
	 * @throws Exception
	 */
	public String httpPost(String urlPath, Map<String, String> params) throws Exception {
		StringBuilder sb = new StringBuilder();
		// 如果参数不为空
		if (params != null && !params.isEmpty()) {
			params.put("client", "zcweb");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				// Post方式提交参数的话，不能省略内容类型与长度
				sb.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), ENCODING)).append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}

		// 得到实体的二进制数据
		byte[] entitydata = sb.toString().getBytes();
		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(CONNECTION_TIMEOUT);
		conn.setReadTimeout(30000);
		// 如果通过post提交数据，必须设置允许对外输出数据
		conn.setDoOutput(true);
		// 这里只设置内容类型与内容长度的头字段
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		conn.setRequestProperty("Charset", ENCODING);
		conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		// 把实体数据写入是输出流
		outStream.write(entitydata);
		// 内存中的数据刷入
		outStream.flush();
		outStream.close();
		// 如果请求响应码是200，则表示成功
		if (conn.getResponseCode() == 200) {
			// 获得服务器响应的数据
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), ENCODING));
			// 数据
			String retData = null;
			String responseData = "";
			while ((retData = in.readLine()) != null) {
				responseData += retData;
			}
			in.close();
			conn.disconnect();
			return responseData;
		} else {
			int res=conn.getResponseCode();
			Map<String,String> jsonCod = new HashMap<String,String>();
			jsonCod.put("status","4");
			jsonCod.put("error","连接异常,错误码" + conn.getResponseCode());
			JSONObject json = JSONObject.fromObject(jsonCod);
			return json.toString();
		}
	}
}
