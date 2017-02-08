package cn.lim.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcUtils {
	
	// 获取配置文件中的相关参数
	public static final String CLASSNAME = ResourceBundle.getBundle("jdbc").getString("ClassName");
	public static final String URL = ResourceBundle.getBundle("jdbc").getString("url");
	public static final String DBUSER = ResourceBundle.getBundle("jdbc").getString("DBuser");
	public static final String DBPSW = ResourceBundle.getBundle("jdbc").getString("DBpsw");
	
	// 在静态代码块中注册驱动——在类加载时便注册，并且只需注册一次
	static{
		try {
			Class.forName(CLASSNAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// 获取连接对象并返回
	public static Connection getConnection() throws SQLException{
		Connection conn = DriverManager.getConnection(URL,DBUSER,DBPSW);
		return conn;
	}
}
