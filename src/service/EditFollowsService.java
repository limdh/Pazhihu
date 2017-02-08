package cn.lim.service;


import cn.lim.dao.DBAccess;
import cn.lim.domain.FollowTable;
import cn.lim.domain.WebUser;

public class EditFollowsService {

	public WebUser editFollow(FollowTable ft) {
		DBAccess dba = new DBAccess();
		if(dba.editFollow(ft)){
			return dba.getWebUser();
		}		
		
		return null;
	}

}
