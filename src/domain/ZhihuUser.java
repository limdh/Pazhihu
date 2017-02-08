package cn.lim.domain;

public class ZhihuUser {

	// ֪���û���
	private String zhihuName;
	// ֪����ҳ����
	private String userURL;
	// ֪����ע����
	private String followees;
	// ֪������ע����
	private String followers;
	// ��֪���ϵĸ��˳ɾ�
	private String achievements;
	// ���û���֪���Ķ�̬��������ʾ
	private String last;
	// ���û���֪���Ķ�̬�������ж�
	private String newest;
	// ��֪���ϻش������
	private String answers;
	// ��֪���ϵ�����
	private String asks;
	// ��֪���ϵ���˽����
	private boolean secret = false;
	
	// ����֪���û������Զ���������
	public ZhihuUser(String zhihuName) {
		this.zhihuName = zhihuName;
		this.userURL = "https://www.zhihu.com/people/" + zhihuName;
	}

	public String getUserURL() {
		return userURL;
	}

	public String getZhihuName() {
		return zhihuName;
	}

	public void setZhihuName(String zhihuName) {
		this.zhihuName = zhihuName;
	}

	public String getFollowees() {
		return followees;
	}

	public void setFollowees(String string) {
		this.followees = string;
	}

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}

	public String getAchievements() {
		return achievements;
	}

	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
	
	public String getNewest() {
		return newest;
	}

	public void setNewest(String newest) {
		this.newest = newest;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}
	
	public String getAsks() {
		return asks;
	}

	public void setAsks(String asks) {
		this.asks = asks;
	}
	
	public boolean isSecret() {
		return secret;
	}

	public void setSecret(boolean secret) {
		this.secret = secret;
	}


}
