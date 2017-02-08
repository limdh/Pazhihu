package cn.lim.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.lim.domain.WebUser;
import cn.lim.service.RegService;

public class RegServlet extends HttpServlet {

	private static final long serialVersionUID = -3524905355273911295L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 解决乱码问题，设置request缓冲区的编码(post)，并设置字符的编码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//暂存用户输入的用户名、密码和验证码提示信息，用于返回至表单
		request.setAttribute("username", request.getParameter("username"));
		request.setAttribute("password", request.getParameter("password"));
		request.setAttribute("passwordTwice", request.getParameter("passwordTwice"));
		
		//如果未获取到表单中的信息（可能是用户直接在地址栏输入servlet链接造成），重定向到注册页面
		if(request.getParameter("username") == null || request.getParameter("password") ==null || request.getParameter("passwordTwice")==null){
			response.sendRedirect(request.getContextPath() + "/reg.jsp");
		}
		
		//获取到请求中的参数列表，通过bean工具封装到user中
		Map<String, String[]> parameters = request.getParameterMap();
		
		WebUser user = new WebUser();
		try {
			BeanUtils.populate(user, parameters);
			
			//验证数据是否重复
			RegService regService = new RegService();
			int state = regService.check(user);
			
			//用户名已存在的情况
			if(state == -1){
				//设置提示信息，用于返回至表单
				request.setAttribute("msgUsername", "<font size='5px' color='red'>用户名已存在</font>");
				// 转发到注册页面
				request.getRequestDispatcher("/reg.jsp").forward(request, response);
			}
			
			//检查通过，把用户信息保存到数据库
			else if(state == 0){
				if(regService.putIn(user)){
					response.getWriter().write("注册成功");
				}else
					response.getWriter().write("添加数据失败");
			}
			
		
		} catch (Exception e) {
			System.out.println("数据库处理出错！");
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
