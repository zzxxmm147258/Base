<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
	<strong>
		<h1 class="text-center" style="margin-top: 5%;">操作码信息</h1>
	</strong>
	<c:if test="${not empty addSuccess }">
		<h3 style="text-align:center;color:red">添加成功！现在可以继续添加。</h3>
	</c:if>
	<div class="container" style="margin-top: 5%;">
		<form:form class="form-horizontal" method="post" modelAttribute="operationcode">
			<form:hidden path="createDate"/>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>操作码:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="operaid" />
				</div>
				<div>
					<form:errors style="color:red" path="operaid"/>
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">操作名称:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="operaname" />
				</div>
				<div>
					<form:errors style="color:red" path="operaname"/>
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>备注:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="remark" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4">
				<div style="padding:1px" class="col-md-5">
					<input id="submit" class="btn btn-primary col-md-12" type="submit" value="提交">
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