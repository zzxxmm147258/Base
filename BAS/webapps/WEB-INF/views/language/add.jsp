<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>单元信息添加/修改</title>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>

<script type="text/javascript" src="<c:url value='/resources/js/jquery.validate.js'/>"></script>
<script type="text/javascript">
	$().ready(function (){
		$("#inputForm").validate({
			rules:{
				code: "required",
				zh: "required",
				en: "required"
			}
		});
		
	});
</script>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form id="inputForm" method="${method }" modelAttribute="bean" enctype="multipart/form-data">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
         
            <h4 class="modal-title" id="myModalLabel">
                             语言添加/修改
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
			<input type="hidden" name="id" value="${bean.id }">
				<div class="col-md-4">
    				<label>编码</label><form:errors path="code" style="color:red;"/>
					 <form:input id="code" path="code" cssClass="form-control" /> 
    			</div>
				 <div class="col-md-4">
    				<label>中文</label><form:errors path="zh" style="color:red;"/>
					 <form:input id="zh" path="zh" cssClass="form-control" /> 
    			</div>
    			<div class="col-md-4">
    				<label>英文</label><form:errors path="en" style="color:red;"/>
					 <form:input id="en" path="en" cssClass="form-control" /> 
    			</div>
    		</div>
    	
    	</div>
    		
			<input type="submit" class="btn btn-primary" value="提交" /> 
			<a href="<c:url value='/admin/language/list'/>" class="btn btn-success">返回</a>
		</div>   
		</div>  
		</div>
		  
   </form:form>
</div>
<script type="text/javascript">
$('#myModal').modal('show');

function onlyNumber(obj){
	var value = obj.value;
	if(''!=value){
		obj.value = value.replace(/[^\d]/g,'');
	}
}

</script>
</body>

</html>