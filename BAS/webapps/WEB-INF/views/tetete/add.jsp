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
	href="<c:url value='/resources/css/common.css'/>">
<%-- <link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/datePicker/skin/WdatePicker.css'/>">	 --%>
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
	<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.validate.js'/>"></script>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/datePicker/WdatePicker.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/datePicker/calendar.js'/>"></script>
	<script type="text/javascript"
	src="<c:url value='/resources/datePicker/config.js'/>"></script> --%>
<script type="text/javascript">

$().ready(function() {
					
					$("#inputForm").validate({
						rules:{
							tempNo:"required",
							timeLimit:"required",
							timeLimit:{
								digits:true
							}
						}
					})
			});
</script>
<title>文章管理</title>
</head>
<body>
<div class="content scrollWH" scrollH='100'>

<c:choose>
    <c:when test="${empty bean.tempNo}">
   <form id="inputForm" class="form-horizontal" action='<c:url value="/admin/tetete/save"/>'  method="post">
   </c:when>
   <c:otherwise>
    <form id="inputForm" class="form-horizontal" action='<c:url value="/admin/tetete/update"/>' method="post">
   </c:otherwise>
</c:choose>
   
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">模板号</label>
			<div class="col-sm-4">
			<c:choose>
			  <c:when test="${empty bean.tempNo}">
			    <input type="text" name="tempNo"  maxlength="32"   class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="tempNo"  maxlength="32" value="${bean.tempNo}" readonly="readonly"  class="form-control"/>
			  </c:otherwise>
			</c:choose>
			</div>
			
			<label for="firstname" class="col-sm-2 control-label">短信有效时间</label>
			<div class="col-sm-4">
			<c:choose>
			  <c:when test="${empty bean.timeLimit}">
			    <input type="text" name="timeLimit"  maxlength="3"  class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="timeLimit"  maxlength="3" value="${bean.timeLimit}"  class="form-control"/>
			  </c:otherwise>
			</c:choose>
			</div>
		</div>
    	

		<div class="form-group"  id="temp">
		   <label for="" class="col-sm-2 control-label">模板内容</label>
		    <div class="col-sm-10">
		    <c:choose>
			<c:when test="${empty bean.temp}">
		      <input type="text" name="temp"  maxlength="200"   style="height:100px" class="form-control" />
			</c:when>
			<c:otherwise>
			   <input type="text" name="temp"  maxlength="200"  value="${bean.temp}"  style="height:100px" class="form-control"/>
			</c:otherwise>
			</c:choose>
			</div>
		
			<%-- <label for="introduce" class="col-sm-2 control-label">模板内容</label>
		    <div class="col-sm-10">
		    <c:choose>
			<c:when test="${empty bean.temp}">
		    <textarea id="editor" name="temp" class="editor" class="form-control"
							style="width: 100%;"></textarea>
			</c:when>
			<c:otherwise>
			<textarea id="editor" name="temp" class="editor"  class="form-control"
							style="width: 100%;">${bean.temp}</textarea>
			</c:otherwise>
			</c:choose>
			</div> --%>
		</div>
						
		<table class="table">
			<tr>
				<th>&nbsp;</th>
				<td><input type="submit" class="btn btn-default" value="确认" />
					<input type="button" class="btn btn-default" value="返回"
					onclick="location.href='list'" />
					</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>