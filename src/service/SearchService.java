package cn.lim.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.lim.domain.ZhihuUser;

public class SearchService {
	
	/**
	 * 爬知乎的过程方法
	 * @param zhUsername
	 * @return
	 */
	public static ZhihuUser search(String zhUsername) {

		ZhihuUser targetUser = new ZhihuUser(zhUsername);
		
		// 使用Jsoup，通过url获取相关网页数据，如果链接无法打开，会抛出异常
		Document doc = null;
		
		try {
			
			// 获取用户基本信息，个人成就和动态页面
			doc = Jsoup.connect(targetUser.getUserURL()+"/activities").get();
			// 如果获取到的doc不为空，则认为获取到了相关信息
			if (doc != null) {
				sealBaseInfo(targetUser,doc);
				sealAchievements(targetUser,doc);
				sealActivities(targetUser,doc);
				doc = null;
			}
			
			// 获取用户的回答问题页面
			doc = Jsoup.connect(targetUser.getUserURL()+"/answers").get();
			// 如果获取到的doc不为空，则认为获取到了相关信息
			if (doc != null) {
				sealAnswerInfo(targetUser,doc);
				doc = null;
			}
			
			// 获取用户的提问页面
			doc = Jsoup.connect(targetUser.getUserURL()+"/asks").get();
			// 如果获取到的doc不为空，则认为获取到了相关信息
			if (doc != null) {
				sealAsksInfo(targetUser,doc);
				doc = null;
			}
			
			
		}
		// 如果打开链接出现异常，说明用户不存在或者知乎无法访问，返回空
		catch (IOException e) {
			return null;
		}
		
		// 返回封装好的bean对象
		return targetUser;
	}



	/**
	 * 获取用户基本信息的方法
	 * @param targetUser
	 * @param doc
	 */
	private static void sealBaseInfo(ZhihuUser targetUser, Document doc) {
		
		

		//用try-catch结构是因为：如果用户设置了隐私访问，在获取信息时会发生异常
		try{
		
			// 获取知乎的用户名和主页链接,并将它们封装进bean
			targetUser.setZhihuName(doc.getElementsByClass("ProfileHeader-name").first().text());
			
			// 通过selector选择器获取关注者和关注人数,并将它们封装进bean
			targetUser.setFollowees(doc.getElementsByClass("NumberBoard-value").get(0).text());
			targetUser.setFollowers(doc.getElementsByClass("NumberBoard-value").get(1).text());
			
		}catch(Exception e){
			// 如果获取元素基本信息失败，说明用户设置了隐私保护，无法抓取到任何信息；
			targetUser.setSecret(true);
		}
		
	}
	

	
	/**
	 * 获取用户基本信息，个人成就的方法
	 * @param targetUser
	 * @param doc
	 */
	private static void sealAchievements(ZhihuUser targetUser, Document doc) {
		
		//定义用于存储个人成就的字符串
		String achievements = "";
		
		
		//用try-catch结构是因为：如果用户设置了隐私访问，在获取信息时会发生异常
		try {
			
			// 获取用户的个人成就的整个区域
			Element achs_el = doc.getElementsByClass("Profile-sideColumn").get(0);
			// 获取赞同数
			String agreementsNum = achs_el.getElementsByClass("IconGraf").get(0).text();
			// 获取感谢及收藏数
			String thanksNum = achs_el.getElementsByClass("Profile-sideColumnItemValue").get(0).text();
			// 获取公共编辑参与数
			String editNum = achs_el.getElementsByClass("IconGraf").get(1).text();
			achievements = "<br />· " + agreementsNum + "<br />· " + thanksNum.substring(0, thanksNum.indexOf("，")) 
				+ "<br />· 获得 " + thanksNum.substring(thanksNum.indexOf("，")+1, thanksNum.length()) + "<br />· " 
					+ editNum;
			targetUser.setAchievements(achievements);
			
		} catch (Exception e) {
			// 如果获取元素集发生异常，则说明用户没有更新动态，或者设置了隐私保护；
			achievements = "<br/>· sorry，主人设置了隐私保护...";
			targetUser.setAchievements(achievements);
		}
		
	}

	
	/**
	 * 获取用户最新动态的方法
	 * @param targetUser
	 * @param doc
	 */
	private static void sealActivities(ZhihuUser targetUser, Document doc) {
		// 定义用于显示的最新动态
		String lastActions = "";
		
		// 定义用于判断是否更新的动态标题
		String newestAction = "";
		
		try {
			// 获取用户的最新动态的整个动态区域
			Element last_el = doc.getElementById("Profile-activities");
			// 获取区域内的每个小分块
			Elements list_els = last_el.getElementsByClass("List-item");
			
			for (int i = 0; i < list_els.size(); i++) {
				// 获取每条动态的更新时间
				String time = list_els.get(i).getElementsByTag("span").get(1).text();

				// 获取每条动态的动作
				String action = list_els.get(i).getElementsByTag("span").get(0).text();

				// 获取每条动态的标题
				String title = list_els.get(i).getElementsByTag("a").get(0).text();

				// 获取每条动态的链接
				String link = list_els.get(i).getElementsByTag("a").get(0).attr("href");

				// 判断如果链接是相对路径，则改为绝对路径
				if(!link.startsWith("http")){
					link = "https://www.zhihu.com" + link;
				}
				
				//将动态信息组装成html格式的字符串，方便存储及输出
				lastActions = lastActions +  "<br/>· " + time + ":&#9;" + action + ":&#9;" + title + "<a href = "
						+ link + ">...详情</a>";
				
				//将动态标题组装成字符串，用于判断是否发生更新
				newestAction = newestAction + action + title;

				targetUser.setLast(lastActions);
				targetUser.setNewest(newestAction);
			}
			
		} catch (Exception e) {
			// 如果获取元素集发生异常，则说明用户没有更新动态，或者设置了隐私保护；
			lastActions = "<br/>· sorry，主人没有最新动态，或设置了隐私保护...";
			newestAction = "error权限问题无法查看";
			targetUser.setLast(lastActions);
			targetUser.setNewest(newestAction);
		}
		
	}
	

	
	/**
	 * 获取用户在知乎回答问题信息的方法
	 * @param targetUser
	 * @param doc
	 */
	private static void sealAnswerInfo(ZhihuUser targetUser, Document doc) {
		
		// 定义用于显示的回答
		String answers = "";
		
		try{
			
			// 获取到整回答区域的div容器
			Element answers_el = doc.getElementById("Profile-answers");
			// 获取区域内的每个小分块
			Elements list_els = answers_el.getElementsByClass("List-item");
			
			for (int i = 0; i < list_els.size(); i++) {
				
				// 获取每个问题的标题
				String title = list_els.get(i).getElementsByTag("a").get(0).text();
				
				// 获取每个问题的链接
				String link = list_els.get(i).getElementsByTag("a").get(0).attr("href");
				// 判断如果链接是相对路径，则改为绝对路径
				if(!link.startsWith("http")){
					link = "https://www.zhihu.com" + link;
				}
				
				// 获取赞同数，用try-catch是因为如果没有人点赞，会缺失该部分，jsoup会报出异常
				String agreement = "";
				try{
					agreement = "（" + list_els.get(i).getElementsByClass("AnswerItem-extraInfo").get(0).text()+"）";
				}catch(Exception e){
					agreement = "（ 暂时还没有用户点赞）";
				}
				
				//将回答的信息组装成html格式的字符串，方便存储及输出
				answers = answers +  "<br/>· " + title + ":&#9;" + agreement + "<a href = " + link + ">...详情</a>";
				
			}
			
			targetUser.setAnswers(answers);
			
		}catch(Exception e){
			// 如果获取元素集发生异常，则说明用户没有更新动态，或者设置了隐私保护；
			answers = "<br/>· 主人没有回答任何问题，或设置了隐私保护...";
			targetUser.setAnswers(answers);
		}
		
		
	}
	

