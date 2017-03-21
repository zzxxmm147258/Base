<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>">
</head>
<body>
	<div>
			<p>
				<strong>编码：</strong> ${bean.rxcode }
			</p>
			<p>
				<strong>字段名：</strong> ${bean.fldname }
			</p>
			<p>
				<strong>字段显示名：</strong> ${bean.flddisplayname }
			</p>
			<p>
				<strong>该字段对应Excel列号：</strong> ${bean.excelcol }
			</p>
			<p>
				<strong>字段类型：</strong> ${bean.fldtype }
			</p>
			<p>
				<strong>大小：</strong> ${bean.fldsize }
			</p>
			<p>
				<strong>小数：</strong> ${bean.flddecimal }
			</p>
			<p>
				<strong>缺省值：</strong> ${bean.flddefault }
			</p>
			<p>
				<strong>分组描述：</strong> ${bean.groupdesc }
			</p>
			<p>
				<strong>选项：</strong> ${bean.flags }
			</p>
			<p>
				<strong>排序号：</strong> ${bean.idx }
			</p>
			<p>
				<strong>groovy：</strong> ${bean.groovy }
			</p>
			<p>
				<strong>操作人：</strong> ${bean.operator }
			</p>
			<p>
				<strong>创建时间：</strong><fmt:formatDate value="${bean.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/> 
			</p>
			<p>
				<strong>修改日期：</strong><fmt:formatDate value="${bean.modifyDate }" pattern="yyyy-MM-dd HH:mm:ss"/>  
			</p>
			
	</div>
		<c:if test="${param['view']!=null}">
		<a href="${pageContext.request.contextPath}/admin/readexceldef/list" class="btn btn-success">返回</a>
	</c:if>
	<c:if test="${param['del']!=null}">
		<form:form modelAttribute="bean" method="delete" action="${pageContext.request.contextPath}/admin/readexceldefg/${bean.rxcode }/${bean.fldname}">
			<button class="btn btn-danger" >删除</button><a href="${pageContext.request.contextPath}/admin/readexceldef/list" class="btn btn-success">返回</a>
		</form:form>
		</c:if>
</body>
</html>