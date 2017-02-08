package cn.lim.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtils {

	/**
	 * �����ʼ�������
	 * @param address ���ܷ��ʼ���ַ 
	 * @param text �ʼ�������
	 * @return �����Ƿ��ͳɹ��ı�־
	 */
	
	public static boolean sendMail(String address,String text){

		try{
			
			// ���û�������
			Properties props = new Properties();
			// ����������ʹ���ҵ�163���䷢����Ϣ
			props.setProperty("mail.host", "smtp.sina.com");
			
			// �趨ʹ��Ȩ����֤
			props.setProperty("mail.smtp.auth", "true");
			// �趨�˺�������
			Authenticator authenticator = new Authenticator(){
				@Override
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("pazhihu@sina.com","1234qwer");
				}
			};
			
			// �������
			Session session = Session.getDefaultInstance(props, authenticator);
			
			// �����ʼ�����
			Message message = new MimeMessage(session);
			// ���÷����˵�ַ
			message.setFrom(new InternetAddress("pazhihu@sina.com"));
			
			// �����ռ��ˣ�to:�ռ���   cc:����   bcc:����
			message.setRecipient(RecipientType.TO, new InternetAddress(address));
			// ��������
			message.setSubject("����ע���û��и����������� ��֪��");
			// ��������
			message.setContent(text, "text/html;charset=UTF-8");
		    
			//������Ϣ
			Transport.send(message);
	        return true;
	        
		}catch(Exception e)	{
			return false;
		}
		
		
	}
	
}