	/**
	 * 获取用户在知乎的提问信息的方法
	 * @param targetUser
	 * @param doc
	 */	
	private static void sealAsksInfo(ZhihuUser targetUser, Document doc) {
		
		// 定义用于显示的提问
		String asks = "";
		
		try{
			
			// 获取到整个提问区域的div容器
			Element asks_el = doc.getElementById("Profile-asks");
			// 获取区域内的每个小分块
			Elements list_els = asks_el.getElementsByClass("List-item");
			
			for (int i = 0; i < list_els.size(); i++) {
				
				// 获取每个提问的时间
				String time = list_els.get(i).getElementsByClass("ContentItem-statusItem").get(0).text();
				// 获取每个提问的回答数
				String answers = list_els.get(i).getElementsByClass("ContentItem-statusItem").get(1).text();
				// 获取每个提问的关注度
				String follows = list_els.get(i).getElementsByClass("ContentItem-statusItem").get(2).text();
				
				// 获取每个问题的标题
				String title = list_els.get(i).getElementsByTag("a").get(0).text();
				
				// 获取每个问题的链接
				String link = list_els.get(i).getElementsByTag("a").get(0).attr("href");
				// 判断如果链接是相对路径，则改为绝对路径
				if(!link.startsWith("http")){
					link = "https://www.zhihu.com" + link;
				}
				
				//将提问的信息组装成html格式的字符串，方便存储及输出
				asks = asks +  "<br/>· " + time + ":&#9;" + title + "（"+ answers + " & " + follows +"）" 
						+ "<a href = " + link + ">...详情</a>";
				
			}
			
			targetUser.setAsks(asks);
			
		}catch(Exception e){
			// 如果获取元素集发生异常，则说明用户没有更新动态，或者设置了隐私保护；
			asks = "<br/>· 主人没有任何提问，或设置了隐私保护...";
			targetUser.setAsks(asks);
		}
		
	}


}
