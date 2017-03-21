<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生成二维码</title>
</head>
<body>
	<div style="text-align: center">
		<div>
			<div><h2>二维码生成</h2></div>
			<form action="<c:url value='/main/common/createD2code'/>" method="post" enctype="multipart/form-data">
				内容<input type="text" name="contents" value="${contents}" style="width: 300px"/><br />
				<br /> 大小<input type="text" name="size" value="${size}" style="width: 200px"/>像素<br />
				<br /> 图标<input type="file" name="d2code"/> <input type="submit"
					value="提交" />
			</form>
		</div>
		<div>
			<c:if test="${not empty imageName}">
				<img src="<c:url value='/main/common/D2imag/${imageName}.png'/>" />
			</c:if>
		</div>
	</div>
</body>
</html>