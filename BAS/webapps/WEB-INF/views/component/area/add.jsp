<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>">
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/bootstrap.js'/>"></script>
<title>Insert title here</title>
</head>
<body>
<strong>
		<h1 class="text-center" style="margin-top: 5%;">
		<c:if test="${empty area.name}">添加地区</c:if>
		<c:if test="${not empty area.name}">修改地区</c:if>
		</h1>
	</strong>
	<div class="container" style="margin-top: 5%;">
		<form:form class="form-horizontal" method="post" modelAttribute="area" enctype="multipart/form-data">	
		<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">上级地区:</label>
				</div>
				<div class="col-md-4">
					<%-- <form:input class="form-control" path="name" value="${area.name}"/> --%>
					${parentName}
				</div>
			</div>	
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">名称:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="name" value="${area.name}"/>
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="enName">英文名称:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="enName" value="${area.enName}"/>
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">排序:</label>
				</div>
				<div class="col-md-4">
				<form:input class="form-control" path="orders" value="${area.orders}"/>
				</div>
			</div>
			
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">图片(300*300):</label>
				</div>
				<div class="col-md-4">
				<input type="file" id="img" name="img"    Class="form-control" /> 
				</div>
			</div>
			
			<div style="display:none">
				<form:input path="id" value="${area.id}"/>
				<form:input path="fullName" value="${area.fullName}"/>
				<form:input path="treePath" value="${area.treePath}"/>
				<form:input path="parent" value="${area.parent}"/>
				<form:input path="area1" value="${area.area1}"/>
			</div>
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<input id="submit" class="btn btn-primary col-md-12" type="submit" value="确定">
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<a type="button" class="btn btn-default col-md-12" onclick="javascript:history.go(-1);">取消</a>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>