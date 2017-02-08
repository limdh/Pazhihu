<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%response.setStatus(HttpServletResponse.SC_OK);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录pa知乎</title>
<style type="text/css">
@IMPORT url("error_styles.css");
</style>
<script type="text/javascript" src="script.js"></script>
</head>

<body>
	<div id="errorMainDIV">
	<%
		//如果直接访问错误页面，提示页面不存在信息
		int errorCode;
		if(request.getAttribute("javax.servlet.error.status_code") != null){
			errorCode=(Integer)request.getAttribute("javax.servlet.error.status_code");
		}else{
			errorCode=404;
		}
	%>
		<div id="errorTitle">
			<a href="<%=request.getContextPath()%>"><img src="img/errorLogo.png" /></a>
			<span id="errorCode">-<%=errorCode %></span>
		</div>
		
		<div id="errorContent">
		
		<% if(errorCode==500){ %>
			<div id="errorInfo">小爬爬开小差了，快点叫醒管理员...</div>
			<div id="errorDetail">
				服务器内部错误，请联系管理员...
			</div>
		<%}else if(errorCode==404){ %>
			<div id="errorInfo">你似乎来到了没有小爬爬存在的荒原...</div>
			<div id="errorDetail">
				来源链接是否正确？
			</div>
		<%}else{%>
			<div id="errorInfo">小爬爬开小差了，快点叫醒管理员...</div>
			<div id="errorDetail">
				运气真好，这可是真的是小概率错误哦，一定要叫醒管理员给你颁奖！
			</div>
		<% } %>
			<hr id = "errorHR"/>
			<div id = "errorBackLink">
			<a href = "<%=request.getContextPath()%>">返回首页</a>
			<span>或者</span>
            <a href="#" onclick="javascript :history.back(-1);">返回上页</a>
            </div>
            
            
		</div>
	</div>
</body>

</html>