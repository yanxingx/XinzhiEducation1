package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import json.JsonService;

import bean.UserInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import dao.RelaOfTeacherAndStuDao;
import dao.TeacherInfoDao;
import dao.UserInfoDao;

public class GetStudentService {
	public void getAllStudentList(HttpServletResponse response,String token) throws IOException{//获取所有的学生列表
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		UserInfoDao userInfoDao = new UserInfoDao();
		UserInfo userInfo2 = userInfoDao.verifyToken(token);
		JSONObject jsonObject = new JSONObject();
		if(userInfo2!=null){
			//System.out.println("token验证成功");
			List<UserInfo> userInfos = userInfoDao.getAllStudents();
			JSONArray jsonArray = new JSONArray();
			for(int i=0;i<userInfos.size();i++){
				UserInfo userInfo = userInfos.get(i);
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("id", userInfo.getId()+"");
				jsonObject2.put("username", userInfo.getUsername());
				jsonObject2.put("nick_name", userInfo.getNickName());
				jsonObject2.put("heart_coin", userInfo.getHeartCoin()+"");
				jsonObject2.put("is_teacher", userInfo.getIsTeacher()+"");
				jsonArray.put(jsonObject2);
			}
			String jsonString = JsonService.responseStringJson(1, "数据获取成功", jsonArray);
			out.println(jsonString);
		}else{
			//System.out.println("token验证失败");
			String jsonString = JsonService.responseStringJson(0, "token无效", new JSONObject());
			out.println(jsonString);
		}
		
		out.flush();
		out.close();
	}
	public void getAllStudentListWithoutToken(HttpServletResponse response) throws IOException{//获取所有的学生列表除去本老师已经添加的
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		UserInfoDao userInfoDao = new UserInfoDao();
		JSONObject jsonObject = new JSONObject();
		List<UserInfo> userInfos = userInfoDao.getAllStudents();
			
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<userInfos.size();i++){
			UserInfo userInfo = userInfos.get(i);
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("id", userInfo.getId()+"");
			jsonObject2.put("username", userInfo.getUsername());
			jsonObject2.put("nick_name", userInfo.getNickName());
			jsonObject2.put("heart_coin", userInfo.getHeartCoin()+"");
			jsonObject2.put("is_teacher", userInfo.getIsTeacher()+"");
			jsonArray.put(jsonObject2);
		}
		String jsonString = JsonService.responseStringJson(1, "数据获取成功", jsonArray);
		out.println(jsonString);
		
		out.flush();
		out.close();
	}
	public void getAllStudentListWithoutAdded(HttpServletResponse response,String token) throws IOException{//获取所有的学生列表除去本老师已经添加的
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		UserInfoDao userInfoDao = new UserInfoDao();
		UserInfo userInfo2 = userInfoDao.verifyToken(token);
		JSONObject jsonObject = new JSONObject();
		if(userInfo2!=null){
			//System.out.println("token验证成功");
			List<UserInfo> userInfos = userInfoDao.getAllStudents();
			
			RelaOfTeacherAndStuDao relaOfTeacherAndStuDao = new RelaOfTeacherAndStuDao();
			TeacherInfoDao teacherInfoDao = new TeacherInfoDao();
			int teacherId = teacherInfoDao.checkTeacherId(userInfo2.getId());
			userInfos = relaOfTeacherAndStuDao.removeAddedStu(teacherId,userInfos);
			
			JSONArray jsonArray = new JSONArray();
			for(int i=0;i<userInfos.size();i++){
				UserInfo userInfo = userInfos.get(i);
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("id", userInfo.getId()+"");
				jsonObject2.put("username", userInfo.getUsername());
				jsonObject2.put("nick_name", userInfo.getNickName());
				jsonObject2.put("heart_coin", userInfo.getHeartCoin()+"");
				jsonObject2.put("is_teacher", userInfo.getIsTeacher()+"");
				jsonArray.put(jsonObject2);
			}
			String jsonString = JsonService.responseStringJson(1, "数据获取成功", jsonArray);
			out.println(jsonString);
		}else{
			//System.out.println("token验证失败");
			String jsonString = JsonService.responseStringJson(0, "token无效", new JSONObject());
			out.println(jsonString);
		}
		
		out.flush();
		out.close();
	}
}
