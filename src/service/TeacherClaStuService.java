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
			if(userInfo.getIsTeacher()==1){//�ж��ǲ�����ʦ�˺�
				int teaId = teacherInfoDao.checkTeacherId(userInfo.getId());
				boolean insertSuccess = reaOAndStuDao.teaClaimStu(teaId, Integer.parseInt(stuUserId));
				if(insertSuccess){
					String jsonString = JsonService.responseStringJson(1, "ѧ����ӳɹ�", new JSONObject());
					out.println(jsonString);
				}else{
					String jsonString = JsonService.responseStringJson(0, "ѧ�����ʧ��", new JSONObject());
					out.println(jsonString);
				}
			}else{
				String jsonString = JsonService.responseStringJson(0, "��ʦ�˺Ų�������ѧ��", new JSONObject());
				out.println(jsonString);
			}
		}else{
			String jsonString = JsonService.responseStringJson(0, "token��Ч", new JSONObject());
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
			if(userInfo.getIsTeacher()==1){//�ж��ǲ�����ʦ�˺�
				//��ʼͨ����ʦ�û�id��ȡ��ʦid
				TeacherInfoDao teacherInfoDao = new TeacherInfoDao();
				int teacherId = teacherInfoDao.checkTeacherId(userInfo.getId());
				//��ʼ��ȡѧ���б�
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
				String jsonString = JsonService.responseStringJson(1, "ѧ���б��ȡ�ɹ�", jsonArray);
				out.println(jsonString);
			}else{
				String jsonString = JsonService.responseStringJson(0, "��ʦ�˺Ų��ܻ�ȡѧ���б�", new JSONObject());
				out.println(jsonString);
			}
		}else{
			String jsonString = JsonService.responseStringJson(0, "token��Ч", new JSONObject());
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
			if(userInfo.getIsTeacher()==1){//�ж��ǲ�����ʦ�˺�
				boolean updateSuccess = userInfoDao.updateHeartCoinOfUser(Integer.parseInt(stuid), Integer.parseInt(num));
				if(updateSuccess){
					String jsonString = JsonService.responseStringJson(1, "�ı��޸ĳɹ�", new JSONObject());
					out.println(jsonString);
				}else{
					String jsonString = JsonService.responseStringJson(0, "�ı��޸�ʧ��", new JSONObject());
					out.println(jsonString);
				}
			}else{
				String jsonString = JsonService.responseStringJson(0, "��ʦ�˺Ų����޸�ѧ���ı�", new JSONObject());
				out.println(jsonString);
			}
		}else{
			String jsonString = JsonService.responseStringJson(0, "token��Ч", new JSONObject());
			out.println(jsonString);
		}
		
		out.flush();
		out.close();
	}
}
