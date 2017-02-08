<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>注册pa知乎</title>
<style type="text/css">
@IMPORT url("styles.css");
</style>
<script type="text/javascript" src="script.js"></script>
</head>

<body>
	<div id="regMainDIV">
		
		<div id="formDIV">
			<div id="title">
				<a href="<%=request.getContextPath()%>"><img src="img/indexLogo.png" /></a>
			</div>
			<c:if test = "${sessionScope.username != null}">
				<div id="regErrorTip">
					<a href = "${pageContext.request.contextPath}/myCenter.jsp">${sessionScope["username"]}，您已登录本网站</a>
					<br /><br />
					<a href = "${pageContext.request.contextPath}/logout">如要注册新的帐号，请先点此退出登录</a>
				</div>
			</c:if>
			<c:if test = "${sessionScope.username == null}">
			<form action="reg" onsubmit="return regSubmitForm()" method="post">
				<div id="inputDIV">
					<input type="text" id="usernameRegInput" name="username" value="请输入登录邮箱"
						onfocus="cleanUsernameReg()" onblur="checkUsernameReg()" />
					<div id="usernameRegTip">${msgUsername}</div>
					<input type="text" id="passwordRegInput" name="password" value="请输入密码"
						onfocus="cleanPasswordReg()" onblur="checkPasswordReg()" />
					<div id="passwordRegTip"></div>
					<input type="text" id="passwordTwiceInput" name="passwordTwice" value="请再次输入密码"
						onfocus="cleanPasswordTwice()" onblur="checkPasswordTwice()" />
					<div id="passwordTwiceTip"></div>
				</div>
				<br />
				<div id="buttonDIV">
					<input type="image" id="regButton" name="regButton"
						src="img/reg_bt.jpg" onmouseover="this.src='img/reg_hover_bt.jpg'"
						onmouseout="this.src='img/reg_bt.jpg'" />
				</div>
				<div id="loginlinkDiv">
					<a href = "<%=request.getContextPath()%>/login.jsp">我有账号，直接登录</a>
				</div>
			</form>
			</c:if>
		</div>
		
	</div>
</body>
</html>