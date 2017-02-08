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

		// ��ȡ�û���������ݣ�����û���֪����ҳ����url
		String zhUser = request.getParameter("zhUser");

		// ���δ��ȡ���û���������ݣ��򷵻���ҳ����ʾ����
		if(zhUser == null || zhUser == ""){
			// ������ת����¼ҳ��
			request.setAttribute("searchError", "������Ҫ������֪���û�");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		// ���û���֪����ҳ���Ӵ��ݵ�service����д������ݽ���service�㱻��װ��targetUser��
		ZhihuUser targetUser = SearchService.search(zhUser);

		
		

		// �����ȡ���û������û�����Ϣ��ȡ�����������ڻỰ����
		if (targetUser != null) {

			request.setAttribute("resultName",
					"<a href=" + targetUser.getUserURL() + " title='ȥ֪��Ta����ҳ'>" + targetUser.getZhihuName() + "</a>");
			
			if(targetUser.isSecret()){
				request.setAttribute("resultFollowees", "sorry��������������˽�������޷���ȡTa����Ϣ");
			}else{
			
				//�ж��û��Ƿ��¼����ѡ��ͬ����ӹ�ע������ʾ��ʽ
				if(request.getSession().getAttribute("username") == null){
					request.setAttribute("resultAddLink", "<a href='"+ request.getContextPath() + "/login.jsp'>��¼�󣬿�����ӽ���ע�б����ո��������ʼ�</a>");
				}else{
					request.setAttribute("resultAddLink", "<a href='" + request.getContextPath() + "/setFollows.jsp?id="+ zhUser +"'>��ӽ���ע�б�</a>");
				}
			
				request.setAttribute("resultFollowees", "Ta��ע��" + targetUser.getFollowees() + "��");
	
				request.setAttribute("resultFollowers", "��" + targetUser.getFollowers() + "�˹�עTa");
	
				request.setAttribute("resultTopicsFollow",
						"<font color='#0663c1'><strong>���˳ɾͣ�</strong></font>" + targetUser.getAchievements());
				
				request.setAttribute("resultLast", "<font color='#0663c1'><strong><a href="+targetUser.getUserURL()+
						"/activities>Ta�Ķ�̬��</a></strong></font>" + targetUser.getLast());
	
				request.setAttribute("resultAnswers","<font color='#0663c1'><strong><a href="+targetUser.getUserURL()+
						"/answers>Ta�Ļش�</a></strong></font>" + targetUser.getAnswers());
	
				request.setAttribute("resultQuestions","<font color='#0663c1'><strong><a href="+targetUser.getUserURL()+
						"/answers>Ta�����ʣ�</a></strong></font>" + targetUser.getAsks());

			}
		}
		// ���û�л�ȡ���û����ڻỰ�д洢ʧ����Ϣ
		else {
			request.setAttribute("resultError", "û���ҵ����û�");

		}
		// �ض��������ҳ�棬���������ʾ��ҳ����
		//response.sendRedirect(request.getContextPath() + "/index.jsp");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
