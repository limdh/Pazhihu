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

	// ����װ�õĶ�����ӽ����ݿ�
	public int addFollowList() {
		
		DBAccess dba = new DBAccess();
		for (int i = 0; i < this.ftList.size(); i++) {
			// ��¼�ʼ�������ʧ�ܣ������ʼ�����
			if (!checkMail(webUser)) {
				return MAILFAIL;
			}
			// ֪���û�������ʧ�ܣ����ع�ע����
			if (!checkFollows(ftList.get(i).getFollow())) {
				return FOLLOWFAIL;
			}
			// ������ע�����֪���û��ѱ���¼�û���ע�����ع�ע����
			if(checkFollowExist(ftList.get(i))){
				return FOLLOWEXIST;
			}
			// ������ݵ����ݿ�
			if(!dba.setFollowTable(ftList.get(i))){
				return DBFAIL;
			}
		}
		this.webUser = dba.getWebUser();
		return SUCCESS;
	}

	// ���֪���û��Ƿ����
	private boolean checkFollows(String follow) {
		String url = "https://www.zhihu.com/people/"+follow;
		// ʹ��Jsoup��ͨ��url��ȡȫ����ҳ���ݣ���������޷��򿪣����׳��쳣
		try {
			// �����ȡ����docΪ�գ���˵���û�������
			if (Jsoup.connect(url).get() == null)
				return false;
		}
		// ��������ӳ����쳣��Ҳ˵���û������ڻ���������쳣
		catch (IOException e) {
			return false;
		}
		return true;
	}

	// �������ݿ����¼�û����Ƿ���ȷ����ֹbug
	private boolean checkMail(WebUser webUser) {
		DBAccess dbAcc = new DBAccess();
		if(dbAcc.checkExist(webUser))
		{
			return true;
		}
		return false;
	}
	
	// ��������֪���û����Ƿ��Ѿ�����
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
