package cn.lim.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcUtils {
	
	// ��ȡ�����ļ��е���ز���
	public static final String CLASSNAME = ResourceBundle.getBundle("jdbc").getString("ClassName");
	public static final String URL = ResourceBundle.getBundle("jdbc").getString("url");
	public static final String DBUSER = ResourceBundle.getBundle("jdbc").getString("DBuser");
	public static final String DBPSW = ResourceBundle.getBundle("jdbc").getString("DBpsw");
	
	// �ھ�̬�������ע�����������������ʱ��ע�ᣬ����ֻ��ע��һ��
	static{
		try {
			Class.forName(CLASSNAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// ��ȡ���Ӷ��󲢷���
	public static Connection getConnection() throws SQLException{
		Connection conn = DriverManager.getConnection(URL,DBUSER,DBPSW);
		return conn;
	}
}
