package cn.lim.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lim.domain.ZhihuUser;
import cn.lim.service.SearchService;

public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = -2511572692481642573L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取用户输入的内容，组成用户的知乎主页链接url
		String zhUser = request.getParameter("zhUser");

		// 如果未获取到用户输入的内容，则返回首页并提示错误
		if(zhUser == null || zhUser == ""){
			// 设置跳转到登录页面
			request.setAttribute("searchError", "请输入要搜索的知乎用户");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		// 把用户的知乎主页链接传递到service层进行处理，数据将在service层被封装到targetUser中
		ZhihuUser targetUser = SearchService.search(zhUser);

		
		

		// 如果获取到用户，则将用户的信息提取出来，保存在会话域中
		if (targetUser != null) {

			request.setAttribute("resultName",
					"<a href=" + targetUser.getUserURL() + " title='去知乎Ta的首页'>" + targetUser.getZhihuName() + "</a>");
			
			if(targetUser.isSecret()){
				request.setAttribute("resultFollowees", "sorry，主人设置了隐私保护，无法获取Ta的信息");
			}else{
			
				//判断用户是否登录，以选择不同的添加关注链接显示方式
				if(request.getSession().getAttribute("username") == null){
					request.setAttribute("resultAddLink", "<a href='"+ request.getContextPath() + "/login.jsp'>登录后，可以添加进关注列表，接收更新提醒邮件</a>");
				}else{
					request.setAttribute("resultAddLink", "<a href='" + request.getContextPath() + "/setFollows.jsp?id="+ zhUser +"'>添加进关注列表</a>");
				}
			
				request.setAttribute("resultFollowees", "Ta关注了" + targetUser.getFollowees() + "人");
	
				request.setAttribute("resultFollowers", "有" + targetUser.getFollowers() + "人关注Ta");
	
				request.setAttribute("resultTopicsFollow",
						"<font color='#0663c1'><strong>个人成就：</strong></font>" + targetUser.getAchievements());
				
				request.setAttribute("resultLast", "<font color='#0663c1'><strong><a href="+targetUser.getUserURL()+
						"/activities>Ta的动态：</a></strong></font>" + targetUser.getLast());
	
				request.setAttribute("resultAnswers","<font color='#0663c1'><strong><a href="+targetUser.getUserURL()+
						"/answers>Ta的回答：</a></strong></font>" + targetUser.getAnswers());
	
				request.setAttribute("resultQuestions","<font color='#0663c1'><strong><a href="+targetUser.getUserURL()+
						"/answers>Ta的提问：</a></strong></font>" + targetUser.getAsks());

			}
		}
		// 如果没有获取到用户，在会话中存储失败信息
		else {
			request.setAttribute("resultError", "没有找到该用户");

		}
		// 重定向回搜索页面，并将结果显示在页面上
		//response.sendRedirect(request.getContextPath() + "/index.jsp");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
