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

	// 检查用户名是否存在
	public boolean checkExist(WebUser user) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select * from users where username=?";

		try {
			// 使用工具创建连接
			conn = JdbcUtils.getConnection();
			// 获取statement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, user.getUsername());

			// 执行查询语句获取结果集，如果查询到了用户，返回真，如果没有查询到用户，返回假
			rs = pst.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭资源
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

	// 检查输入的知乎用户名是否已经存在
	public boolean checkExist(FollowTable ft) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select * from follows where F_Mail = ? and F_Follow = ?";

		try {
			// 使用工具创建连接
			conn = JdbcUtils.getConnection();
			// 获取statement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, ft.getMail());
			pst.setString(2, ft.getFollow());

			// 执行查询语句获取结果集，如果查询到了用户，返回真，如果没有查询到用户，返回假
			rs = pst.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭资源
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

	// 注册用户添加到数据库
	public boolean setUser(WebUser user) {

		Connection conn = null;
		PreparedStatement pst = null;

		String sql = "insert into users (username,password,date) values (?,?,?);";

		// 取当前日期。
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());

		try {
			// 使用工具创建连接
			conn = JdbcUtils.getConnection();
			// 获取PreparedStatement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getPassword());
			pst.setString(3, date);

			// 执行查询语句获取结果集
			if (pst.executeUpdate() == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return false;

	}

	// 添加关注列表
	public boolean setFollowTable(FollowTable ft) {

		Connection conn = null;
		PreparedStatement pst_1 = null;
		PreparedStatement pst_2 = null;
		ResultSet rs = null;

		String sql_1 = "insert into follows (F_Mail,F_Follow,F_isSend,F_Date) values (?,?,?,?);";
		String sql_2 = "select * from follows where F_Mail=?";

		try {
			// 使用工具创建连接
			conn = JdbcUtils.getConnection();
			// 获取PreparedStatement
			pst_1 = (PreparedStatement) conn.prepareStatement(sql_1);
			pst_1.setString(1, ft.getMail());
			pst_1.setString(2, ft.getFollow());
			if (ft.isSend()) {
				pst_1.setString(3, "YES");
			} else {
				pst_1.setString(3, "NO");
			}
			pst_1.setString(4, ft.getFollowDate());

			// 执行查询语句获取结果集
			if (pst_1.executeUpdate() == 1) {
				//添加成功后，获取最新关注列表，更新session中的用户信息
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
			// 关闭资源
			try {
				pst_1.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	//检查用户登录情况
	public boolean checkLogin(String username, String password) {
		Connection conn = null;
		PreparedStatement pst_1 = null;
		PreparedStatement pst_2 = null;

		ResultSet rs_1 = null;
		ResultSet rs_2 = null;

		String sql_1 = "select * from users where username=? and password=?";
		String sql_2 = "select * from follows where F_Mail=?";

		try {
			// 使用工具创建连接
			conn = JdbcUtils.getConnection();
			// 获取PreparedStatement
			pst_1 = (PreparedStatement) conn.prepareStatement(sql_1);
			pst_1.setString(1, username);
			pst_1.setString(2, password);

			// 执行查询语句获取结果集，如果查询到了用户，返回真，如果没有查询到用户，返回假
			rs_1 = pst_1.executeQuery();

			//如果查询到了用户，获取该用户的关注列表，并返回真
			if (rs_1.next()) {
				this.webUser.setUsername(username);
				this.webUser.setPassword(password);

				if (rs_1.getString("date") != null)
					this.webUser.setRegDate(rs_1.getString("date"));

				// 查询该用户的关注列表
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
			// 关闭资源
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

	// 删除关注列表
	public boolean deleteFollow(String mail,String followName){
		
		Connection conn = null;
		PreparedStatement pst_1 = null;
		PreparedStatement pst_2 = null;
		ResultSet rs = null;
		
		String sql_1 = "delete from follows where F_Mail = ? and F_Follow = ?;";
		String sql_2 = "select * from follows where F_Mail=?";

		try {
			//使用工具创建连接
			conn = JdbcUtils.getConnection();
			//获取PreparedStatement
			pst_1 = (PreparedStatement) conn.prepareStatement(sql_1);
			pst_1.setString(1, mail);
			pst_1.setString(2, followName);
			
			//执行语句结果集
			if(pst_1.executeUpdate()==1){
				
				// 如果查询到了结果，再更新用户的关注列表
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
			//关闭资源
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

	//编辑关注列表
	public boolean editFollow(FollowTable ft) {
		
		Connection conn = null;
		PreparedStatement pst_1 = null;
		PreparedStatement pst_2 = null;
		ResultSet rs = null;
		
		String sql_1 = "update follows set F_isSend=?,F_Date=? where F_Mail=? and F_Follow=?;";
		String sql_2 = "select * from follows where F_Mail=?";

		try {
			//使用工具创建连接
			conn = JdbcUtils.getConnection();
			//获取PreparedStatement
			pst_1 = (PreparedStatement) conn.prepareStatement(sql_1);
			if (ft.isSend()) {
				pst_1.setString(1, "YES");
			} else {
				pst_1.setString(1, "NO");
			}
			pst_1.setString(2, ft.getFollowDate());
			pst_1.setString(3, ft.getMail());
			pst_1.setString(4, ft.getFollow());
			
			//执行语句结果集
			if(pst_1.executeUpdate()==1){
				
				// 如果查询到了结果，再更新用户的关注列表
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
			//关闭资源
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
