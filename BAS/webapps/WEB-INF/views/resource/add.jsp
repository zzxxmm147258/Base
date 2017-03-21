<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
	<strong>
		<h1 class="text-center" style="margin-top: 5%;">资源信息</h1>
	</strong>
	<c:if test="${not empty addSuccess }">
		<h3 style="text-align:center;color:red">添加成功！现在可以继续添加。</h3>
	</c:if>
	<div class="container" style="margin-top: 5%;">
		<form:form class="form-horizontal" method="post" modelAttribute="resource">
			<form:hidden path="createDate"/>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>资源ID:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="resourceid" />
				</div>
				<div>
					<form:errors style="color:red" path="resourceid"/>
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">资源名称:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="resname" />
				</div>
				<div>
					<form:errors style="color:red" path="resname"/>
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
					<label>备注:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="remark" />
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">是否有效:</label>
				</div>
				<div class="col-md-4">
					<form:select class="form-control" path="availabe">
						<form:option value="1">有效</form:option>
						<form:option value="0">无效</form:option>
					</form:select>
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