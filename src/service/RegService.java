package cn.lim.service;

import cn.lim.dao.DBAccess;
import cn.lim.domain.WebUser;

public class RegService {

	private final int USEREXIST = -1;
	private final int OK = 0;
	
	public int check(WebUser user) {

		DBAccess dbAcc = new DBAccess();
		if(dbAcc.checkExist(user))
		{
			return USEREXIST;
		}
		return OK;
	}

	public boolean putIn(WebUser user) {

		DBAccess dbAcc = new DBAccess();
		if(dbAcc.setUser(user)){
			return true;
		}
		return false;
	}


}
