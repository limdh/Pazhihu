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


		// 预防乱码问题，设置request缓冲区的编码(post)，并设置字符的编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//获取当前登录用户
		String mail = (String) request.getSession().getAttribute("username");
		
		//如果用户未登录，则重定向到登录页面，并提示错误
		if(mail == null || mail ==""){
			// 设置跳转到登录页面
			request.setAttribute("msgLoginFail", "请重新登录后操作");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		
		//获取要修改的关注名
		String followName = request.getParameter("id");
		
		// 获取当前日期。
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());
		
		// 获取用户的提醒设置，如果用户勾选了接收邮件，则将发送标识设置为true
		boolean isSend = false;
		if(request.getParameter("mailSend") != null){
			isSend = true;
		}
		
		// 如果未获取到用户信息，返回个人中心并显示提示
		if(followName == null || followName == "" ||date == null || date ==""){
			// 设置跳转到个人中心
			request.setAttribute("myCenterErrorTip","请在关注列表里选择要修改的对象！");
			request.getRequestDispatcher("/myCenter.jsp").forward(request, response);
			return;
		}
		
		//将数据封装，传递到service层进行处理
		FollowTable ft = new FollowTable(mail, followName, isSend, date);
		EditFollowsService efs = new EditFollowsService();
		WebUser webUser = efs.editFollow(ft);
		//如果操作成功，返回更新的用户关注列表
		if(webUser!=null){
			request.getSession().setAttribute("follows", webUser.getFollows());
			// 设置跳转到个人中心
			response.getWriter().write("<script>window.opener.location.replace('myCenter.jsp');self.close();</script>");
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
