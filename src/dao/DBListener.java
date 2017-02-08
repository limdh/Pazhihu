package cn.lim.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

import cn.lim.domain.ZhihuUser;
import cn.lim.utils.JdbcUtils;

/**
 * �����̨��д���ݿ����
 * @author Lim
 *
 */
public class DBListener {

	//��ȡ�����������ʼ����û����估��ע�б�
	public HashMap<String, String> getSendList() {
		
		// ����洢�����ʼ��б�ı���
		HashMap<String,String> sendList = new HashMap<String, String>();
		
		// �����ݿ⣬��ȡ�����������ʼ����û����估��ע�б�
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select F_Mail,F_Follow from follows where F_isSend='yes'";

		try {
			// ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			// ��ȡstatement
			pst = (PreparedStatement) conn.prepareStatement(sql);

			// ִ�в�ѯ����ȡ���������������ݴ����������
			rs = pst.executeQuery();
			while(rs.next()){
				//����Map�е����ݣ��ϲ���ע�б���ͬ���û�����ߺ��ڹ���Ч��
				//����ڴ����б��У��Ѿ����ڸ�֪���û��Ĺ�ע�����ʼ���ӵ����������б���Ӣ�Ķ���,�ָ�
				String sendListKey = rs.getString("F_Follow");
				String sendListValue = rs.getString("F_Mail");
				if(sendList.containsKey(rs.getString("F_Follow"))){
					sendList.replace(sendListKey, sendList.get(sendListKey)+","+sendListValue);
				}
				//����ڴ����б��У�û�и�֪���û��Ĺ�ע���򽫸�֪���û����뵽�����б�
				else{
					sendList.put(rs.getString("F_Follow"), rs.getString("F_Mail"));
				}
			}
			System.out.println();
			System.out.println("*** ��ȡ�����б�ɹ���***");
			return sendList;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println();
			System.out.println("*** ��ȡ�����б�ʧ�ܣ� ***");
			return null;
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
	}
	
	

	public String getNewest(String zhUserName) {
		// ����洢���¶�̬�ı���
		String newest = "";
		
		// �����ݿ⣬��ȡ�����������ʼ����û����估��ע�б�
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String sql = "select FA_Activities from followsactivities where FA_Follow = ?";

		try {
			// ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			// ��ȡstatement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			pst.setString(1, zhUserName);
			
			// ִ�в�ѯ����ȡ���������ȡ�����ز�ѯ���
			rs = pst.executeQuery();
			
			// ���ݿ��д��ڸ��û��Ķ�̬���򷵻��䶯̬����
			if(rs.next()){
				newest = rs.getString("FA_Activities");
				System.out.println(zhUserName +"�� ��ȡ������Ϣ�ɹ���");
				return newest;
			}
			// ���ݿ��л�û�и��û��Ķ�̬�����ؿ�
			else
			{
				System.out.println(zhUserName +"�����ݿ��л�û�и��û��Ķ�̬��");
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(zhUserName + "����ȡ������Ϣʧ�ܣ�");
			return null;
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
	}
	
	
	
	
	public boolean setActivities(ZhihuUser zhUser, String zhUserName, String newestInDB) {
		
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "";
		
		// ��������ݿ���ԭ����������ϢΪ�գ�˵��û�б�����û���������Ϣ��insert����¼�����û���������Ϣ
		if (newestInDB == null){
			 sql = "insert into followsactivities (FA_Activities,FA_Follow) values (?,?)";
		}
		// ������ݿ���ԭ���ʹ��ڸ�֪���û��Ķ�̬��ֱ�Ӹ��¸�����̬
		else{
			 sql = "update followsactivities set FA_Activities = ? where FA_Follow = ?";
		}
		try {
			//ʹ�ù��ߴ�������
			conn = JdbcUtils.getConnection();
			//��ȡPreparedStatement
			pst = (PreparedStatement) conn.prepareStatement(sql);
			
			pst.setString(1, zhUser.getNewest());
			pst.setString(2, zhUserName);
			
			//ִ���������
			if(pst.executeUpdate()==1){
				System.out.println(zhUserName + "���������ݿ�ɹ���");
				return true;
				
			}else{
				System.out.println(zhUserName + "���������ݿ�ʧ�ܣ�");
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			//�ر���Դ
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
