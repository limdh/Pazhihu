package cn.lim.domain;

import java.util.HashMap;

//ÍøÕ¾µÄ×¢²áÓÃ»§bean
public class WebUser {

	private String username;
	private String password;
	private HashMap<String, String> follows;
	private String regDate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public HashMap<String, String> getFollows() {
		return follows;
	}

	public void setFollows(HashMap<String, String> follows) {
		this.follows = follows;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "WebUser [username=" + username + ", password=" + password + ", follows=" + follows + ", regDate="
				+ regDate + "]";
	}

}
