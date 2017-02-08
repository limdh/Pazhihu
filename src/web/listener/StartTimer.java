package cn.lim.web.listener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimerTask;

import cn.lim.dao.DBListener;
import cn.lim.domain.ZhihuUser;
import cn.lim.service.SearchService;
import cn.lim.utils.MailUtils;

public class StartTimer extends TimerTask {
	
	// 定义存放更新表的变量，由于要存放知乎用户和发送邮箱一对数据，所以设置为map类型
	// Map中，key是关注的知乎用户，value是邮箱地址
	private HashMap<String, String> sendList = new HashMap<String, String>();
	
	// 获取时间数据，方便记录及调试
	private Calendar cal = Calendar.getInstance();

	private DBListener dbl = new DBListener();
	
	@Override
	public void run() {
		
		System.out.println();
		System.out.println();
		System.out.println(cal.getTime()+" - 定时后台任务已启动！");
		System.out.println();
		System.out.println();
		
		
		// 通过数据库中的关注表，获取邮件提醒设置为yes的用户邮箱，以及对应的关注知乎帐号，放进map容器
		sendList = dbl.getSendList();

		System.out.println("------------"+cal.getTime()+"-----------------");
		System.out.println();
		System.out.println();
		
		// 遍历关注的知乎帐号，和接收提醒的邮箱
		for(String zhUserName:sendList.keySet()){
			
			// 抓取列表中知乎用户的最新动态
			ZhihuUser zhUser = SearchService.search(zhUserName);
			
			// 获取数据库动态表中保存的用户动态
			String newestInDB = dbl.getNewest(zhUserName);
			
			// 通过对比刚抓取的数据和存在数据库中的数据，判断用户动态是否更新
			// 有更新，将抓取到的最新动态存放到数据库的动态表，并发送提醒邮件到相应的邮箱
			if(!zhUser.getNewest().equalsIgnoreCase(newestInDB)){
				System.out.println();
				System.out.println("***************");
				System.out.println(zhUser.getZhihuName()+": 用户存在更新: ");
				System.out.println();

				//将最新信息存入数据库中
				if(dbl.setActivities(zhUser,zhUserName,newestInDB)){
					System.out.println(zhUserName + "：最新数据已存入数据库");
				}
				else{
					System.out.println(zhUserName + "：数据库操作出错");
				}
				
				//发送邮件给相应联系人
				//有更新的情况下，两种情况不发送邮件：1.设置了访问权限；2.新关注的用户，且需第一次保存进数据库
				if(zhUser.isSecret()){
					System.out.println();
					System.out.println(zhUserName + "：权限问题！无需发送动态更新邮件...");
				}
				
				else if(zhUserName == null){
					System.out.println();
					System.out.println(zhUserName + "：新关注的用户！无需发送动态更新邮件...");
				}
				
				else{
					System.out.println();
					System.out.println(zhUserName + "：开始发送最新信息邮件...");
					
					// 组织邮件正文
					String text = "您好，您关注的用户 "+zhUser.getZhihuName() + " 最近 " +zhUser.getNewest()+
							"<br /> <a href = "+ zhUser.getUserURL()+"/activities>" + "详情</a>";
				
					// 判断是否有多个用户邮箱关注该知乎用户，分离邮箱地址
					if(sendList.get(zhUserName).contains(",")){
						String[] mails = sendList.get(zhUserName).split(",");
						for (int i = 0; i < mails.length; i++) {
							if(MailUtils.sendMail(mails[i], text)){
								System.out.println(zhUserName + "：" + mails[i]+"，发送成功！");
							}else{
								System.out.println(zhUserName + "：" + mails[i]+"，发送失败！");
							}
							
							
						}
					}
					//如果只有一个用户关注该知乎用户，直接发送邮件到相应地址
					else{
						if(MailUtils.sendMail(sendList.get(zhUserName), text)){
							System.out.println(zhUserName + "：" + sendList.get(zhUserName)+"，发送成功！");
						}else{
							System.out.println(zhUserName + "：" + sendList.get(zhUserName)+"，发送失败！");
						}
					}
				}
			}
			// 没有更新，则不进行任何操作
			else{
				System.out.println();
				System.out.println(zhUser.getZhihuName()+": 用户没有更新...");
			}
			System.out.println();
			System.out.println("***************");
			System.out.println();
		}
			
	}
		

}
