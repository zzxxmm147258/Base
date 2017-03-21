<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
	<strong>
		<h1 class="text-center" style="margin-top: 5%;">菜单信息</h1>
	</strong>
	<c:if test="${not empty addSuccess }">
		<h3 style="text-align:center;color:red">添加成功！现在可以继续添加。</h3>
	</c:if>
	<div class="container" style="margin-top: 5%;">
		<form:form class="form-horizontal" method="post" modelAttribute="menu">		
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">菜单ID:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="menuid" value="${menu.menuid}"/>
				</div>
				<div>
					<form:errors style="color:red" path="menuid" />
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">菜单名称:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="menuname" value="${menu.menuname}"/>
				</div>
				<div>
					<form:errors style="color:red" path="menuname" />
				</div>
			</div>
			
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>系统ID:</label>
				</div>
				<div class="col-md-4">
					<% 
						request.setAttribute("sysSelect", DataConfig.getSysIdMap());
					%>
					<form:select class="form-control" path="sysid">
						<c:forEach items="${sysSelect}" var="sys">
							<form:option value="${sys.key}">${sys.key}:${sys.value}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">级别:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="level" value="${menu.level}"/>
				</div>
				<div>
					<form:errors style="color:red" path="level" />
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">链接:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="url" value="${menu.url}"/>
				</div>
				<div>
					<form:errors style="color:red" path="url" />
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>显示方式:</label>
				</div>
				<div class="col-md-4">
					<form:select class="form-control" path="showType">
						<form:option value="右下角显示">右下角显示</form:option>
						<form:option value="当前页面显示">当前页面显示</form:option>
						<form:option value="新页面显示">新页面显示</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">是否启用:</label>
				</div>
				<div class="col-md-4">
					<form:select class="form-control" path="availabe">
						<form:option value="1">是</form:option>
						<form:option value="0">否</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div style="padding:1px" class="col-md-5">
						<input class="btn btn-primary col-md-12" type="submit" value="提交">
					</div><div class="col-md-2"></div>
					<div style="padding:1px" class="col-md-5">
						<a class="btn btn-default col-md-12" id="close" >取消</a>
					</div>
				</div>
			</div>
		</form:form>
	</div>

</body>
<script type="text/javascript">
	var updateSuccess = '${updateSuccess}';
	if(updateSuccess != null && updateSuccess != ''){
		$('#close').attr("closeNow","true");//关闭页面
	}
	
	$('#close').click(function(){
		$.MenuUtil.close();
	});
</script>
</html>