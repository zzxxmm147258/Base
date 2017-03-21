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
				<strong>名字：</strong> ${bean.rxname }
			</p>
			<p>
				<strong>表名：</strong> ${bean.tblname }
			</p>
			<p>
				<strong>数据模型：</strong> ${bean.model }
			</p>
			<p>
				<strong>读入Excel表单名：</strong> ${bean.excelsheetname }
			</p>
			<p>
				<strong>Excel起始读入行：</strong> ${bean.excelfromrow }
			</p>
			<p>
				<strong>Excel每行循环执行次数：</strong> ${bean.fortimes }
			</p>
			<p>
				<strong>groovy：</strong> ${bean.groovy }
			</p>
			<p>
				<strong>选项：</strong> ${bean.flags }
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
		<form:form modelAttribute="bean" method="delete" action="${pageContext.request.contextPath}/admin/readexceldef/${bean.rxcode }">
			<button class="btn btn-danger" >删除</button><a href="${pageContext.request.contextPath}/admin/readexceldef/list" class="btn btn-success">返回</a>
		</form:form>
		</c:if>
</body>
</html>