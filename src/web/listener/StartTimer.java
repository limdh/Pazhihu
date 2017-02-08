package cn.lim.web.listener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimerTask;

import cn.lim.dao.DBListener;
import cn.lim.domain.ZhihuUser;
import cn.lim.service.SearchService;
import cn.lim.utils.MailUtils;

public class StartTimer extends TimerTask {
	
	// �����Ÿ��±�ı���������Ҫ���֪���û��ͷ�������һ�����ݣ���������Ϊmap����
	// Map�У�key�ǹ�ע��֪���û���value�������ַ
	private HashMap<String, String> sendList = new HashMap<String, String>();
	
	// ��ȡʱ�����ݣ������¼������
	private Calendar cal = Calendar.getInstance();

	private DBListener dbl = new DBListener();
	
	@Override
	public void run() {
		
		System.out.println();
		System.out.println();
		System.out.println(cal.getTime()+" - ��ʱ��̨������������");
		System.out.println();
		System.out.println();
		
		
		// ͨ�����ݿ��еĹ�ע����ȡ�ʼ���������Ϊyes���û����䣬�Լ���Ӧ�Ĺ�ע֪���ʺţ��Ž�map����
		sendList = dbl.getSendList();

		System.out.println("------------"+cal.getTime()+"-----------------");
		System.out.println();
		System.out.println();
		
		// ������ע��֪���ʺţ��ͽ������ѵ�����
		for(String zhUserName:sendList.keySet()){
			
			// ץȡ�б���֪���û������¶�̬
			ZhihuUser zhUser = SearchService.search(zhUserName);
			
			// ��ȡ���ݿ⶯̬���б�����û���̬
			String newestInDB = dbl.getNewest(zhUserName);
			
			// ͨ���Աȸ�ץȡ�����ݺʹ������ݿ��е����ݣ��ж��û���̬�Ƿ����
			// �и��£���ץȡ�������¶�̬��ŵ����ݿ�Ķ�̬�������������ʼ�����Ӧ������
			if(!zhUser.getNewest().equalsIgnoreCase(newestInDB)){
				System.out.println();
				System.out.println("***************");
				System.out.println(zhUser.getZhihuName()+": �û����ڸ���: ");
				System.out.println();

				//��������Ϣ�������ݿ���
				if(dbl.setActivities(zhUser,zhUserName,newestInDB)){
					System.out.println(zhUserName + "�����������Ѵ������ݿ�");
				}
				else{
					System.out.println(zhUserName + "�����ݿ��������");
				}
				
				//�����ʼ�����Ӧ��ϵ��
				//�и��µ�����£���������������ʼ���1.�����˷���Ȩ�ޣ�2.�¹�ע���û��������һ�α�������ݿ�
				if(zhUser.isSecret()){
					System.out.println();
					System.out.println(zhUserName + "��Ȩ�����⣡���跢�Ͷ�̬�����ʼ�...");
				}
				
				else if(zhUserName == null){
					System.out.println();
					System.out.println(zhUserName + "���¹�ע���û������跢�Ͷ�̬�����ʼ�...");
				}
				
				else{
					System.out.println();
					System.out.println(zhUserName + "����ʼ����������Ϣ�ʼ�...");
					
					// ��֯�ʼ�����
					String text = "���ã�����ע���û� "+zhUser.getZhihuName() + " ��� " +zhUser.getNewest()+
							"<br /> <a href = "+ zhUser.getUserURL()+"/activities>" + "����</a>";
				
					// �ж��Ƿ��ж���û������ע��֪���û������������ַ
					if(sendList.get(zhUserName).contains(",")){
						String[] mails = sendList.get(zhUserName).split(",");
						for (int i = 0; i < mails.length; i++) {
							if(MailUtils.sendMail(mails[i], text)){
								System.out.println(zhUserName + "��" + mails[i]+"�����ͳɹ���");
							}else{
								System.out.println(zhUserName + "��" + mails[i]+"������ʧ�ܣ�");
							}
							
							
						}
					}
					//���ֻ��һ���û���ע��֪���û���ֱ�ӷ����ʼ�����Ӧ��ַ
					else{
						if(MailUtils.sendMail(sendList.get(zhUserName), text)){
							System.out.println(zhUserName + "��" + sendList.get(zhUserName)+"�����ͳɹ���");
						}else{
							System.out.println(zhUserName + "��" + sendList.get(zhUserName)+"������ʧ�ܣ�");
						}
					}
				}
			}
			// û�и��£��򲻽����κβ���
			else{
				System.out.println();
				System.out.println(zhUser.getZhihuName()+": �û�û�и���...");
			}
			System.out.println();
			System.out.println("***************");
			System.out.println();
		}
			
	}
		

}
