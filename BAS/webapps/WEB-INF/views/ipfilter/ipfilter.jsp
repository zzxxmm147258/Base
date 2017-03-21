<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/lc_switch.css'/>">
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/bootstrap.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/input.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/lc_switch.js'/>"></script>
<script type="text/javascript">
	$().ready(function(){
		
		$('input[name="ispassd"]').lc_switch('允许','禁止');
		
		
		var $delete = $("#delete");
		
		$delete.click(function() {
			var $this = $(this);
			$.dialog({
				type: "warn",
				content: "确认删除？",
				onOk: function() {
					$.ajax({
						url: "delete.ajax",
						type: "POST",
						data: {id: $this.attr("val")},
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.success) {
								$this.closest("tr").remove();
							}
						}
					});
				}
			});
			return false;
		});
	});
</script>
<title>Insert title here</title>
</head>
<body>
	<div class="bar">
		<a class="btn btn-default" href="<c:url value='add'/>">增加</a>
	</div>
	<div class="table">
		<table class="table table-hover table-bordered">
			<thead>
				<tr>
					<th><input type="checkbox" id="selectAll" /></th>
					<th>IP</th>
					<th>名称</th>
					<th>备注</th>
					<th>允许/禁止</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${iplist}" var="ipmodel">
					<tr id="${ipmodel.id}">
						<td><input type="checkbox" name="ids"
							value="${ipmodel.id}" /></td>
						<td>${ipmodel.ip} </td>
						<td>${ipmodel.name} </td>
						<td>${ipmodel.remark}</td>
						<td><input type="checkbox" name="ispassd" value="${ipmodel.ispassd}" disabled="disabled" <c:if test="${ipmodel.ispassd}">checked="checked"</c:if>/></td>
						<td><a href="edit?id=${ipmodel.id}">[编辑]</a>
						    <a href="javascript:;" id="delete" name="delete" val="${ipmodel.id}">[删除]</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>