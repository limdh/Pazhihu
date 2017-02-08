# Pazhihu README

## 一、pazhihu简介  
“爬知乎”是一个可以利用爬虫程序读取知乎用户动态的网页应用，它主要提供两大功能：  
1. 在线搜索知乎用户功能
通过知乎用户的个性域名搜索，可以获取知乎用户个人信息及动态。例如要关注轮子哥输入：excited-vczh。在页面右侧，会出现搜索结果，结果包括：他的个人成就，最新动态，回答过的问题，提出过的问题摘要，并有链接直达详细内容。  
2. 动态更新邮件提醒功能
爬知乎的登录用户，可以对知乎用户设置关注，关注的知乎用户更新动态后，发送邮件提醒。  
  
> **特别说明：**  
> 由于Pazhihu是学习JavaSE和部分JavaEE内容后的练习项目，因此使用的技术较为基础，主要涉及到JSP、Servlet、JavaBean、JDBC、DOM、JSTL 、EL、MySQL等基础知识，是适合初学者的趣味练习项目。  
  
## 二、启动说明  
1. 前端页面源码在pazhihu/web下  
2. 后台处理程序源码在pazhihu/src下  
3. src/jdbc.properties是数据库配置文件：  
 * 其中各配置项为：
 * url：数据库位置
 * DBuser：数据库账户
 * DBpsw：数据库密码
  
## 三、类、层次、模块设计

### 1. 搜索模块

无需登录，输入知乎用户的域名后缀，自动返回该知乎用户的部分信息：  
个人成就、最新动态、回答的问题、提出的问题
	
**前台页面：**首页
	
**后台功能类：**searchServlet、searchService
  
### 2. 注册登录模块

如想要关注一些知乎用户，他们在知乎有了新动态时，想要接收来自爬知乎的提醒邮件，可以选择成为爬知乎网站的注册用户，登录用户可以享受关注功能。
  
**前台页面：**注册、登录页面
  
**后台功能类：**  
* Servlet：RegServlet、LoginServlet、LogoutServlet
* Service：RegService、LoginService
* Dao：DBAccess
* Domain：WebUser、ZhihuUser
* Utils：JdbcUtils
	
**数据库：**user表
  
### 3. 关注管理模块

成为网站的注册用户后，登录可以进入个人中心页面，在个人中心页面，可以添加和管理关注的用户列表。
  
**前台页面：**个人中心页面，添加关注页面、编辑关注页面
  
**后台功能类：**
* Servlet：setFollowsServlet、editFollowsServlet、DeleteFollowsServlet
* Service：setFollowsService、editFollowsService、DeleteFollowsService
* Dao：DBAccess
* Domain：FollowTable、WebUser、ZhihuUser
* Utils：JdbcUtils
	
**数据库表：**follows关注表
  
### 4. 邮件模块

用户设置了关注后，网站服务器会自动每隔半个小时，在后台获取被关注的知乎用户的最新动态，如果有更新的动态，则立刻发送邮件提醒。

**前台页面：**无
	
**后台功能类：**
* Listener：StartTimer、Listener
* Utils：JdbcUtils、MailUtils
* Dao：DBListener
	
**数据库表：**FollowsActivities动态表
  
## 五、待解决问题：  

1. 个别用户的个人成就有些许不一样，需要加判断识别，不然会无法获取；  
2.  数据库连接较为简单粗暴，后期可以使用连接池技术；  
3.  背景图片为知乎改版前的背景，本身是一个动画，但是练习爬虫为了直接目的，直接截图不纠结在这个细节，后期可以做改善；  
4.  后台输出的显示未删除，为了后期调试还可以使用；  
5.  发送的邮件格式比较简陋，后期可以美化一下；  
5.  暂时想不到什么了，毕竟没有长时间多用户的环境下测试过，以后发现了再改吧。  

