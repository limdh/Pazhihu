package cn.lim.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lim.domain.FollowTable;
import cn.lim.domain.WebUser;
import cn.lim.service.SetFollowsService;

public class SetFollowsServlet extends HttpServlet {

	private static final long serialVersionUID = 5568963891071149606L;

	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Ԥ���������⣬����request�������ı���(post)���������ַ��ı���
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// ��ȡ�û����������ã�����û���ѡ�˽����ʼ����򽫷��ͱ�ʶ����Ϊtrue
		boolean isSend = false;
		if(request.getParameter("mailSend") != null){
			isSend = true;
		}
		
		// ��ȡҪ��ע���û����ж��û��Ƿ��������û�
		String[] follows;
		if(request.getParameter("follows").indexOf(",") != -1){
			follows = request.getParameter("follows").split(",");
		}else{
			follows = new String[]{request.getParameter("follows")};
		}
		
		// ��ȡ��ǰ��¼�û�����
		WebUser webUser = new WebUser();
		webUser.setUsername((String) request.getSession().getAttribute("username"));
		webUser.setPassword((String) request.getSession().getAttribute("password"));
		webUser.setFollows((HashMap<String,String>) request.getSession().getAttribute("follows"));
		webUser.setRegDate((String) request.getSession().getAttribute("regDate"));
		
		//��֤�û��Ƿ��¼�����δ��¼��ת������¼ҳ��
		String mail = webUser.getUsername();
		if(mail == "" || mail == null){
			// ������ʾ��Ϣ�����ڷ�������
			request.setAttribute("msgLoginFail", "���¼�����");
			// ת������¼ҳ��
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
		
		// ��ȡ��ǰ���ڡ�
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());
		
		
		// �����ݷ�װ������������
		ArrayList<FollowTable> ftList = new ArrayList<FollowTable>();
		for (int i = 0; i < follows.length; i++) {
			ftList.add(new FollowTable(mail, follows[i], isSend, date));
		}
		
		
		// �������������ݵ�service�㴦�������ش�����
		SetFollowsService setService = new SetFollowsService(ftList, webUser);
		int flag = setService.addFollowList();
		
		if(flag == SetFollowsService.MAILFAIL){
			//���ݴ�����Ϣ��ת������¼ҳ��
			request.setAttribute("msgLoginFail", "��¼������������µ�¼");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else if(flag == SetFollowsService.FOLLOWFAIL){
			//���ݴ�����Ϣ��ת������������ҳ��
			request.setAttribute("msgFollows", "��֪���û������ڣ���ȷ��������������֪��");
			request.getRequestDispatcher("/setFollows.jsp").forward(request, response);
		}else if(flag == SetFollowsService.DBFAIL){
			//���ݴ�����Ϣ��ת������������ҳ��
			request.setAttribute("msgFollows", "���ݿ�������Ժ����Ի���ϵ��վ����Ա");
			request.getRequestDispatcher("/setFollows.jsp").forward(request, response);
		}else if(flag == SetFollowsService.FOLLOWEXIST){
			//���ݴ�����Ϣ��ת������������ҳ��
			request.setAttribute("msgFollows", "�ύ���б����й�ע�����û�");
			request.getRequestDispatcher("/setFollows.jsp").forward(request, response);
		}else if(flag == SetFollowsService.SUCCESS){
			//��ע�ɹ�����session�е�follows��������
			webUser = setService.getWebUser();
			request.getSession().setAttribute("follows", webUser.getFollows());
			//ת������������ҳ��
			request.getRequestDispatcher("/myCenter.jsp").forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
