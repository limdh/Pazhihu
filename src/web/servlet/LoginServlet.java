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
		

		// 解决乱码问题，设置request缓冲区的编码(post)，并设置字符的编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// 暂存用户输入的用户名、密码和验证码提示信息，用于返回至表单
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
			
			// 设置跳转到首页
			response.sendRedirect(request.getContextPath()+"/myCenter.jsp");
		}
		else{
			// 设置提示信息，用于返回至表单
			request.setAttribute("msgLoginFail", "用户名或密码错误");
			// 重定向到登录页面
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
