package cn.lim.service;

import cn.lim.dao.DBAccess;
import cn.lim.domain.WebUser;

public class DeleteFollowsService {

	public WebUser delete(String mail,String followName){

		DBAccess dbAcc = new DBAccess();
		// ɾ����Ӧ���ݣ�����ɹ������ظ��û����µ���Ϣ
		if(dbAcc.deleteFollow(mail, followName)){
			return dbAcc.getWebUser();
		}
		return null;
		
	}
}
