<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>登录pa知乎</title>
<style type="text/css">
@IMPORT url("styles.css");
</style>
<script type="text/javascript" src="script.js"></script>
</head>

<body>
	<div id="loginMainDIV">
		<div id="formDIV">
			<div id="title">
				<a href="<%=request.getContextPath()%>"><img src="img/indexLogo.png" /></a>
			</div>
			<c:if test="${paramValues.login[0] == 'false'}">
				<c:set var="msgLoginFail" value="请登录后再操作" />
			</c:if>
			${requestScope.loginFlag}
			<div id="loginFailInfo">${msgLoginFail}</div>
			<form action="login" onsubmit="return loginSubmitForm()" method="post">
				<div id="inputDIV">
					<input type="text" id="usernameInput" name="username" value="请输入登录邮箱" 
						onfocus="cleanUsername()" onblur="checkUsername()" />
					<div id="usernameTip"></div>
					<input type="text" id="passwordInput" name="password" value="请输入密码"
						onfocus="cleanPassword()" onblur="checkPassword()" />
					<div id="passwordTip"></div>
				</div>
				<br />
				<div id="buttonDIV">
					<input type="image" id="loginButton" name="loginButton"
						src="img/login_bt.jpg"
						onmouseover="this.src='img/login_hover_bt.jpg'"
						onmouseout="this.src='img/login_bt.jpg'" />
				</div>
				<div id="reglinkDiv">
					<a href="<%=request.getContextPath()%>/reg.jsp">立即注册，使用更新提示功能</a>
				</div>
			</form>
		</div>

	</div>
</body>
</html>