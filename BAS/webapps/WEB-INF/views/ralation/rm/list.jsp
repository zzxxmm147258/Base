<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<body>
	<div class="container" style="width: 100%; padding: 0 15px; margin: 0;">
		<div class="row" style="margin-bottom: 10px;">
			<label class="control-label col-sm-1 col_padding" for="roleidInput">角色ID：</label>
			<div class="col-sm-3">
				<div class="input-group">
					<input id="roleidInput" value="${co.roleid }" type="text"
						class="form-control"
						onkeydown="if(event.keyCode==13){document.getElementById('ButFind').click();}">
				</div>
			</div>
			<label class="control-label col-sm-1 col_padding" for="menuidInput">菜单ID：</label>
			<div class="col-sm-3">
				<div class="input-group">
					<input id="menuidInput" value="${co.menuid }" type="text"
						class="form-control"
						onkeydown="if(event.keyCode==13){document.getElementById('ButFind').click();}">
				</div>
			</div>
		</div>
		<div class="click_list row" style="margin: 0;">
			<a target="_self" menuid="ralationRmAdd01" menuname="添加关系" href="<c:url value='/admin/ralation/rm/add'/>">增加</a> <a
				id="ButFind" name="find" onclick="onButFind()">查询</a>
		</div>

		<div class="dataTables_wrapper form-inline dt-bootstrap no-footer">
			<div class="dataTables_scroll">
				<div class="dataTables_scrollHead">
					<div class="dataTables_scrollHeadInner" role="grid">
						<table class="table-bordered table-condensed table-striped">
							<thead>
								<tr class='THD_width'>
									<th  width="50">序号</th>
									<th  width="50">操作</th>
									<th  width="100">角色名称</th>
									<th  width="80">角色ID</th>
									<th  width="80">菜单ID</th>
									<th  width="100">菜单名称</th>
									<th  width="120">操作人</th>
									<th  width="150">创建日期</th>
									<th  width="150">最新修改时间</th>
								</tr>
							</thead>
								<c:forEach items="${list }" var="b" varStatus="i">
									<tr roleid="${b.roleid }" menuid="${b.menuid }">
										<td style="text-align: center;">${i.count }</td>
										<td class="text-center">
											<a target="_self" menuid="${b.roleid}${b.menuid}01" menuname="编辑关系" href='<c:url value="/admin/ralation/rm/${b.roleid}/${b.menuid}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>
											<a><span name="remove" class="glyphicon glyphicon-remove" style="color: red; font-size: 16px" title="删除" /></a>
										</td>
										<td>${b.rolename}</td>
										<td>${b.roleid }</td>
										<td>${b.menuid }</td>
										<td>${b.menuname}</td>
										<td>${b.operator }</td>
										<td><fmt:formatDate value="${b.createDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td><fmt:formatDate value="${b.modifyDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									</tr>
								</c:forEach>
							</table>
							</div>
							</div>
							</div>
							</div>
							<c:if test="${empty select}">
								<div style="float: left">
									<portal:page url="${url }" />
								</div>
							</c:if>
							</div>
</body>


<script type="text/javascript">
	$("table").htablesize();
	$('span[name="remove"]').click(function(){
		if(confirm("是否确定要删除？")){
		var tr = $(this).parent().parent().parent().parent();
		var menuid = tr.attr("menuid");
		var roleid = tr.attr("roleid");
		$.ajax({
			url:'<c:url value="/admin/ralation/rm/del" />',
			data:{menuid:menuid,roleid:roleid},
			type:'post',
			success:function(data){
				tr.remove();
			},
			error:function(){
				alert("删除失败！");
			}
		})
		}
	})
	
function onButFind(){
	var url = "";
	var roleid = $('#roleidInput').val();
	var menuid = $('#menuidInput').val();
	
	url = "${pageContext.request.contextPath}/admin/ralation/rm/list?";
	if(null!=roleid && ''!=roleid){
		url += "&roleid="+roleid;
	};
	if(null!=menuid && ''!=menuid){
		url += "&menuid="+menuid;
	};
	$('a[name=find]').attr("href",url);
}

</script>
<script type="text/javascript" src='<c:url value="/resources/js/table.js"/>'></script>
</html>