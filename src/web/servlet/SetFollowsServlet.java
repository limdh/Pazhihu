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
		
		// 预防乱码问题，设置request缓冲区的编码(post)，并设置字符的编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// 获取用户的提醒设置，如果用户勾选了接收邮件，则将发送标识设置为true
		boolean isSend = false;
		if(request.getParameter("mailSend") != null){
			isSend = true;
		}
		
		// 获取要关注的用户，判断用户是否输入多个用户
		String[] follows;
		if(request.getParameter("follows").indexOf(",") != -1){
			follows = request.getParameter("follows").split(",");
		}else{
			follows = new String[]{request.getParameter("follows")};
		}
		
		// 获取当前登录用户对象
		WebUser webUser = new WebUser();
		webUser.setUsername((String) request.getSession().getAttribute("username"));
		webUser.setPassword((String) request.getSession().getAttribute("password"));
		webUser.setFollows((HashMap<String,String>) request.getSession().getAttribute("follows"));
		webUser.setRegDate((String) request.getSession().getAttribute("regDate"));
		
		//验证用户是否登录，如果未登录，转发到登录页面
		String mail = webUser.getUsername();
		if(mail == "" || mail == null){
			// 设置提示信息，用于返回至表单
			request.setAttribute("msgLoginFail", "请登录后操作");
			// 转发到登录页面
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
		
		// 获取当前日期。
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(Calendar.getInstance().getTime());
		
		
		// 将数据封装到对象容器中
		ArrayList<FollowTable> ftList = new ArrayList<FollowTable>();
		for (int i = 0; i < follows.length; i++) {
			ftList.add(new FollowTable(mail, follows[i], isSend, date));
		}
		
		
		// 将对象容器传递到service层处理，并返回处理结果
		SetFollowsService setService = new SetFollowsService(ftList, webUser);
		int flag = setService.addFollowList();
		
		if(flag == SetFollowsService.MAILFAIL){
			//传递错误信息，转发到登录页面
			request.setAttribute("msgLoginFail", "登录邮箱错误，请重新登录");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else if(flag == SetFollowsService.FOLLOWFAIL){
			//传递错误信息，转发到个人中心页面
			request.setAttribute("msgFollows", "该知乎用户不存在，或确认网络能正常打开知乎");
			request.getRequestDispatcher("/setFollows.jsp").forward(request, response);
		}else if(flag == SetFollowsService.DBFAIL){
			//传递错误信息，转发到个人中心页面
			request.setAttribute("msgFollows", "数据库错误，请稍后再试或联系网站管理员");
			request.getRequestDispatcher("/setFollows.jsp").forward(request, response);
		}else if(flag == SetFollowsService.FOLLOWEXIST){
			//传递错误信息，转发到个人中心页面
			request.setAttribute("msgFollows", "提交的列表中有关注过的用户");
			request.getRequestDispatcher("/setFollows.jsp").forward(request, response);
		}else if(flag == SetFollowsService.SUCCESS){
			//关注成功，将session中的follows变量更新
			webUser = setService.getWebUser();
			request.getSession().setAttribute("follows", webUser.getFollows());
			//转发到个人中心页面
			request.getRequestDispatcher("/myCenter.jsp").forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
