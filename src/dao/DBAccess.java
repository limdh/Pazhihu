package cn.lim.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

import cn.lim.domain.FollowTable;
import cn.lim.domain.WebUser;
import cn.lim.utils.JdbcUtils;

public class DBAccess {

	private WebUser webUser = new WebUser();

	// ����û����Ƿ����
	public boolean checkExist(WebUser user) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select * from users where username=?";

		try {
			// ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			// ��ȡstatement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, user.getUsername());

			// ִ�в�ѯ����ȡ������������ѯ�����û��������棬���û�в�ѯ���û������ؼ�
			rs = pst.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر���Դ
			try {
				rs.close();
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	// ��������֪���û����Ƿ��Ѿ�����
	public boolean checkExist(FollowTable ft) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select * from follows where F_Mail = ? and F_Follow = ?";

		try {
			// ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			// ��ȡstatement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, ft.getMail());
			pst.setString(2, ft.getFollow());

			// ִ�в�ѯ����ȡ������������ѯ�����û��������棬���û�в�ѯ���û������ؼ�
			rs = pst.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر���Դ
			try {
				rs.close();
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	// ע���û���ӵ����ݿ�
	public boolean setUser(WebUser user) {

		Connection conn = null;
		PreparedStatement pst = null;

		String sql = "insert into users (username,password,date) values (?,?,?);";

		// ȡ��ǰ���ڡ�
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());

		try {
			// ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			// ��ȡPreparedStatement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getPassword());
			pst.setString(3, date);

			// ִ�в�ѯ����ȡ�����
			if (pst.executeUpdate() == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر���Դ
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return false;

	}

	// ��ӹ�ע�б�
	public boolean setFollowTable(FollowTable ft) {

		Connection conn = null;
		PreparedStatement pst_1 = null;
		PreparedStatement pst_2 = null;
		ResultSet rs = null;

		String sql_1 = "insert into follows (F_Mail,F_Follow,F_isSend,F_Date) values (?,?,?,?);";
		String sql_2 = "select * from follows where F_Mail=?";

		try {
			// ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			// ��ȡPreparedStatement
			pst_1 = (PreparedStatement) conn.prepareStatement(sql_1);
			pst_1.setString(1, ft.getMail());
			pst_1.setString(2, ft.getFollow());
			if (ft.isSend()) {
				pst_1.setString(3, "YES");
			} else {
				pst_1.setString(3, "NO");
			}
			pst_1.setString(4, ft.getFollowDate());

			// ִ�в�ѯ����ȡ�����
			if (pst_1.executeUpdate() == 1) {
				//��ӳɹ��󣬻�ȡ���¹�ע�б�����session�е��û���Ϣ
				HashMap<String, String> follows = null;

				pst_2 = (PreparedStatement) conn.prepareStatement(sql_2);
				pst_2.setString(1, ft.getMail());
				rs = pst_2.executeQuery();

				while (rs.next()) {
					if (follows == null) {
						follows = new HashMap<String, String>();
					}
					follows.put(rs.getString("F_Follow"), rs.getString("F_isSend"));
				}

				this.webUser.setUsername(ft.getMail());
				this.webUser.setFollows(follows);
				
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر���Դ
			try {
				pst_1.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	//����û���¼���
	public boolean checkLogin(String username, String password) {
		Connection conn = null;
		PreparedStatement pst_1 = null;
		PreparedStatement pst_2 = null;

		ResultSet rs_1 = null;
		ResultSet rs_2 = null;

		String sql_1 = "select * from users where username=? and password=?";
		String sql_2 = "select * from follows where F_Mail=?";

		try {
			// ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			// ��ȡPreparedStatement
			pst_1 = (PreparedStatement) conn.prepareStatement(sql_1);
			pst_1.setString(1, username);
			pst_1.setString(2, password);

			// ִ�в�ѯ����ȡ������������ѯ�����û��������棬���û�в�ѯ���û������ؼ�
			rs_1 = pst_1.executeQuery();

			//�����ѯ�����û�����ȡ���û��Ĺ�ע�б���������
			if (rs_1.next()) {
				this.webUser.setUsername(username);
				this.webUser.setPassword(password);

				if (rs_1.getString("date") != null)
					this.webUser.setRegDate(rs_1.getString("date"));

				// ��ѯ���û��Ĺ�ע�б�
				HashMap<String, String> follows = null;
	
				pst_2 = (PreparedStatement) conn.prepareStatement(sql_2);
				pst_2.setString(1, username);
				rs_2 = pst_2.executeQuery();
	
				while (rs_2.next()) {
					if (follows == null) {
						follows = new HashMap<String, String>();
					}
					follows.put(rs_2.getString("F_Follow"), rs_2.getString("F_isSend"));
	
				}
				this.webUser.setFollows(follows);

				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر���Դ
			try {
				if(rs_1!=null)
					rs_1.close();
				if(pst_1!=null)
					pst_1.close();
				if(rs_2!=null)
					rs_2.close();
				if(pst_2!=null)
					pst_2.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	public WebUser getWebUser() {
		return webUser;
	}

	// ɾ����ע�б�
	public boolean deleteFollow(String mail,String followName){
		
		Connection conn = null;
		PreparedStatement pst_1 = null;
		PreparedStatement pst_2 = null;
		ResultSet rs = null;
		
		String sql_1 = "delete from follows where F_Mail = ? and F_Follow = ?;";
		String sql_2 = "select * from follows where F_Mail=?";

		try {
			//ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			//��ȡPreparedStatement
			pst_1 = (PreparedStatement) conn.prepareStatement(sql_1);
			pst_1.setString(1, mail);
			pst_1.setString(2, followName);
			
			//ִ���������
			if(pst_1.executeUpdate()==1){
				
				// �����ѯ���˽�����ٸ����û��Ĺ�ע�б�
				HashMap<String, String> follows = null;

				pst_2 = (PreparedStatement) conn.prepareStatement(sql_2);
				pst_2.setString(1, mail);
				rs = pst_2.executeQuery();

				while (rs.next()) {
					if (follows == null) {
						follows = new HashMap<String, String>();
					}
					follows.put(rs.getString("F_Follow"), rs.getString("F_isSend"));
				}

				this.webUser.setUsername(mail);
				this.webUser.setFollows(follows);
				return true;
				
			}else{
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//�ر���Դ
			try {
				pst_1.close();
				pst_2.close();
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

		return false;
		
	}

	//�༭��ע�б�
	public boolean editFollow(FollowTable ft) {
		
		Connection conn = null;
		PreparedStatement pst_1 = null;
		PreparedStatement pst_2 = null;
		ResultSet rs = null;
		
		String sql_1 = "update follows set F_isSend=?,F_Date=? where F_Mail=? and F_Follow=?;";
		String sql_2 = "select * from follows where F_Mail=?";

		try {
			//ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			//��ȡPreparedStatement
			pst_1 = (PreparedStatement) conn.prepareStatement(sql_1);
			if (ft.isSend()) {
				pst_1.setString(1, "YES");
			} else {
				pst_1.setString(1, "NO");
			}
			pst_1.setString(2, ft.getFollowDate());
			pst_1.setString(3, ft.getMail());
			pst_1.setString(4, ft.getFollow());
			
			//ִ���������
			if(pst_1.executeUpdate()==1){
				
				// �����ѯ���˽�����ٸ����û��Ĺ�ע�б�
				HashMap<String, String> follows = null;

				pst_2 = (PreparedStatement) conn.prepareStatement(sql_2);
				pst_2.setString(1, ft.getMail());
				rs = pst_2.executeQuery();

				while (rs.next()) {
					if (follows == null) {
						follows = new HashMap<String, String>();
					}
					follows.put(rs.getString("F_Follow"), rs.getString("F_isSend"));
				}

				this.webUser.setUsername(ft.getMail());
				this.webUser.setFollows(follows);
				return true;
				
			}else{
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//�ر���Դ
			try {
				if(pst_1!=null)
					pst_1.close();
				if(pst_2!=null)
					pst_2.close();
				if(rs!=null)
					rs.close();
				if(conn!=null)
					conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

		return false;
	}

}
