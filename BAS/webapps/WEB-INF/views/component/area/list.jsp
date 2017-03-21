<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<div>
<a type="button" class="btn btn-primary" href='<c:url value="/admin/areabas/${parent}/add"/>'>添加</a>
<c:if test="${not empty grand}">
	<a type="button" class="btn btn-default" href='<c:url value="/admin/areabas/${grand}/list"/>'>返回上一级</a>
</c:if>

</div>
<div>

<table class="table table-hover">
<thead>
<tr>
<th colspan="5" bgcolor="#EDF3F4" style="text-align: center;">上级地区 - ${parentName}</th>
</tr>
</thead>
<tr>
<c:if test="${empty areas}">
	<td colspan="5" style="text-align: center; color: red;">无下级地区 </td>
</c:if>
<c:forEach items="${areas}" var="area" varStatus="idx">
<td>
<span areaId="${area.id}">
	<a href='<c:url value="/admin/areabas/${area.id}/list"/>' >${area.name}:${area.enName}</a>
	<a href='<c:url value="/admin/areabas/${area.id}/update"/>'>[编辑]</a>
	<a name="remove" >[删除]</a>
</span>
</td>
<c:if test="${idx.count%5==0 }">
	</tr><tr>
</c:if>
<c:if test="${ idx.count==fn:length(areas) && idx.count%5!=0 }">
	<c:forEach begin="0" end="${5-idx.count%5-1 }">
		<td></td>
	</c:forEach>
</c:if>
</c:forEach>

</tr>
</table>
</div>
</body>
<script type="text/javascript">
$('a[name="remove"]').click(function(){
	if(confirm("是否确定要删除？")){
	var span = this.parentNode;
	var areaId = span.getAttribute("areaId");
	$.post('<c:url value="/admin/areabas/del.ajax"/>',{"areaId":areaId},function(data){
		if(data){
			span.remove();
		}
	})
	}
})
</script>
</html>