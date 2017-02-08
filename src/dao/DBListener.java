package cn.lim.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

import cn.lim.domain.ZhihuUser;
import cn.lim.utils.JdbcUtils;

/**
 * 定义后台读写数据库的类
 * @author Lim
 *
 */
public class DBListener {

	//获取设置了提醒邮件的用户邮箱及关注列表
	public HashMap<String, String> getSendList() {
		
		// 定义存储待发邮件列表的变量
		HashMap<String,String> sendList = new HashMap<String, String>();
		
		// 读数据库，获取设置了提醒邮件的用户邮箱及关注列表
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select F_Mail,F_Follow from follows where F_isSend='yes'";

		try {
			// 使用工具创建连接
			conn = JdbcUtils.getConnection();
			// 获取statement
			pst = (PreparedStatement) conn.prepareStatement(sql);

			// 执行查询语句获取结果集，将结果数据存进行整理保存
			rs = pst.executeQuery();
			while(rs.next()){
				//整理Map中的数据，合并关注列表相同的用户，提高后期工作效率
				//如果在待发列表中，已经存在该知乎用户的关注，则将邮件添加到该条待发列表，用英文逗号,分隔
				String sendListKey = rs.getString("F_Follow");
				String sendListValue = rs.getString("F_Mail");
				if(sendList.containsKey(rs.getString("F_Follow"))){
					sendList.replace(sendListKey, sendList.get(sendListKey)+","+sendListValue);
				}
				//如果在待发列表中，没有该知乎用户的关注，则将该知乎用户加入到待发列表
				else{
					sendList.put(rs.getString("F_Follow"), rs.getString("F_Mail"));
				}
			}
			System.out.println();
			System.out.println("*** 获取待发列表成功！***");
			return sendList;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println();
			System.out.println("*** 获取待发列表失败！ ***");
			return null;
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
	}
	
	

	public String getNewest(String zhUserName) {
		// 定义存储最新动态的变量
		String newest = "";
		
		// 读数据库，获取设置了提醒邮件的用户邮箱及关注列表
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select FA_Activities from followsactivities where FA_Follow = ?";

		try {
			// 使用工具创建连接
			conn = JdbcUtils.getConnection();
			// 获取statement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, zhUserName);
			
			// 执行查询语句获取结果集，读取并返回查询结果
			rs = pst.executeQuery();
			
			// 数据库中存在该用户的动态，则返回其动态内容
			if(rs.next()){
				newest = rs.getString("FA_Activities");
				System.out.println(zhUserName +"： 获取最新信息成功！");
				return newest;
			}
			// 数据库中还没有该用户的动态，返回空
			else
			{
				System.out.println(zhUserName +"：数据库中还没有该用户的动态！");
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(zhUserName + "：获取最新信息失败！");
			return null;
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
	}
	
	
	
	
	public boolean setActivities(ZhihuUser zhUser, String zhUserName, String newestInDB) {
		
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "";
		
		// 如果在数据库中原来的最新信息为空，说明没有保存该用户的最新信息，insert语句新加入该用户的最新信息
		if (newestInDB == null){
			 sql = "insert into followsactivities (FA_Activities,FA_Follow) values (?,?)";
		}
		// 如果数据库中原来就存在该知乎用户的动态，直接更新该条动态
		else{
			 sql = "update followsactivities set FA_Activities = ? where FA_Follow = ?";
		}
		try {
			//使用工具创建连接
			conn = JdbcUtils.getConnection();
			//获取PreparedStatement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			
			pst.setString(1, zhUser.getNewest());
			pst.setString(2, zhUserName);
			
			//执行语句结果集
			if(pst.executeUpdate()==1){
				System.out.println(zhUserName + "：更新数据库成功！");
				return true;
				
			}else{
				System.out.println(zhUserName + "：更新数据库失败！");
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			//关闭资源
			try {
				if(pst!=null)
					pst.close();
				if(rs!=null)
					rs.close();
				if(conn!=null)
					conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
			
		}
	}

	
}
