<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
	<div>
		<div style="float: right;">
			<button type="button" class="btn btn-default" id="btnSave">保存</button>
			<a href="javascript:history.go(-1);">返回</a>
		</div>
	</div>
<form:form method="post" id="form">
<table class="table">
<tr style="background: #E5E5E5;">
				<td width="50"></td>
				<td>角色</td>
			</tr>
<c:forEach items="${roles}" var="role">
<tr name="tr" id="${role.roleid}">
<td>
	<input id="${role.roleid}" type="checkbox" name="roleid" value="${role.roleid}"/>
	</td>
	<td>${role.rolename }</td>
</tr>
</c:forEach>
</table>
</form:form>
</body>
<script type="text/javascript">
<c:forEach items="${roleIds }" var="roleId">
$('input[name="roleid"][value="${roleId }"]').attr("checked",true);
</c:forEach>

/* var tr = $('tr[name="tr"]'); 
tr.click(function(){
	var id = this.getAttribute("id");
	var chk = $('input#'+id); 
	console.log(chk);
	if(chk.is(":checked")){
		chk.attr('checked',false);
		return;
	}
	chk.attr('checked',true);
}) */

$("#btnSave").click(function(){
	$("#form").submit();		
}); 
</script>
</html>