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
	<div id="myCenterLinkDiv">
		
		
		<c:if test = "${sessionScope.username != null}">
			<a href = "${pageContext.request.contextPath}/myCenter.jsp">欢迎您，${sessionScope["username"]}</a>
			&nbsp;|&nbsp;
			<a href = "${pageContext.request.contextPath}/logout">退出</a>
		</c:if>
		
		<c:if test = "${sessionScope.username == null}">
			<c:redirect url="/login.jsp" >
				<c:param name="login" value="false"/>
			</c:redirect>
		</c:if>
		
	</div>
	<div id="myCenterMainDIV">
		<div id="myInfoDIV">
			<div id="myCenterTitle">
				<a href="<%=request.getContextPath()%>"><img src="img/indexLogo.png" /></a>
			</div>
			<div id="myCenterErrorTip">${myCenterErrorTip}</div>
			<div class="myCenterInfo">用户邮箱：${sessionScope["username"]}</div>
			<br />
			<div class="myCenterInfo">
			关注列表：
				
				<table id="followTable">
					<tr>
			            <th width="35px" class="followTH">序号</th>
			            <th width="180px" class="followTH">关注用户</th>
			            <th width="80px" class="followTH">是否提醒</th>
			            <th width="60px" class="followTH">操作</th>
			        </tr>

					<c:set value="1" var="num" />
					<c:forEach var="followItem" items="${sessionScope['follows']}">
					
						<c:if test = "${followItem != null}">
							<tr id= "tr${num}">
								<td class="followTD"><c:out value="${num}" /></td>
					            <td class="followTD" id = "td${num}"><c:out value="${followItem.key}" /></td>
					            <td class="followTD"><c:out value="${followItem.value}"/></td>
					            <td class="followTD">
									<a name="${num}" href="#" onclick="editConfirm(this)" >编辑</a>
									<a name="${num}" href="#" onclick="deleteConfirm(this)">删除</a>
								</td>
				        	</tr>
						 	<c:set value="${num+1}" var="num" />
					 	
					 	</c:if>
					</c:forEach>
					
					<tr>
						<td class="followTD" colspan="4"><a href="setFollows.jsp">继续添加关注</a></td>
		        	</tr>
					
				</table>
			</div>
			<br />
			
			<div class="myCenterInfo">注册日期：${sessionScope["regDate"]}</div>

		</div>
	</div>
</body>
</html>