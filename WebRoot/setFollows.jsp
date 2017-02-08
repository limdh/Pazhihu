<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<div id="setFollowsLinkDiv">
		
		<c:if test = "${sessionScope.username != null}">
			<a href = "<%=request.getContextPath()%>/myCenter.jsp">欢迎您，${sessionScope["username"]}</a>
			&nbsp;|&nbsp;
			<a href = "<%=request.getContextPath()%>/logout">退出</a>
		</c:if>
		
		<c:if test = "${sessionScope.username == null}">
			<c:redirect url="/login.jsp" >
				<c:param name="login" value="false"/>
			</c:redirect>
		</c:if>
		
	</div>
	
	<div id="setFollowsMainDIV">
		<div id="formDIV">
			<div id="title">
				<a href="<%=request.getContextPath()%>"><img src="img/indexLogo.png" /></a>
			</div>
			
			<form action="set" onsubmit="return setFollowsForm()" method="post">
				<div id="followDIV">
				<c:if test="${paramValues.id[0] == null}">
					<input type="text" id="followsInput" name="follows" value="设置关注帐号"
						onfocus="cleanFollows()" onblur="checkFollows()" />
				</c:if>
				<c:if test="${paramValues.id[0] != null}">
					<input type="text" id="followsInput" name="follows" value="${paramValues.id[0]}"
						onfocus="cleanFollows()" onblur="checkFollows()" />
				</c:if>
					<div id="followsTip">${msgFollows}</div>
					<div id="followsList">
						<b>* 提示：</b><br />
						知乎帐号，即知乎的个性域名，或个性域名的后缀。<br />
						例如要关注轮子哥输入：excited-vczh<br />
						多个关注对象，用英文逗号,分隔。<br />
						<hr/>
					</div>
					<div id = "mailSendDIV">
						<div id="mailSendRadioTitle">
							<b>Ta在知乎有新动态时，发送提醒邮件？</b>
						</div>
						<div id = "mailSendRadioDIV">
							<label class="mailSendLabel">
								<input type = "checkbox" class="mailSendRadio" name = "mailSend" />
								我想接收提醒邮件
							</label>
						</div>
					</div>
				</div>
				<br />
				<div id="buttonDIV">
					<input type="image" id="setButton" name="setButton" src="img/addFollow_bt.jpg"
						onmouseover="this.src='img/addFollow_hover_bt.jpg'"
						onmouseout="this.src='img/addFollow_bt.jpg'" />
				</div>
			</form>

		</div>
	</div>
</body>
</html>