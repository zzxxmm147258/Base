<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
	<div>
		<div style="float: right;">
			<button type="button" class="btn btn-primary" id="btnSave">保存</button>
			<a href="javascript:history.go(-1);">返回</a>
		</div>
	</div>
<form:form method="post" id="form">
<table class="table table-hover table-bordered">
<tr style="background: #E5E5E5;">
				<td width="50"></td>
				<td>菜单ID</td>
				<td>菜单名称</td>
				<td>级别</td>
			</tr>
<c:forEach items="${menus}" var="menu">
<tr>
<td>
	<input type="checkbox" name="menuid" value="${menu.menuid}"/>
	</td>
	<td>
		<c:choose>
			<c:when test="${menu.level eq 1 }">
				<span class="glyphicon glyphicon-circle-arrow-right"/>${menu.menuid }
			</c:when>
			<c:when test="${menu.level eq 2 }">
				&nbsp|__<span class="glyphicon glyphicon-circle-arrow-right"/>${menu.menuid }
			</c:when>
			<c:when test="${menu.level eq 3 }">
				&nbsp|&nbsp&nbsp&nbsp&nbsp&nbsp|__<span class="glyphicon glyphicon-circle-arrow-right"/>${menu.menuid }
			</c:when>
		</c:choose>
	</td>
	<td>${menu.menuname }</td>
	<td>${menu.level }</td>
</tr>
</c:forEach>
</table>
</form:form>
</body>
<script type="text/javascript">
<c:forEach items="${menuIds }" var="menuId">
$('input[name="menuid"][value="${menuId }"]').attr("checked",true);
</c:forEach>
$("#btnSave").click(function(){
	$("#form").submit();		
}); 
</script>
</html>