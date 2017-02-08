package cn.lim.service;

import cn.lim.dao.DBAccess;
import cn.lim.domain.WebUser;

public class LoginService {
	
	WebUser webUser = new WebUser();
	
	public boolean check(String username,String password) {

		DBAccess dbAcc = new DBAccess();
		if(dbAcc.checkLogin(username,password)){
			webUser = dbAcc.getWebUser();
			return true;
		}
		return false;
	}

	public WebUser getWebUser() {
		return webUser;
	}

	
}
