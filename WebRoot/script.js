/*-----------------------------------
 * 
		      首页搜索表单校验
 *                         
 -----------------------------------*/

//搜索框：用户名校验，知乎用户名的规则：长度要求在 4-30之间，只能使用英文字母、数字、符号_.-
function checkZhUser() {
	var zhihuName = document.getElementById("zhUserInput").value;
	var zhUserTip = document.getElementById("zhUserTip");
	zhUserTip.style.color = "red";
	zhUserTip.style.fontSize = "9px";

	//如果用户未输入，提示用户输入
	if (zhihuName.length == 0 || zhihuName == "") {
		zhUserTip.innerHTML = "不能为空！";
		return false;
	}
	//如果用户输入内容不符合规则，则显示规则
	else if (!/^[A-Za-z0-9_.-].{3,29}$/.test(zhihuName)) {
		zhUserTip.innerHTML = "知乎的个性域名长度要求在 4-30之间，只能使用英文字母、数字、符号_.-";
		return false;
	}
	//如果输入正确，提示绿色的ok
	else {
		zhUserTip.style.color = "green";
		zhUserTip.style.fontSize = "15px";
		zhUserTip.innerHTML = "<b>ok</b>";
		return true;
	}
	return false;
}

//表单提交校验
function submitForm() {
	//如果通过验证，则提交表单
	if (checkZhUser()) {
		return true;
	}
	return false;
}




/*-----------------------------------
 * 
		          登录页面表单校验
 *                         
 -----------------------------------*/

//用户名输入框：获取焦点时清除内容
function cleanUsername() {
	var username = document.getElementById("usernameInput");
	username.value = "";
	username.focus();
}

//用户名输入框：用户名校验，用户名的规则：用户名必须是注册的邮箱，使用邮箱的通用验证规则
function checkUsername() {
	var username = document.getElementById("usernameInput").value;
	var usernameTip = document.getElementById("usernameTip");
	usernameTip.style.color = "red";
	usernameTip.style.fontSize = "9px";
	if (username == "" || username.length == 0) {
		usernameTip.innerHTML = "登录邮箱不能为空！";
		return false;
	}
	else if (!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(username)) {
		usernameTip.innerHTML = "请输入正确的邮箱地址！";
		return false;
	}
	else {
		usernameTip.style.color = "green";
		usernameTip.style.fontSize = "15px";
		usernameTip.innerHTML = "<b>ok</b>";
		return true;
	}
	return false;

}

//密码输入框：获取焦点时清除内容，并密文显示
function cleanPassword() {
	var password = document.getElementById("passwordInput");
	password.value = "";
	password.focus();
	password.setAttribute("type", "password");
}

//密码输入框：登录密码只做空校验
function checkPassword() {
	var password = document.getElementById("passwordInput").value;
	var passwordTip = document.getElementById("passwordTip");
	passwordTip.style.color = "red";
	passwordTip.style.fontSize = "9px";
	if (password == "" || password.length == 0) {
		passwordTip.innerHTML = "请输入密码！";
		return false;
	}
	else{
		return true;
	}
	return false;
}

//表单提交校验
function loginSubmitForm() {
	//如果通过验证，则提交表单
	if (checkUsername() && checkPassword()) {
		return true;
	}
	return false;
}




/*-----------------------------------
 * 
		           注册页面表单校验
 *                         
 -----------------------------------*/

//用户名输入框：获取焦点时清除内容
function cleanUsernameReg() {
	var usernameReg = document.getElementById("usernameRegInput");
	usernameReg.value = "";
	usernameReg.focus();
}

//用户名输入框：用户名校验，用户名的规则：用户名必须是注册的邮箱，使用邮箱的通用验证规则
function checkUsernameReg() {
	var usernameReg = document.getElementById("usernameRegInput").value;
	var usernameRegTip = document.getElementById("usernameRegTip");
	usernameRegTip.style.color = "red";
	usernameRegTip.style.fontSize = "9px";
	if (usernameReg == "" || usernameReg.length == 0) {
		usernameRegTip.innerHTML = "邮箱帐号不能为空！";
		return false;
	}
	else if (!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(usernameReg)) {
		usernameRegTip.innerHTML = "请输入正确的邮箱地址！";
		return false;
	}
	else {
		usernameRegTip.style.color = "green";
		usernameRegTip.style.fontSize = "15px";
		usernameRegTip.innerHTML = "<b>ok</b>";
		return true;
	}

}

//密码输入框：获取焦点时清除内容，并密文显示
function cleanPasswordReg() {
	var passwordReg = document.getElementById("passwordRegInput");
	passwordReg.value = "";
	passwordReg.focus();
	passwordReg.setAttribute("type", "password");
}

