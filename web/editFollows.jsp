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
	
	<div id="editFollowsMainDIV">
		<c:if test = "${sessionScope.username == null}">
			<c:redirect url="/login.jsp" >
				<c:param name="login" value="false"/>
			</c:redirect>
		</c:if>
	
		<div id="formDIV">
			<div id="title">
				<img src="img/indexLogo.png" />
			</div>
			<form action="edit?id=${param.id}" onsubmit="return editFollowsForm()" method="post">
				<div id="editfollowDIV">
					<div id="editfollowsTip">${msgFollows}</div>
					<div id = "mailSendDIV">
						<div id="mailSendRadioTitle">
							<b><font id="followNameFont">${param.id}</font>在知乎有新动态时，是否发送提醒邮件？</b>
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