package cn.lim.web.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lim.domain.FollowTable;
import cn.lim.domain.WebUser;
import cn.lim.service.EditFollowsService;

public class EditFollowsServlet extends HttpServlet {

	private static final long serialVersionUID = 7502807024444735767L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		// Ԥ���������⣬����request�������ı���(post)���������ַ��ı���
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//��ȡ��ǰ��¼�û�
		String mail = (String) request.getSession().getAttribute("username");
		
		//����û�δ��¼�����ض��򵽵�¼ҳ�棬����ʾ����
		if(mail == null || mail ==""){
			// ������ת����¼ҳ��
			request.setAttribute("msgLoginFail", "�����µ�¼�����");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		
		//��ȡҪ�޸ĵĹ�ע��
		String followName = request.getParameter("id");
		
		// ��ȡ��ǰ���ڡ�
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());
		
		// ��ȡ�û����������ã�����û���ѡ�˽����ʼ����򽫷��ͱ�ʶ����Ϊtrue
		boolean isSend = false;
		if(request.getParameter("mailSend") != null){
			isSend = true;
		}
		
		// ���δ��ȡ���û���Ϣ�����ظ������Ĳ���ʾ��ʾ
		if(followName == null || followName == "" ||date == null || date ==""){
			// ������ת����������
			request.setAttribute("myCenterErrorTip","���ڹ�ע�б���ѡ��Ҫ�޸ĵĶ���");
			request.getRequestDispatcher("/myCenter.jsp").forward(request, response);
			return;
		}
		
		//�����ݷ�װ�����ݵ�service����д���
		FollowTable ft = new FollowTable(mail, followName, isSend, date);
		EditFollowsService efs = new EditFollowsService();
		WebUser webUser = efs.editFollow(ft);
		//��������ɹ������ظ��µ��û���ע�б�
		if(webUser!=null){
			request.getSession().setAttribute("follows", webUser.getFollows());
			// ������ת����������
			response.getWriter().write("<script>window.opener.location.replace('myCenter.jsp');self.close();</script>");
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
