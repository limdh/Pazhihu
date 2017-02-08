package cn.lim.web.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ���Ǻ�̨���¼����
 * ����������Զ�����
 * ÿ��ʮ���ӣ���ȡһ�����ݿ��еĹ�ע��
 * ���ݹ�ע������ݣ����һ��֪���û������¶�̬
 * �����̬�и��£������ʼ�����
 * @author Lim
 *
 */
public class Listener  implements ServletContextListener {

	//������ʼ����̨����
	public void contextInitialized(ServletContextEvent arg0) {
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("...��̨����������...");
		System.out.println("...ÿ��ʮ����ִ��һ��...");
		System.out.println();
		System.out.println();
		System.out.println();
		Timer timer = new Timer(true);
		StartTimer st = new StartTimer();
		
		//�趨�����ʱ����Ϊÿ��ʮ���ӣ���ÿ����ʮ����ִ��һ��st��run
		timer.schedule(st,0,30*60*1000);
			
	}
	
	//�����Զ�������
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("���������٣�");
		System.out.println();
		System.out.println();
		System.out.println();
		
	}
	
}
