package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherInfoDao {//��ʦ�������
	public int checkTeacherId(int userid){//ͨ���û�id��ѯ��ʦid
		String SQL = "select id from teacher_info where user_id=?";
		Connection connection = DBDao.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			pstmt.setInt(1, userid);
			ResultSet rSet = pstmt.executeQuery();
			if(rSet.next()){
				return rSet.getInt("id");
			}
			connection.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBDao.closeConnection(connection);
		}
		return 0;
	}
}
