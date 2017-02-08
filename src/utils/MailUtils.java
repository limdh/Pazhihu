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
	 * 这是邮件发送类
	 * @param address 接受方邮件地址 
	 * @param text 邮件的正文
	 * @return 返回是否发送成功的标志
	 */
	
	public static boolean sendMail(String address,String text){

		try{
			
			// 设置基本参数
			Properties props = new Properties();
			// 设置主机，使用我的163邮箱发送信息
			props.setProperty("mail.host", "smtp.sina.com");
			
			// 设定使用权限验证
			props.setProperty("mail.smtp.auth", "true");
			// 设定账号与密码
			Authenticator authenticator = new Authenticator(){
				@Override
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("pazhihu@sina.com","1234qwer");
				}
			};
			
			// 获得连接
			Session session = Session.getDefaultInstance(props, authenticator);
			
			// 创建邮件对象
			Message message = new MimeMessage(session);
			// 设置发件人地址
			message.setFrom(new InternetAddress("pazhihu@sina.com"));
			
			// 设置收件人，to:收件人   cc:抄送   bcc:暗送
			message.setRecipient(RecipientType.TO, new InternetAddress(address));
			// 设置主题
			message.setSubject("您关注的用户有更新啦！—— 爬知乎");
			// 设置正文
			message.setContent(text, "text/html;charset=UTF-8");
		    
			//发送消息
			Transport.send(message);
	        return true;
	        
		}catch(Exception e)	{
			return false;
		}
		
		
	}
	
}
