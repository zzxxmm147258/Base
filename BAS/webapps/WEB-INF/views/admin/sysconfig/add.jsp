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
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/uploadify.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/common.css'/>">
<script type="text/javascript">
	var basePath = "<%=basePath%>";
</script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/bootstrap.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery.uploadify.min.js'/>"></script>
<script type="text/javascript">

</script>
<title>Insert title here</title>
</head>
<body>
<c:choose>
  <c:when test="${not empty pluginConfig.code}">
  <form id="inputForm" class="form-horizontal" action="update" role="form"
		method="post">

   </c:when>
   <c:otherwise>
   <form id="inputForm" class="form-horizontal" action="save" role="form"
		method="post">
   </c:otherwise>
</c:choose>
<div id="myTabContent" class="tab-content">
 <div class="tab-pane fade in active" id="base">
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">唯一编码</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.code} ">
			    <input type="text" name="code" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="code" class="form-control" value="${pluginConfig.code}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
     <label for="createdate" class="col-sm-2 control-label">名称</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.name} ">
				  <input type="text" name="name" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="name" class="form-control" value="${pluginConfig.name}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">选项1</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.option1} ">
			    <input type="text" name="option1" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="option1" class="form-control" value="${pluginConfig.option1}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">选项2</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.option2} ">
				  <input type="text" name="option2" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="option2" class="form-control" value="${pluginConfig.option2}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">选项3</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.option3} ">
			    <input type="text" name="option3" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="option3" class="form-control" value="${pluginConfig.option3}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">选项4</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.option4} ">
				  <input type="text" name="option4" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="option4" class="form-control" value="${pluginConfig.option4}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">选项5</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.option5} ">
			    <input type="text" name="option5" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="option5" class="form-control" value="${pluginConfig.option5}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">选项6</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.option6} ">
				  <input type="text" name="option6" class="form-control"/>
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="option6" class="form-control" value="${pluginConfig.option6}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">选项7</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.option7} ">
			    <input type="text" name="option7" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="option7" class="form-control" value="${pluginConfig.option7}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">选项8</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.option8} ">
				  <input type="text" name="option8" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="option8" class="form-control" value="${pluginConfig.option8}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">选项9</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.option9} ">
			    <input type="text" name="option9" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="option9" class="form-control" value="${pluginConfig.option9}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">选项10</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.option10} ">
				  <input type="text" name="option10" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="option10" class="form-control" value="${pluginConfig.option10}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">选项11</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.option11} ">
			    <input type="text" name="option11" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="option11" class="form-control" value="${pluginConfig.option11}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">选项12</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.option12} ">
				  <input type="text" name="option12" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="option12" class="form-control" value="${pluginConfig.option12}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">选项13</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.option13} ">
			    <input type="text" name="option13" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="option13" class="form-control" value="${pluginConfig.option13}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">选项14</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.option14} ">
				  <input type="text" name="option14" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="option14" class="form-control" value="${pluginConfig.option14}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">选项15</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.option15} ">
			    <input type="text" name="option15" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="option15" class="form-control" value="${pluginConfig.option15}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">选项16</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.option16} ">
				  <input type="text" name="option16" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="option16" class="form-control" value="${pluginConfig.option16}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">选项17</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.option17} ">
			    <input type="text" name="option17" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="option17" class="form-control" value="${pluginConfig.option17}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">选项18</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.option18} ">
				  <input type="text" name="option18" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="option18" class="form-control" value="${pluginConfig.option18}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
     <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">整数选项1</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.i_option1} ">
			    <input type="number" name="i_option1" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="number"  name="i_option1" class="form-control" value="${pluginConfig.i_option1}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">整数选项2</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.i_option2} ">
				  <input type="number" name="i_option2" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="number"  name="i_option2" class="form-control" value="${pluginConfig.i_option2}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
     <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">数值选项1</label>
             <div class="col-sm-2" style="float:left;">
              <c:choose>
			  <c:when test="${empty pluginConfig.n_option1} ">
			    <input type="text" name="n_option1" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="n_option1" class="form-control" value="${pluginConfig.n_option1}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
              <label for="createdate" class="col-sm-2 control-label">数值选项2</label>
			   <div class="col-sm-2">
				<c:choose>
			     <c:when test="${empty pluginConfig.n_option2} ">
				  <input type="text" name="n_option2" class="form-control" />
				 </c:when>
			   <c:otherwise>
			     <input type="text"  name="n_option2" class="form-control" value="${pluginConfig.n_option2}"/>
			   </c:otherwise>
			  </c:choose>
			  </div>
     </div> 
   <div class="form-group">
    <label for="orderid" class="col-sm-2 control-label">备注</label>
             <div class="col-sm-2" style="float:left;width:850px;">
              <c:choose>
			  <c:when test="${empty pluginConfig.remark} ">
			    <input type="text" name="remark" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text"  name="remark" class="form-control" value="${pluginConfig.remark}"/>
			  </c:otherwise>
			  </c:choose>
		     </div>
     </div> 
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