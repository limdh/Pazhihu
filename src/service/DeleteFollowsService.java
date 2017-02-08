package cn.lim.service;

import cn.lim.dao.DBAccess;
import cn.lim.domain.WebUser;

public class DeleteFollowsService {

	public WebUser delete(String mail,String followName){

		DBAccess dbAcc = new DBAccess();
		// 删除相应数据，如果成功，返回该用户的新的信息
		if(dbAcc.deleteFollow(mail, followName)){
			return dbAcc.getWebUser();
		}
		return null;
		
	}
}
