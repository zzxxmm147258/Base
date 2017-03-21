<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	//获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var basePath = "<%=basePath%>";
</script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/uploadify.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/lc_switch.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/common.css'/>">
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/bootstrap.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery.uploadify.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/editor/kindeditor.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/input.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/lc_switch.js'/>"></script>
<script type="text/javascript">
$(function(){
	$('input[name="ispassd"]').lc_switch('允许','禁止');
});
</script>
<title></title>
</head>
<body>

<c:choose>
  <c:when test="${not empty ipFilter.id}">
  <form id="inputForm" class="form-horizontal" action="edit"
		method="post">
		<input type="hidden" value="${ipFilter.id}" name="id"/>
	
   </c:when>
   <c:otherwise>
   <form id="inputForm" class="form-horizontal" action="add"
		method="post">
   </c:otherwise>
</c:choose>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">IP</label>
			<div class="col-sm-10">
			<c:choose>
			  <c:when test="${empty ipFilter.ip} ">
			    <input type="text" name="ip" class="text" maxlength="200" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="ip" class="text" maxlength="200" value="${ipFilter.ip}"/>
			  </c:otherwise>
			</c:choose>
				
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">名称</label>
			<div class="col-sm-10">
			<c:choose>
			  <c:when test="${empty ipFilter.name} ">
			    <input type="text" name="name" class="text" maxlength="200" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="name" class="text" maxlength="200" value="${ipFilter.name}"/>
			  </c:otherwise>
			</c:choose>
				
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">端口 </label>
			<div class="col-sm-10">
			<c:choose>
			  <c:when test="${empty ipFilter.port} ">
			    <input type="text" name="port" class="text" maxlength="200" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="port" class="text" maxlength="200" value="${ipFilter.port}"/>
			  </c:otherwise>
			</c:choose>
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">允许/禁止</label>
			<div class="col-sm-10">
			<c:choose>
			  <c:when test="${empty ipFilter.ispassd||ipFilter.ispassd}">
			    <input type="checkbox" name="ispassd" class="text" value="true" maxlength="200" checked="checked"/>
			  </c:when>
			  <c:otherwise>
			     <input type="checkbox" name="ispassd" class="text" maxlength="200" value="true"/>
			  </c:otherwise>
			</c:choose>
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">备注</label>
			<div class="col-sm-10">
			<c:choose>
			  <c:when test="${empty ipFilter.remark} ">
			    <input type="text" name="remark" class="text" maxlength="200" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="remark" class="text" maxlength="200" value="${ipFilter.remark}"/>
			  </c:otherwise>
			</c:choose>
			</div>
		</div>
		<table class="table">
			<tr>
				<th>&nbsp;</th>
				<td><input type="submit" class="btn btn-default" value="确认" />
					<input type="button" class="btn btn-default" value="重置"
					onclick="location.href='list'" /></td>
			</tr>
		</table>
	</form>
</body>
</html>