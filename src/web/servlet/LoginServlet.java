package cn.lim.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lim.domain.WebUser;
import cn.lim.service.LoginService;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -254677114134427055L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		// ����������⣬����request�������ı���(post)���������ַ��ı���
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// �ݴ��û�������û������������֤����ʾ��Ϣ�����ڷ�������
		request.setAttribute("username", request.getParameter("username"));
		request.setAttribute("password", request.getParameter("password"));
		
		LoginService loginService = new LoginService();
		WebUser webUser= new WebUser();
		
		
		if(loginService.check(request.getParameter("username"), request.getParameter("password"))){
			webUser = loginService.getWebUser();
			request.getSession().setAttribute("username", webUser.getUsername());
			request.getSession().setAttribute("password", webUser.getPassword());
			request.getSession().setAttribute("follows", webUser.getFollows());
			request.getSession().setAttribute("regDate", webUser.getRegDate());
			
			// ������ת����ҳ
			response.sendRedirect(request.getContextPath()+"/myCenter.jsp");
		}
		else{
			// ������ʾ��Ϣ�����ڷ�������
			request.setAttribute("msgLoginFail", "�û������������");
			// �ض��򵽵�¼ҳ��
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
