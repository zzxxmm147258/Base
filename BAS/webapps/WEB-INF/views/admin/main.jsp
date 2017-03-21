<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<!-- <html xmlns="http://www.w3.org/1999/xhtml"> -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
<link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>" type="text/css">
<link rel="stylesheet" href="<c:url value='/resources/css/list.css'/>" type="text/css">
<title>${title}</title>
</head>
<body>
	<div class="box">
		<!-- 头部 -->
		<div class="head min-width">
			<h3 class="fl">
				<a href=""></a>
			</h3>
			<!-- 			<div class="head_input fl">
				<input type="text" class="one"><input type="button"
					value="搜索" class="two">
			</div> -->
			<ul class="fr">
				<li>欢迎您:
					<c:choose>
						<c:when test="${not empty loginInfo.user.truename }">${loginInfo.user.truename }</c:when>
						<c:otherwise>${loginInfo.user.username }</c:otherwise>
					</c:choose>
				</li>
				<li><span class='help'></span>帮助</li>
				<a href='<c:url value="/logout"/>'><li><span class='go'></span>退出</li></a>
			</ul>
		</div>

		<!-- minbody -->
		<div class="add" id="add">
			<!--  这个是一级菜单， -->
			<ul id="creat">
			</ul>
		</div>
		<div class="Current min-width clearfix">
			<span><em></em>您当前的位置:</span>
			<ul class="clearfix" id="ress">
			</ul>
		</div>

		<!-- listcontent -->
		<div class="content clearfix min-width">
			<div class="list_ul fl" id="text_ul"></div>
			<div class="cont" id="ifam">
				<div class="cont_Automatic_list">
					<ul class='fl'>
						<li class="cont_iframe_li" menuid="Index" onclick="onclickLi('Index')">
							<a href="" target="ifrIndex" id="index" class="fl"
							style="border: 1px solid #ccc; padding-left: 8px; padding-right: 30px; height: 31px; font: 14px/30px '微软雅黑'; color: #ccc;">首页</a>
						</li>
					</ul>
					<div class="remove_all fl">
						<i></i>
						<dl>
							<dd>关闭全部</dd>
							<dd>关闭当前页</dd>
							<!-- <dd>刷新页面</dd> -->
						</dl>
					</div>
					<div class="remove_icon">
						<dl>
							<dd>关闭全部</dd>
							<dd>关闭当前页</dd>
							<!-- <dd>刷新页面</dd> -->
						</dl>
					</div>
				</div>
				<div class="cont_iframe_Whole">
					<div menuid="Index" class="cont_iframe_item" ><iframe name="ifrIndex" onload="autoHeight('Index')"></iframe></div>
				</div>
			</div>
			
		</div>
	</div>
	</div>
	<script type="text/javascript">
		var json = ${menuJson};
		var url = "${pageContext.request.contextPath}";
	</script>
	<script type="text/javascript" src='<c:url value="/resources/js/menu.js"/>'></script>
</body>
</html>