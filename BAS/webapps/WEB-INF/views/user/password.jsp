<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>

<c:if test="${empty success }">
	<strong>
		<h1 class="text-center" style="margin-top: 5%;">
		修改密码
		</h1>
	</strong>
	<div class="container" style="margin-top: 5%;">
		<div align="center" style="color:red;"><h4>${error }</h4></div>
		<form:form class="form-horizontal" method="post" modelAttribute="password">
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>原密码:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" type="password" path="oldPassword" />
				</div>
				<div>
					<form:errors style="color:red" path="oldPassword" />
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>新密码:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" type="password" path="newPassword" />
				</div>
				<div>
					<form:errors style="color:red" path="newPassword" />
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>重输密码:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" type="password" path="newPassword2" />
				</div>
				<div>
					<form:errors style="color:red" path="newPassword2" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<input id="submit" class="btn btn-primary col-md-12" type="submit"
						class="btn btn-default" value="提交">
				</div>
			</div>
		</form:form>
	</div>	
</c:if>
<div>
	<strong>
		<h1 class="text-center" style="margin-top: 5%;">
		${success }
		</h1>
	</strong>
</div>
</body>
</html>