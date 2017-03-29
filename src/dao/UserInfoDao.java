package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.UserInfo;

public class UserInfoDao {
	public UserInfo getUserInfoByUsername(String username){//通过用户名从数据库读取用户信息
		UserInfo userInfo = null;
		String SQL = "select * from user_info where username = ?";
		Connection connection = DBDao.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setString(1, username);
			ResultSet rSet = pstmt.executeQuery();
			//判断数据结果是否有效
			if(rSet.next()){
				userInfo = new UserInfo();
				userInfo.setId(rSet.getInt("id"));
				userInfo.setUsername(rSet.getString("username"));
			}
			connection.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(connection);
		}
		return userInfo;
	}
	public UserInfo getUserInfo(String username,String password){//通过用户名密码从数据库读取用户信息
		UserInfo userInfo = null;
		String SQL = "select * from user_info where username = ? and password = ?";
		Connection connection = DBDao.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rSet = pstmt.executeQuery();
			//判断数据结果是否有效
			if(rSet.next()){
				userInfo = new UserInfo();
				userInfo.setId(rSet.getInt("id"));
				userInfo.setUsername(rSet.getString("username"));
				userInfo.setNickName(rSet.getString("nick_name"));
				userInfo.setHeartCoin(rSet.getInt("heart_coin"));
				userInfo.setIsTeacher(rSet.getInt("is_teacher"));
			}
			connection.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(connection);
		}
		return userInfo;
	}
	public String replaceNewToken(int userid){//给用户生成并更换一个新的token
		String SQL = "update user_info set token=? where id=?";
		Connection connection = DBDao.getConnection();
		try {
			String randomNumStr = Math.random()+"";
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setString(1, randomNumStr);
			pstmt.setInt(2, userid);
			pstmt.executeUpdate();
			connection.close();
			pstmt.close();
			return randomNumStr;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(connection);
		}
		return "";
	}
	public boolean createNewUser(String username,String password){//创建新的用户
		String SQL = "insert into user_info(username,password) values(?,?)";
		Connection connection = DBDao.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
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
	public UserInfo verifyToken(String token){//验证token是否和数据库相符
		String SQL = "select * from user_info where token=?";
		Connection connection = DBDao.getConnection();
		UserInfo userInfo = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setString(1, token);
			ResultSet rSet = pstmt.executeQuery();
			//判断数据结果是否有效
			if(rSet.next()){
				userInfo = new UserInfo();
				userInfo.setId(rSet.getInt("id"));
				userInfo.setUsername(rSet.getString("username"));
				userInfo.setNickName(rSet.getString("nick_name"));
				userInfo.setHeartCoin(rSet.getInt("heart_coin"));
				userInfo.setIsTeacher(rSet.getInt("is_teacher"));
				return userInfo;
			}
			connection.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(connection);
		}
		return userInfo;
	}
	public List<UserInfo> getAllStudents(){//获取学生列表
		String SQL = "select * from user_info where is_teacher=0 order by heart_coin desc";
		Connection connection = DBDao.getConnection();
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			ResultSet rSet = pstmt.executeQuery();
			while(rSet.next()){
				int id = rSet.getInt("id");
				String username = rSet.getString("username");
				String token = rSet.getString("token");
				int isTeacher = rSet.getInt("is_teacher");
				int heartCoin = rSet.getInt("heart_coin");
				String nickName = rSet.getString("nick_name");
				userInfos.add(new UserInfo(id,username,token,isTeacher,heartCoin,nickName));
			}
			connection.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(connection);
		}
		return userInfos;
	}
	public List<UserInfo> getUserListById(List<int[]> teaAndStuList){
		List<UserInfo> stuList = new ArrayList();
		
		String SQL = "select * from user_info where id=?";
		Connection connection = DBDao.getConnection();
		try {
		PreparedStatement pstmt = connection.prepareStatement(SQL);
		for(int i=0;i<teaAndStuList.size();i++){
			int stuid = teaAndStuList.get(i)[1];
			pstmt.setInt(1, stuid);
			ResultSet rSet = pstmt.executeQuery();
			if(rSet.next()){
				int id = rSet.getInt("id");
				String username = rSet.getString("username");
				String nick_name = rSet.getString("nick_name");
				int heart_coin = rSet.getInt("heart_coin");
				int is_teacher = rSet.getInt("is_teacher");
				stuList.add(new UserInfo(id, username, "", is_teacher, heart_coin, nick_name));
			}
		}
		connection.close();
		pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			DBDao.closeConnection(connection);
		}
		return stuList;
	}
	public boolean updateHeartCoinOfUser(int userId,int num){//修改用户心币值
		String SQL = "update user_info set heart_coin=heart_coin+? where id=?";
		Connection connection = DBDao.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setInt(1, num);
			pstmt.setInt(2, userId);
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
}
