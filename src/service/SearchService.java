package cn.lim.service;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.lim.domain.ZhihuUser;

public class SearchService {
	
	/**
	 * ��֪���Ĺ��̷���
	 * @param zhUsername
	 * @return
	 */
	public static ZhihuUser search(String zhUsername) {

		ZhihuUser targetUser = new ZhihuUser(zhUsername);
		
		// ʹ��Jsoup��ͨ��url��ȡ�����ҳ���ݣ���������޷��򿪣����׳��쳣
		Document doc = null;
		
		try {
			
			// ��ȡ�û�������Ϣ�����˳ɾͺͶ�̬ҳ��
			doc = Jsoup.connect(targetUser.getUserURL()+"/activities").get();
			// �����ȡ����doc��Ϊ�գ�����Ϊ��ȡ���������Ϣ
			if (doc != null) {
				sealBaseInfo(targetUser,doc);
				sealAchievements(targetUser,doc);
				sealActivities(targetUser,doc);
				doc = null;
			}
			
			// ��ȡ�û��Ļش�����ҳ��
			doc = Jsoup.connect(targetUser.getUserURL()+"/answers").get();
			// �����ȡ����doc��Ϊ�գ�����Ϊ��ȡ���������Ϣ
			if (doc != null) {
				sealAnswerInfo(targetUser,doc);
				doc = null;
			}
			
			// ��ȡ�û�������ҳ��
			doc = Jsoup.connect(targetUser.getUserURL()+"/asks").get();
			// �����ȡ����doc��Ϊ�գ�����Ϊ��ȡ���������Ϣ
			if (doc != null) {
				sealAsksInfo(targetUser,doc);
				doc = null;
			}
			
			
		}
		// ��������ӳ����쳣��˵���û������ڻ���֪���޷����ʣ����ؿ�
		catch (IOException e) {
			return null;
		}
		
		// ���ط�װ�õ�bean����
		return targetUser;
	}



	/**
	 * ��ȡ�û�������Ϣ�ķ���
	 * @param targetUser
	 * @param doc
	 */
	private static void sealBaseInfo(ZhihuUser targetUser, Document doc) {
		
		

		//��try-catch�ṹ����Ϊ������û���������˽���ʣ��ڻ�ȡ��Ϣʱ�ᷢ���쳣
		try{
		
			// ��ȡ֪�����û�������ҳ����,�������Ƿ�װ��bean
			targetUser.setZhihuName(doc.getElementsByClass("ProfileHeader-name").first().text());
			
			// ͨ��selectorѡ������ȡ��ע�ߺ͹�ע����,�������Ƿ�װ��bean
			targetUser.setFollowees(doc.getElementsByClass("NumberBoard-value").get(0).text());
			targetUser.setFollowers(doc.getElementsByClass("NumberBoard-value").get(1).text());
			
		}catch(Exception e){
			// �����ȡԪ�ػ�����Ϣʧ�ܣ�˵���û���������˽�������޷�ץȡ���κ���Ϣ��
			targetUser.setSecret(true);
		}
		
	}
	

	
	/**
	 * ��ȡ�û�������Ϣ�����˳ɾ͵ķ���
	 * @param targetUser
	 * @param doc
	 */
	private static void sealAchievements(ZhihuUser targetUser, Document doc) {
		
		//�������ڴ洢���˳ɾ͵��ַ���
		String achievements = "";
		
		
		//��try-catch�ṹ����Ϊ������û���������˽���ʣ��ڻ�ȡ��Ϣʱ�ᷢ���쳣
		try {
			
			// ��ȡ�û��ĸ��˳ɾ͵���������
			Element achs_el = doc.getElementsByClass("Profile-sideColumn").get(0);
			// ��ȡ��ͬ��
			String agreementsNum = achs_el.getElementsByClass("IconGraf").get(0).text();
			// ��ȡ��л���ղ���
			String thanksNum = achs_el.getElementsByClass("Profile-sideColumnItemValue").get(0).text();
			// ��ȡ�����༭������
			String editNum = achs_el.getElementsByClass("IconGraf").get(1).text();
			achievements = "<br />�� " + agreementsNum + "<br />�� " + thanksNum.substring(0, thanksNum.indexOf("��")) 
				+ "<br />�� ��� " + thanksNum.substring(thanksNum.indexOf("��")+1, thanksNum.length()) + "<br />�� " 
					+ editNum;
			targetUser.setAchievements(achievements);
			
		} catch (Exception e) {
			// �����ȡԪ�ؼ������쳣����˵���û�û�и��¶�̬��������������˽������
			achievements = "<br/>�� sorry��������������˽����...";
			targetUser.setAchievements(achievements);
		}
		
	}

	
	/**
	 * ��ȡ�û����¶�̬�ķ���
	 * @param targetUser
	 * @param doc
	 */
	private static void sealActivities(ZhihuUser targetUser, Document doc) {
		// ����������ʾ�����¶�̬
		String lastActions = "";
		
		// ���������ж��Ƿ���µĶ�̬����
		String newestAction = "";
		
		try {
			// ��ȡ�û������¶�̬��������̬����
			Element last_el = doc.getElementById("Profile-activities");
			// ��ȡ�����ڵ�ÿ��С�ֿ�
			Elements list_els = last_el.getElementsByClass("List-item");
			
			for (int i = 0; i < list_els.size(); i++) {
				// ��ȡÿ����̬�ĸ���ʱ��
				String time = list_els.get(i).getElementsByTag("span").get(1).text();

				// ��ȡÿ����̬�Ķ���
				String action = list_els.get(i).getElementsByTag("span").get(0).text();

				// ��ȡÿ����̬�ı���
				String title = list_els.get(i).getElementsByTag("a").get(0).text();

				// ��ȡÿ����̬������
				String link = list_els.get(i).getElementsByTag("a").get(0).attr("href");

				// �ж�������������·�������Ϊ����·��
				if(!link.startsWith("http")){
					link = "https://www.zhihu.com" + link;
				}
				
				//����̬��Ϣ��װ��html��ʽ���ַ���������洢�����
				lastActions = lastActions +  "<br/>�� " + time + ":&#9;" + action + ":&#9;" + title + "<a href = "
						+ link + ">...����</a>";
				
				//����̬������װ���ַ����������ж��Ƿ�������
				newestAction = newestAction + action + title;

				targetUser.setLast(lastActions);
				targetUser.setNewest(newestAction);
			}
			
		} catch (Exception e) {
			// �����ȡԪ�ؼ������쳣����˵���û�û�и��¶�̬��������������˽������
			lastActions = "<br/>�� sorry������û�����¶�̬������������˽����...";
			newestAction = "errorȨ�������޷��鿴";
			targetUser.setLast(lastActions);
			targetUser.setNewest(newestAction);
		}
		
	}
	

	
	/**
	 * ��ȡ�û���֪���ش�������Ϣ�ķ���
	 * @param targetUser
	 * @param doc
	 */
	private static void sealAnswerInfo(ZhihuUser targetUser, Document doc) {
		
		// ����������ʾ�Ļش�
		String answers = "";
		
		try{
			
			// ��ȡ�����ش������div����
			Element answers_el = doc.getElementById("Profile-answers");
			// ��ȡ�����ڵ�ÿ��С�ֿ�
			Elements list_els = answers_el.getElementsByClass("List-item");
			
			for (int i = 0; i < list_els.size(); i++) {
				
				// ��ȡÿ������ı���
				String title = list_els.get(i).getElementsByTag("a").get(0).text();
				
				// ��ȡÿ�����������
				String link = list_els.get(i).getElementsByTag("a").get(0).attr("href");
				// �ж�������������·�������Ϊ����·��
				if(!link.startsWith("http")){
					link = "https://www.zhihu.com" + link;
				}
				
				// ��ȡ��ͬ������try-catch����Ϊ���û���˵��ޣ���ȱʧ�ò��֣�jsoup�ᱨ���쳣
				String agreement = "";
				try{
					agreement = "��" + list_els.get(i).getElementsByClass("AnswerItem-extraInfo").get(0).text()+"��";
				}catch(Exception e){
					agreement = "�� ��ʱ��û���û����ޣ�";
				}
				
				//���ش����Ϣ��װ��html��ʽ���ַ���������洢�����
				answers = answers +  "<br/>�� " + title + ":&#9;" + agreement + "<a href = " + link + ">...����</a>";
				
			}
			
			targetUser.setAnswers(answers);
			
		}catch(Exception e){
			// �����ȡԪ�ؼ������쳣����˵���û�û�и��¶�̬��������������˽������
			answers = "<br/>�� ����û�лش��κ����⣬����������˽����...";
			targetUser.setAnswers(answers);
		}
		
		
	}
	