//密码输入框：密码校验，强弱分级校验，基本满足规则：六位以上，数字或字母
function checkPasswordReg() {
	var passwordReg = document.getElementById("passwordRegInput").value;
	var passwordRegTip = document.getElementById("passwordRegTip");
	passwordRegTip.style.color = "red";
	passwordRegTip.style.fontSize = "9px";
	if (passwordReg == "" || passwordReg.length == 0) {
		passwordRegTip.innerHTML = "密码不能为空！";
		return false;
	}
	else if (passwordReg.indexOf(" ") != -1) {
		passwordRegTip.innerHTML = "密码不能包含空格符！";
		return false;
	}
	else if (passwordReg.length < 6) {
		passwordRegTip.innerHTML = "密码较短，最短支持6个字符！";
		return false;
	}
	else if (/^(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W])[\da-zA-Z\W]{8,}$/.test(passwordReg)) {
		passwordRegTip.innerHTML = "<b>ok</b>（强：请牢记您的密码）";
		passwordRegTip.style.color = "green";
		return true;
	}
	else if (/^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$/.test(passwordReg)) {
		passwordRegTip.innerHTML = "<b>ok</b>（中：试试字母、数字和标点混合）";
		passwordRegTip.style.color = "green";
		return true;
	}
	else if (/^(?:\d+|[a-zA-Z]+|[!@#$%^&*]+)$/.test(passwordReg)) {
		passwordRegTip.innerHTML = "<b>ok</b>（弱：试试字母、数字和标点混合）";
		passwordRegTip.style.color = "green";
		return true;
	}
	else {
		return true;
	}

}

//确认密码输入框：获取焦点时清除内容，并密文显示
function cleanPasswordTwice() {
	var passwordTwice = document.getElementById("passwordTwiceInput");
	passwordTwice.value = "";
	passwordTwice.focus();
	passwordTwice.setAttribute("type", "password");
}

//确认密码输入框：确认密码校验，两次输入必须一致，且不能为空
function checkPasswordTwice() {
	var passwordTwice = document.getElementById("passwordTwiceInput").value;
	var passwordReg = document.getElementById("passwordRegInput").value;
	var passwordTwiceTip = document.getElementById("passwordTwiceTip");
	passwordTwiceTip.style.color = "red";
	passwordTwiceTip.style.fontSize = "9px";
	if (passwordTwice == "" || passwordTwice.length == 0) {
		passwordTwiceTip.innerHTML = "确认密码不能为空！";
		return false;
	}
	else if (passwordTwice != passwordReg) {
		passwordTwiceTip.innerHTML = "两次密码不一致！";
		return false;
	}
	else {
		passwordTwiceTip.style.color = "green";
		passwordTwiceTip.style.fontSize = "15px";
		passwordTwiceTip.innerHTML = "<b>ok</b>";
		return true;
	}
}

//表单提交校验
function regSubmitForm() {
	//如果通过验证，则提交表单
	if (checkUsernameReg() && checkPasswordReg() && checkPasswordTwice()) {
		return true;
	}
	return false;
}



/*-----------------------------------
 * 
		           设置页面表单校验
 *                         
 -----------------------------------*/

//输入框：获取焦点时清除内容
function cleanFollows() {
	var followsList = document.getElementById("followsInput");
	followsList.value = "";
	followsList.focus();
}

//输入框：知乎用户名的规则和个性域名规则
function checkFollows() {
	var followsName = document.getElementById("followsInput").value;
	var followsTip = document.getElementById("followsTip");
	followsTip.style.color = "red";
	followsTip.style.fontSize = "9px";

	//如果用户未输入，提示用户输入
	if (followsName.length == 0 || followsName == "") {
		followsTip.innerHTML = "不能为空！";
		return false;
	}
	//如果用户输入内容不符合后缀名的规则，要求按照提示输入
	
	else if (!/^[A-Za-z0-9_.-/:].{3,59}$/.test(followsName)) {
		followsTip.innerHTML = "输入不符合要求，或名称太长，请参考输入提示！";
		return false;
	}
	//如果输入正确，提示绿色的ok
	else {
		followsTip.style.color = "green";
		followsTip.style.fontSize = "15px";
		followsTip.innerHTML = "<b>ok</b>";
		return true;
	}
	return false;
}

//表单提交校验
function setFollowsForm() {
	//如果通过验证，则提交表单
	if (checkFollows()) {
		return true;
	}
	return false;
}



/*-----------------------------------
 * 
                        个人中心页面功能模块
 *                         
 -----------------------------------*/


function editConfirm(obj){
	var followName = document.getElementById("td"+obj.name).innerHTML;
	window.open("editFollows.jsp?id="+followName,"编辑关注列表","width=350,height=330,top=200,left=200"); 
}


function deleteConfirm(obj){
	var question = confirm("是否删除当前的数据？");
	if (question ==true){
		//获取到点击删除链接行的关注用户
		var followName = document.getElementById("td"+obj.name).innerHTML;
		//将关注用户传递给servlet处理数据库删除操作
		var url = location.href.substr(0, location.href.lastIndexOf("/"))+"/deleteFollows?id="+followName;
		window.location = url;
	}
}



/*-----------------------------------
 * 
                        编辑关注页面表单提交模块
 *                         
 -----------------------------------*/

function editFollowsForm(){
	var followName=document.getElementById("followNameFont").innerHTML;
	var editfollowsTip = document.getElementById("editfollowsTip");
	if(followName!=null && followName!=""){
		return true;
	}else{
		editfollowsTip.style.color = "red";
		editfollowsTip.style.textAlign = "center";
		editfollowsTip.style.marginLeft="25px";
		editfollowsTip.style.fontSize = "9px";
		editfollowsTip.innerHTML = "修改关注用户出错，请重新登录后操作";
		return false;
	}
	
}

