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
			// 将数据传递到Service层，删除该关注列表，并返回新的用户信息，更新session中的值并刷新
			DeleteFollowsService dfs = new DeleteFollowsService();
			WebUser webUser = dfs.delete(mail, followName);
			if(webUser != null){
				request.getSession().setAttribute("follows", webUser.getFollows());
				// 设置跳转到个人中心
				request.getRequestDispatcher("/myCenter.jsp").forward(request, response);
			}
			
		} else {
			if(mail == null || mail == ""){
				// 设置跳转到登录页面
				request.setAttribute("msgLoginFail", "请重新登录后操作");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}else{
				// 设置跳转到个人中心
				request.setAttribute("myCenterErrorTip","请在关注列表里选择要删除的对象！");
				request.getRequestDispatcher("/myCenter.jsp").forward(request, response);
			}
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
