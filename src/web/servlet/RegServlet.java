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
		
		// ����������⣬����request�������ı���(post)���������ַ��ı���
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//�ݴ��û�������û������������֤����ʾ��Ϣ�����ڷ�������
		request.setAttribute("username", request.getParameter("username"));
		request.setAttribute("password", request.getParameter("password"));
		request.setAttribute("passwordTwice", request.getParameter("passwordTwice"));
		
		//���δ��ȡ�����е���Ϣ���������û�ֱ���ڵ�ַ������servlet������ɣ����ض���ע��ҳ��
		if(request.getParameter("username") == null || request.getParameter("password") ==null || request.getParameter("passwordTwice")==null){
			response.sendRedirect(request.getContextPath() + "/reg.jsp");
		}
		
		//��ȡ�������еĲ����б�ͨ��bean���߷�װ��user��
		Map<String, String[]> parameters = request.getParameterMap();
		
		WebUser user = new WebUser();
		try {
			BeanUtils.populate(user, parameters);
			
			//��֤�����Ƿ��ظ�
			RegService regService = new RegService();
			int state = regService.check(user);
			
			//�û����Ѵ��ڵ����
			if(state == -1){
				//������ʾ��Ϣ�����ڷ�������
				request.setAttribute("msgUsername", "<font size='5px' color='red'>�û����Ѵ���</font>");
				// ת����ע��ҳ��
				request.getRequestDispatcher("/reg.jsp").forward(request, response);
			}
			
			//���ͨ�������û���Ϣ���浽���ݿ�
			else if(state == 0){
				if(regService.putIn(user)){
					response.getWriter().write("ע��ɹ�");
				}else
					response.getWriter().write("�������ʧ��");
			}
			
		
		} catch (Exception e) {
			System.out.println("���ݿ⴦�����");
		}
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
