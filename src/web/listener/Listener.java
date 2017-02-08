package cn.lim.web.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 这是后台更新检查类
 * 跟随服务器自动启动
 * 每隔十分钟，读取一次数据库中的关注表
 * 根据关注表的数据，检查一次知乎用户的最新动态
 * 如果动态有更新，发送邮件提醒
 * @author Lim
 *
 */
public class Listener  implements ServletContextListener {

	//启动初始化后台任务
	public void contextInitialized(ServletContextEvent arg0) {
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("...后台程序已启动...");
		System.out.println("...每三十分钟执行一次...");
		System.out.println();
		System.out.println();
		System.out.println();
		Timer timer = new Timer(true);
		StartTimer st = new StartTimer();
		
		//设定任务的时间间隔为每三十分钟，即每隔三十分钟执行一次st的run
		timer.schedule(st,0,30*60*1000);
			
	}
	
	//销毁自动化任务
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("任务已销毁！");
		System.out.println();
		System.out.println();
		System.out.println();
		
	}
	
}
