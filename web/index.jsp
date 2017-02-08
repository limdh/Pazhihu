<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/error.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>欢迎使用pa知乎</title>
<style type="text/css">
@IMPORT url("styles.css");
</style>
<script type="text/javascript" src="script.js"></script>
</head>

<body>
	<div id="indexlinkDiv">
		
		<c:if test = "${sessionScope.username != null}">
			<a href = "<%=request.getContextPath()%>/myCenter.jsp">欢迎您，${sessionScope["username"]}</a>
			&nbsp;|&nbsp;
			<a href = "<%=request.getContextPath()%>/logout">退出</a>
		</c:if>
		
		<c:if test = "${sessionScope.username == null}">
			<a href = "<%=request.getContextPath()%>/reg.jsp">注册pa知乎</a>|
			<a href = "<%=request.getContextPath()%>/login.jsp">登录</a>
		</c:if>
		
	</div>
	<div id="leftMainDIV">
		<div id="formDIV">
			<div id="title">
				<a href="<%=request.getContextPath()%>"><img src="img/indexLogo.png" /></a>
			</div>
			<div id="searchErrorDIV">
				${searchError}
			</div>
			<form action="search" onsubmit="return submitForm()" method="post">
				<div id="inputDIV">
					<input type="text" id="zhUserInput" name="zhUser"
						onblur="checkZhUser()" />
					<div id="zhUserTip"></div>
				</div>
				<br />
				<div id="buttonDIV">
					<input type="image" id="submitbutton" name="button"
						src="img/submit.jpg" onmouseover="this.src='img/submit_hover.jpg'"
						onmouseout="this.src='img/submit.jpg'" />
				</div>
			</form>
		</div>
	</div>

	<div id="rightMainDIV">
		<div id="resultDIV">
			<div id="resultTitle">
				<span id="resultError">${resultError}</span>
				<span id="resultName">${resultName}</span>
			</div>
			
			<div id="followNum">
				<span id="resultFollowees">${resultFollowees}</span>
				<span id="resultFollowers">${resultFollowers}</span>
			</div>
			<div id="addToList">${resultAddLink}</div>
			
			<div id="topics">${resultTopicsFollow}</div>
			<div id="last">${resultLast}</div>
			<div id="answers">${resultAnswers}</div>
			<div id="questions">${resultQuestions}</div>
		</div>
	</div>
</body>
</html>