package cn.lim.domain;

/**
 * 数据库中关注表对应的对象类
 * @author Lim
 *
 */
public class FollowTable {
	private String mail;
	private String follow;
	private boolean isSend;
	private String followDate;
	
	/**
	 * 关注表的每一行需要四条内容，在创建对象时必须指明它们的值
	 * @param mail
	 * @param follow
	 * @param isSend
	 * @param followDate
	 */
	public FollowTable(String mail, String follow, boolean isSend, String followDate) {
		super();
		this.mail = mail;
		this.follow = follow;
		this.setSend(isSend);
		this.followDate = followDate;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFollow() {
		return follow;
	}

	public void setFollow(String follow) {
		this.follow = follow;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}
	
	public String getFollowDate() {
		return followDate;
	}

	public void setFollowDate(String followDate) {
		this.followDate = followDate;
	}

	@Override
	public String toString() {
		return "FollowTable [mail=" + mail + ", follow=" + follow + ", isSend=" + isSend + ", followDate="
				+ followDate + "]";
	}
	
}
