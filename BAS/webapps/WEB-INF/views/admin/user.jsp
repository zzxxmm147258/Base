<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<head>
	<TITLE>${title}</TITLE>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<c:url value='/resources/hibo/css/hibo_table.css'/>">
    <script type="text/javascript" src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
    <script type="text/javascript" src='<c:url value="/resources/hibo/js/hibo_table.js"/>'></script>
    <script type="text/javascript" src='<c:url value="/resources/hibo/js/hibo_validate.js"/>'></script>
	<SCRIPT type="text/javascript">
		$(function(){
			$("td").honload();
			$("tr").htrdEvent();
		});
	</SCRIPT>
</head>
<body>
	<form action="<c:url value="/common/userp"/>" method="post">
		<table class="table-bordered table-condensed">
			<tr>
				<th width="150px">用户名</th>
				<th width="150px">类型</th>
			</tr>
			<tr>
				<td htype="text" hname="users[0].username">1</td>
				<td htype="text" hname="users[0].utype">1</td>
			</tr>
			<tr>
				<td htype="text" hname="users[3].username">2</td>
				<td htype="text" hname="users[3].utype">2</td>
			</tr>
			<tr>
				<td htype="text" hname="users[2].username">3</td>
				<td htype="text" hname="users[2].utype">3</td>
			</tr>
		</table>
		<input type="submit" value="提交"/>
	</form>
</body>
</html>