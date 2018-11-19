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
 * ���������װ HttpURLConnection
 * 
 * @author �α���
 */
public class HttpHelper {

//	public static final String IP = "http://192.168.1.103:";
	public static final String IP = "http://www.wsslaw.cn:";

	public static final String APIPORT = "80/";
	
	public static final String FEEDBACK = IP + APIPORT + "zcapi/wxfeedback/addBusiness";//��������
	public static final String ADDUSER = IP + APIPORT + "zcapi/wxuser/addUser";//���Ӹ�����Ϣ
//	public static final String ADDUSER = IP + APIPORT + "zcapi/wxuser/addUser";//��ѯ������Ϣ


	
	private static final int CONNECTION_TIMEOUT = 1000 * 5; // Http���ӳ�ʱʱ��
	private static final String ENCODING = "utf-8";// �����ʽ
	private static HttpHelper httpHelper = null;

	public static HttpHelper getHttpHelper() {
		if (httpHelper == null) {

			httpHelper = new HttpHelper();
		}

		return httpHelper;
	}
	/**
	 * ͨ��POST��ʽ��������
	 * 
	 * @param urlPath
	 *            URL��ַ
	 * @param params
	 *            ����
	 * @return
	 * @throws Exception
	 */
	public String httpPost(String urlPath, Map<String, String> params) throws Exception {
		StringBuilder sb = new StringBuilder();
		// ���������Ϊ��
		if (params != null && !params.isEmpty()) {
			params.put("client", "zcweb");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				// Post��ʽ�ύ�����Ļ�������ʡ�����������볤��
				sb.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), ENCODING)).append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}

		// �õ�ʵ��Ķ���������
		byte[] entitydata = sb.toString().getBytes();
		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(CONNECTION_TIMEOUT);
		conn.setReadTimeout(30000);
		// ���ͨ��post�ύ���ݣ�����������������������
		conn.setDoOutput(true);
		// ����ֻ�����������������ݳ��ȵ�ͷ�ֶ�
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		conn.setRequestProperty("Charset", ENCODING);
		conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		// ��ʵ������д���������
		outStream.write(entitydata);
		// �ڴ��е�����ˢ��
		outStream.flush();
		outStream.close();
		// ���������Ӧ����200�����ʾ�ɹ�
		if (conn.getResponseCode() == 200) {
			// ��÷�������Ӧ������
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), ENCODING));
			// ����
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
			jsonCod.put("error","�����쳣,������" + conn.getResponseCode());
			JSONObject json = JSONObject.fromObject(jsonCod);
			return json.toString();
		}
	}
}
