package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.UserInfo;

public class RelaOfTeacherAndStuDao {//老师学生关系表服务类
	public boolean teaClaimStu(int teacherId,int stuUserId){
		String SQL = "insert into relationship_teacher_student(teacher_id,student_id) values(?,?)";
		Connection connection = DBDao.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setInt(1, teacherId);
			pstmt.setInt(2, stuUserId);
			pstmt.executeUpdate();
			connection.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			DBDao.closeConnection(connection);
		}
		return true;
	}
	public List<int[]> checkTeaAndStu(int teacherId){
		List<int[]> teaAndStuList = new ArrayList();
		
		String SQL = "select * from relationship_teacher_student where teacher_id = ?";
		Connection connection = DBDao.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setInt(1, teacherId);
			
			ResultSet rSet = pstmt.executeQuery();
			while(rSet.next()){
				int teacher_id = rSet.getInt("teacher_id");
				int student_id = rSet.getInt("student_id");
				teaAndStuList.add(new int[]{teacher_id,student_id});
			}
			
			connection.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			DBDao.closeConnection(connection);
		}
		return teaAndStuList;
	}
	public List<UserInfo> removeAddedStu(int teacherId,List<UserInfo> userInfos){//从列表中去除已经添加的学生
		List<UserInfo> userInfos2 = new ArrayList<UserInfo>();
		String SQL = "select * from relationship_teacher_student where teacher_id = ? and student_id = ?";
		Connection connection = DBDao.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setInt(1, teacherId);
			
			for(int i=0;i<userInfos.size();i++){
				UserInfo userinfo = userInfos.get(i);
				int stuid = userinfo.getId();
				pstmt.setInt(2,stuid);
				ResultSet rSet = pstmt.executeQuery();
				if(rSet.first()){
					continue;
				}
				userInfos2.add(userinfo);
			}
			connection.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			DBDao.closeConnection(connection);
		}
		return userInfos2;
	}
}
