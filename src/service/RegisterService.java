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
		//查询是否有重名用户
		UserInfo userinfo = userInfoDao.getUserInfoByUsername(username);
		PrintWriter out = response.getWriter();
		if(userinfo==null){//没有重名用户
			boolean regResult = userInfoDao.createNewUser(username, password);
			if(regResult){
				out.println(JsonService.responseStringJson(1, "注册成功", new JSONObject()));
			}else{
				out.println(JsonService.responseStringJson(0, "注册失败", new JSONObject()));
			}
		}else{
			out.println(JsonService.responseStringJson(0, "用户名已被使用", new JSONObject()));
		}
		out.flush();
		out.close();
	}
}
