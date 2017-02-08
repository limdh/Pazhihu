package cn.lim.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.lim.domain.WebUser;
import cn.lim.service.DeleteFollowsService;

public class DeleteFollowsServlet extends HttpServlet {

	private static final long serialVersionUID = -1834340891928518566L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String mail = (String) request.getSession().getAttribute("username");
		String followName = request.getParameter("id");

		if (mail != null && mail != "" && followName != null && followName != "") {
			// �����ݴ��ݵ�Service�㣬ɾ���ù�ע�б��������µ��û���Ϣ������session�е�ֵ��ˢ��
			DeleteFollowsService dfs = new DeleteFollowsService();
			WebUser webUser = dfs.delete(mail, followName);
			if(webUser != null){
				request.getSession().setAttribute("follows", webUser.getFollows());
				// ������ת����������
				request.getRequestDispatcher("/myCenter.jsp").forward(request, response);
			}
			
		} else {
			if(mail == null || mail == ""){
				// ������ת����¼ҳ��
				request.setAttribute("msgLoginFail", "�����µ�¼�����");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}else{
				// ������ת����������
				request.setAttribute("myCenterErrorTip","���ڹ�ע�б���ѡ��Ҫɾ���Ķ���");
				request.getRequestDispatcher("/myCenter.jsp").forward(request, response);
			}
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
