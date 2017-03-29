package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import bean.UserInfo;

import json.JsonService;

import dao.UserInfoDao;

public class RegisterService {
	public void doRegister(HttpServletResponse response,String username,String password) throws IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		UserInfoDao userInfoDao = new UserInfoDao();
		//��ѯ�Ƿ��������û�
		UserInfo userinfo = userInfoDao.getUserInfoByUsername(username);
		PrintWriter out = response.getWriter();
		if(userinfo==null){//û�������û�
			boolean regResult = userInfoDao.createNewUser(username, password);
			if(regResult){
				out.println(JsonService.responseStringJson(1, "ע��ɹ�", new JSONObject()));
			}else{
				out.println(JsonService.responseStringJson(0, "ע��ʧ��", new JSONObject()));
			}
		}else{
			out.println(JsonService.responseStringJson(0, "�û����ѱ�ʹ��", new JSONObject()));
		}
		out.flush();
		out.close();
	}
}