	/**
	 * ��ȡ�û���֪����������Ϣ�ķ���
	 * @param targetUser
	 * @param doc
	 */	
	private static void sealAsksInfo(ZhihuUser targetUser, Document doc) {
		
		// ����������ʾ������
		String asks = "";
		
		try{
			
			// ��ȡ���������������div����
			Element asks_el = doc.getElementById("Profile-asks");
			// ��ȡ�����ڵ�ÿ��С�ֿ�
			Elements list_els = asks_el.getElementsByClass("List-item");
			
			for (int i = 0; i < list_els.size(); i++) {
				
				// ��ȡÿ�����ʵ�ʱ��
				String time = list_els.get(i).getElementsByClass("ContentItem-statusItem").get(0).text();
				// ��ȡÿ�����ʵĻش���
				String answers = list_els.get(i).getElementsByClass("ContentItem-statusItem").get(1).text();
				// ��ȡÿ�����ʵĹ�ע��
				String follows = list_els.get(i).getElementsByClass("ContentItem-statusItem").get(2).text();
				
				// ��ȡÿ������ı���
				String title = list_els.get(i).getElementsByTag("a").get(0).text();
				
				// ��ȡÿ�����������
				String link = list_els.get(i).getElementsByTag("a").get(0).attr("href");
				// �ж�������������·�������Ϊ����·��
				if(!link.startsWith("http")){
					link = "https://www.zhihu.com" + link;
				}
				
				//�����ʵ���Ϣ��װ��html��ʽ���ַ���������洢�����
				asks = asks +  "<br/>�� " + time + ":&#9;" + title + "��"+ answers + " & " + follows +"��" 
						+ "<a href = " + link + ">...����</a>";
				
			}
			
			targetUser.setAsks(asks);
			
		}catch(Exception e){
			// �����ȡԪ�ؼ������쳣����˵���û�û�и��¶�̬��������������˽������
			asks = "<br/>�� ����û���κ����ʣ�����������˽����...";
			targetUser.setAsks(asks);
		}
		
	}


}
