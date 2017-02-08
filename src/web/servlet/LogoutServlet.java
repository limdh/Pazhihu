package cn.lim.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 5248109218023983341L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);//∑¿÷π¥¥Ω®Session
		if(session == null){
			response.sendRedirect(request.getContextPath());
			return;
		}
		session.removeAttribute("username");
		session.removeAttribute("password");
		session.removeAttribute("follows");
		session.removeAttribute("regDate");
		
		response.sendRedirect(request.getContextPath());
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
