package cn.lim.domain;

public class ZhihuUser {

	// 知乎用户名
	private String zhihuName;
	// 知乎首页链接
	private String userURL;
	// 知乎关注人数
	private String followees;
	// 知乎被关注人数
	private String followers;
	// 在知乎上的个人成就
	private String achievements;
	// 该用户在知乎的动态，用于显示
	private String last;
	// 该用户在知乎的动态，用于判断
	private String newest;
	// 在知乎上回答的问题
	private String answers;
	// 在知乎上的提问
	private String asks;
	// 在知乎上的隐私设置
	private boolean secret = false;
	
	// 传入知乎用户名，自动生成链接
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
