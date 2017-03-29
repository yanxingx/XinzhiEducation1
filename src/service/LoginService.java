package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import json.JsonService;
import net.sf.json.JSONObject;
import dao.UserInfoDao;
import bean.UserInfo;

public class LoginService {
	public void doLogin(HttpServletResponse response,String username,String password) throws IOException{//��¼������
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		UserInfoDao userInfoDao = new UserInfoDao();
		UserInfo userInfo = userInfoDao.getUserInfo(username, password);
		if(userInfo!=null){
			//out.println("��¼�ɹ�!");
			//Ϊ�û������µ�token
			String token = userInfoDao.replaceNewToken(userInfo.getId());
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", userInfo.getId()+"");
			jsonObject.put("username", userInfo.getUsername());
			jsonObject.put("nick_name", userInfo.getNickName());
			jsonObject.put("heart_coin", userInfo.getHeartCoin()+"");
			jsonObject.put("is_teacher", userInfo.getIsTeacher()+"");
			jsonObject.put("token", token);
			String jsonString = JsonService.responseStringJson(1,"��¼�ɹ���",jsonObject);
			out.println(jsonString);
		}else{
			//out.println("��¼ʧ��!");
			String jsonString = JsonService.responseStringJson(0, "��¼ʧ�ܣ�", new JSONObject());
			out.println(jsonString);
		}
		out.flush();
		out.close();
	}
	public void doLoginByToken(HttpServletResponse response,String token) throws IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		UserInfoDao userInfoDao = new UserInfoDao();
		UserInfo userInfo = userInfoDao.verifyToken(token);
		if(userInfo!=null){
			//out.println("��¼�ɹ�!");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", userInfo.getId()+"");
			jsonObject.put("username", userInfo.getUsername());
			jsonObject.put("nick_name", userInfo.getNickName());
			jsonObject.put("heart_coin", userInfo.getHeartCoin()+"");
			jsonObject.put("is_teacher", userInfo.getIsTeacher()+"");
			String jsonString = JsonService.responseStringJson(1,"��¼�ɹ���",jsonObject);
			out.println(jsonString);
		}else{
			//out.println("��¼ʧ��!");
			String jsonString = JsonService.responseStringJson(0, "��¼ʧ�ܣ�", new JSONObject());
			out.println(jsonString);
		}
		
		out.flush();
		out.close();
	}
}
