<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<c:url value='/resources/js/comm.js'/>"></script>
<style>
	*{margin:0;padding:0;}
	body{background:#f1f1f5;}
	.error{width:5.8rem;background:#f1f1f5;font:.35rem/.7rem '微软雅黑';border-radius:.09rem;padding:0 .05rem;color:#b3b3c1;display:inline-block;}
	.error_div{padding:.48rem .85rem;width:5.8rem;}
	.btn{width:5.8rem;height:.7rem;padding:0 .85rem .3rem .85rem;}
	.btn a{width:100%;height:100%;font:.35rem/.7rem '微软雅黑';color:#fff;text-align:center;border-radius:.09rem;display:inline-block;text-decoration: none;}
	.btn .ok{background:#E4393C;}
	.btn .cancle{border: 1px solid #a4a4a4;color: gray;}
</style>
<c:choose>
	<c:when test="${not empty message.title}">
		<title>${message.title}</title>
	</c:when>
	<c:otherwise>
		<title>提示</title>
	</c:otherwise>
</c:choose>
</head>
<body>
	 <%--  提示:<c:out value="${message}"></c:out> --%>
	<div class="error_div">
		<div class="error">${message.message}</div>
	</div> 
	   <c:if test="${not empty message.okBtn}">
		   <div class="btn">
		   		<a class='ok' href="<c:url value='${message.oKUrl}'/>">${message.okBtn}</a>
		   </div>
	   </c:if>
	   <c:if test="${not empty message.cancleBtn}">
		   <div class="btn">
		   		<a class='cancle' href="<c:url value='${message.cancleUrl}'/>">${message.cancleBtn}</a>
		   </div>
	   </c:if>
</body>
</html>