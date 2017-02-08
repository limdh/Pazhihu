package cn.lim.service;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;

import cn.lim.dao.DBAccess;
import cn.lim.domain.FollowTable;
import cn.lim.domain.WebUser;

public class SetFollowsService {

	public static final int MAILFAIL = -1;
	public static final int FOLLOWFAIL = -2;
	public static final int DBFAIL = -4;
	public static final int FOLLOWEXIST = -5;
	public static final int SUCCESS = 0;

	ArrayList<FollowTable> ftList = new ArrayList<FollowTable>();
	private WebUser webUser = new WebUser();

	public SetFollowsService(ArrayList<FollowTable> ftList, WebUser webUser) {
		super();
		this.ftList = ftList;
		this.webUser = webUser;
	}

	// 将封装好的对象添加进数据库
	public int addFollowList() {
		
		DBAccess dba = new DBAccess();
		for (int i = 0; i < this.ftList.size(); i++) {
			// 登录邮件检查如果失败，返回邮件错误
			if (!checkMail(webUser)) {
				return MAILFAIL;
			}
			// 知乎用户检查如果失败，返回关注错误
			if (!checkFollows(ftList.get(i).getFollow())) {
				return FOLLOWFAIL;
			}
			// 搜索关注表，如果知乎用户已被登录用户关注，返回关注错误
			if(checkFollowExist(ftList.get(i))){
				return FOLLOWEXIST;
			}
			// 添加数据到数据库
			if(!dba.setFollowTable(ftList.get(i))){
				return DBFAIL;
			}
		}
		this.webUser = dba.getWebUser();
		return SUCCESS;
	}

	// 检查知乎用户是否存在
	private boolean checkFollows(String follow) {
		String url = "https://www.zhihu.com/people/"+follow;
		// 使用Jsoup，通过url获取全部网页数据，如果链接无法打开，会抛出异常
		try {
			// 如果获取到的doc为空，则说明用户不存在
			if (Jsoup.connect(url).get() == null)
				return false;
		}
		// 如果打开链接出现异常，也说明用户不存在或存在其他异常
		catch (IOException e) {
			return false;
		}
		return true;
	}

	// 访问数据库检查登录用户名是否正确，防止bug
	private boolean checkMail(WebUser webUser) {
		DBAccess dbAcc = new DBAccess();
		if(dbAcc.checkExist(webUser))
		{
			return true;
		}
		return false;
	}
	
	// 检查输入的知乎用户名是否已经存在
	private boolean checkFollowExist(FollowTable ft) {
		DBAccess dbAcc = new DBAccess();
		if(dbAcc.checkExist(ft)){
			return true;
		}
		return false;
	}

	public WebUser getWebUser(){
		return this.webUser;
	}
}
