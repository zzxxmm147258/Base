<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>

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
	href="<c:url value='/resources/css/uploadify.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/common.css'/>">
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
<script type="text/javascript">

$().ready(function() {
					var $inputForm = $("#inputForm");
					var $type = $("#type");	
					var oTypeval = $("#type option:selected").val();
					var $content = $("#content");
					var $path=$("#img_path");
					var $browserButton = $("#browserButton");
					if (oTypeval == "0") {
						$content.show();
						$path.hide();
						$browserButton.prop("disabled", true);
					} else {
						$content.hide();
						$path.show();
						$browserButton.prop("disabled", false);
						$browserButton.browser();
					}
					$type.change(function() {
						
						if ($(this).val() == "0") {
							$content.show();
							$path.hide();
							
							$browserButton.prop("disabled", true);
						} else {
							$content.hide();
							$path.show();
							
							$browserButton.prop("disabled", false);
							$browserButton.browser();
						}
					});
					
					
					$("#inputForm").validate({
						rules:{
							title:"required",
							orders:{
								digits:true
							}
						}
					})
			});
</script>
<title>广告管理</title>
</head>
<body>
<div class="content scrollWH" scrollH='100'>

<form:form modelAttribute="bean" id="inputForm" class="form-horizontal" enctype="multipart/form-data">

		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label"> 标题 </label>
			<div class="col-sm-4">
				<form:input type="text" path="title" name="title"  maxlength="200"  class="form-control" />
			</div>
		
			<label for="firstname" class="col-sm-2 control-label"> 类型 </label>
			<div class="col-sm-4">
				<form:select path="type" class="form-control">
					<form:option value="0">文字</form:option>
					<form:option value="1">图片</form:option>
				</form:select>
			</div>
		</div>
		
		
		<div class="form-group">
			<label for="url" class="col-sm-2 control-label"> 链接地址:</label>
			<div class="col-sm-4">
			<form:input type="text" path="url" name="url"  maxlength="200" class="form-control" />
			</div>
			<label for="orders" class="col-sm-2 control-label"> 排序:</label>
			<div class="col-sm-4">
				<form:input type="text" path="orders" name="orders" class="form-control" maxlength="200" />
			</div>
		</div>	
		
<%-- 	<div class="form-group">
		<label for="beginDate" class="col-sm-2 control-label">起始日期:</label>
	   <div class="col-sm-4">
	   <input name="begin"  type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${bean.begin}"  readonly="true"></input>
	   </div>
		<label for="endDate" class="col-sm-2 control-label">结束日期:</label>
	   <div class="col-sm-4">
	   <input name="end"  type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${bean.end}"  readonly="true"></input>
	   </div>
	   </div> --%>
		<div class="form-group" id="introduction">
			<label for="orders" class="col-sm-2 control-label"> 概要:</label>
			<div class="col-sm-10">
				<form:input type="text" path="introduction" name="orders" class="form-control" maxlength="200" />
			</div>
		</div>
		<div class="form-group" id="img_path">
			<label for="" class="col-sm-2 control-label">图片路径</label>
		    <div class="col-sm-4">
		    <c:choose>
			<c:when test="${empty bean.path}">
		      <input type="file" id="img" name="img"    maxlength="200"  class="form-control"/> 
			</c:when>
			<c:otherwise>
			 <img alt="" src="${bean.path}"  style="width:30px;height:30px;float:left;margin-right:6px;">
			 <input type="file" id="img" name="img"    maxlength="200" class="form-control" style="float:left;width:80%;"/> 
			 
			</c:otherwise>
			</c:choose>
			</div>
		</div>
		
		<div class="form-group" id="content">
			<label for="introduce" class="col-sm-2 control-label"> 内容</label>
		    <div class="col-sm-8">
		    	<textarea id="editor" name="content" class="editor"  class="form-control"
							style="width: 100%;">${bean.content}</textarea>
			</div>
		</div>
						
		<table class="table">
			<tr>
				<th>&nbsp;</th>
				<td><input type="submit" class="btn btn-default" value="确认" />
					<input type="button" class="btn btn-default" value="返回"
					onclick="location.href='<c:url value="/main/advertgroup/list"/>'" /></td>
			</tr>
		</table>
	</form:form>
</div>
</body>
</html>