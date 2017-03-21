<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link href='<c:url value="/resources/bootstrap_datetimepicker/css/bootstrap-datetimepicker.min.css"/>' rel="stylesheet" media="screen">
<body>
<div class="container" style="margin-top: 5%;">
	<form:form class="form-horizontal" method="post" modelAttribute="table">
	<div class="form-group">
		<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">系统ID:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="sysid" />
				</div>
				<div>
					<form:errors style="color:red" path="sysid" />
				</div>
		</div>
		<div class="col-md-4"></div>
				<div class="col-md-4">
					<input id="submit" class="btn btn-primary col-md-12" type="submit"
						class="btn btn-default" value="执行">
				</div>
		</div>
	</form:form>
	
	<strong>
		<c:if test="${not empty msg}">${msg}</c:if>
	</strong>
</div>
</body>

</html>