package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JsonService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import bean.UserInfo;
import dao.RelaOfTeacherAndStuDao;
import dao.TeacherInfoDao;
import dao.UserInfoDao;

public class TeacherClaStuService {
	public void claimStudent(HttpServletResponse response,String token,String stuUserId) throws IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		UserInfoDao userInfoDao = new UserInfoDao();
		TeacherInfoDao teacherInfoDao = new TeacherInfoDao();
		RelaOfTeacherAndStuDao reaOAndStuDao = new RelaOfTeacherAndStuDao();
		UserInfo userInfo = userInfoDao.verifyToken(token);
		JSONObject jsonObject = new JSONObject();
		if(userInfo!=null){
			if(userInfo.getIsTeacher()==1){//判断是不是老师账号
				int teaId = teacherInfoDao.checkTeacherId(userInfo.getId());
				boolean insertSuccess = reaOAndStuDao.teaClaimStu(teaId, Integer.parseInt(stuUserId));
				if(insertSuccess){
					String jsonString = JsonService.responseStringJson(1, "学生添加成功", new JSONObject());
					out.println(jsonString);
				}else{
					String jsonString = JsonService.responseStringJson(0, "学生添加失败", new JSONObject());
					out.println(jsonString);
				}
			}else{
				String jsonString = JsonService.responseStringJson(0, "老师账号才能认领学生", new JSONObject());
				out.println(jsonString);
			}
		}else{
			String jsonString = JsonService.responseStringJson(0, "token无效", new JSONObject());
			out.println(jsonString);
		}
		
		out.flush();
		out.close();
	}
	public void getAllMyStudent(HttpServletResponse response,String token) throws IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		UserInfoDao userInfoDao = new UserInfoDao();
		RelaOfTeacherAndStuDao reaOAndStuDao = new RelaOfTeacherAndStuDao();
		UserInfo userInfo = userInfoDao.verifyToken(token);
		JSONObject jsonObject = new JSONObject();
		if(userInfo!=null){
			if(userInfo.getIsTeacher()==1){//判断是不是老师账号
				//开始通过老师用户id获取老师id
				TeacherInfoDao teacherInfoDao = new TeacherInfoDao();
				int teacherId = teacherInfoDao.checkTeacherId(userInfo.getId());
				//开始获取学生列表
				List<int[]> teaAndStuList = reaOAndStuDao.checkTeaAndStu(teacherId);
				List<UserInfo> stuList = userInfoDao.getUserListById(teaAndStuList);
				JSONArray jsonArray = new JSONArray();
				for(int i=0;i<stuList.size();i++){
					UserInfo stuInfo = stuList.get(i);
					JSONObject jsonObject2 = new JSONObject();
					jsonObject2.put("id", stuInfo.getId()+"");
					jsonObject2.put("username", stuInfo.getUsername());
					jsonObject2.put("nick_name", stuInfo.getNickName());
					jsonObject2.put("heart_coin", stuInfo.getHeartCoin()+"");
					jsonObject2.put("is_teacher", userInfo.getIsTeacher()+"");
					jsonArray.put(jsonObject2);
				}
				String jsonString = JsonService.responseStringJson(1, "学生列表获取成功", jsonArray);
				out.println(jsonString);
			}else{
				String jsonString = JsonService.responseStringJson(0, "老师账号才能获取学生列表", new JSONObject());
				out.println(jsonString);
			}
		}else{
			String jsonString = JsonService.responseStringJson(0, "token无效", new JSONObject());
			out.println(jsonString);
		}
		
		out.flush();
		out.close();
	}
	public void teacherUpdateStuHeartCoin(HttpServletResponse response,String token,String stuid,String num) throws IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		UserInfoDao userInfoDao = new UserInfoDao();
		RelaOfTeacherAndStuDao reaOAndStuDao = new RelaOfTeacherAndStuDao();
		UserInfo userInfo = userInfoDao.verifyToken(token);
		JSONObject jsonObject = new JSONObject();
		if(userInfo!=null){
			if(userInfo.getIsTeacher()==1){//判断是不是老师账号
				boolean updateSuccess = userInfoDao.updateHeartCoinOfUser(Integer.parseInt(stuid), Integer.parseInt(num));
				if(updateSuccess){
					String jsonString = JsonService.responseStringJson(1, "心币修改成功", new JSONObject());
					out.println(jsonString);
				}else{
					String jsonString = JsonService.responseStringJson(0, "心币修改失败", new JSONObject());
					out.println(jsonString);
				}
			}else{
				String jsonString = JsonService.responseStringJson(0, "老师账号才能修改学生心币", new JSONObject());
				out.println(jsonString);
			}
		}else{
			String jsonString = JsonService.responseStringJson(0, "token无效", new JSONObject());
			out.println(jsonString);
		}
		
		out.flush();
		out.close();
	}
}
