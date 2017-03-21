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
				number: "required",
				name: "required",
				url: "required",
				version_date: "required"
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
                               版本更新添加/修改
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
			<input type="hidden" name="id" value="${bean.id }">
				<div class="col-md-4">
    				<label>版本号</label><form:errors path="number" style="color:red;"/>
					 <form:input id="number" path="number" cssClass="form-control" /> 
    			</div>
				<div class="col-md-4">
    				<label>版本名称</label><form:errors path="name" style="color:red;"/>
					 <form:input id="name" path="name" cssClass="form-control" /> 
    			</div>
    			<div class="col-md-4">
    				<label>更新类型</label><form:errors path="type" style="color:red;"/>
					<form:select id="type" class="form-control" path="type">
						<form:option  value="Android">Android</form:option>
						<form:option  value="IOS">IOS</form:option>
					</form:select>
    			</div>
    		</div>
    		
    		<div class="row">
    		    <div class="col-md-12">
    				<label>更新内容(多条记录用英文分号“  ;  ”分开)</label><form:errors path="content" style="color:red;"/>
					 <form:input id="content" path="content" cssClass="form-control" /> 
    			</div>
    		</div>
    		
    		<div class="row">
    		    <div class="col-md-12">
    				<label>下载链接</label><form:errors path="url" style="color:red;"/>
					 <form:input id="url" path="url" cssClass="form-control" /> 
    			</div>
    		</div>
    		
    		<div class="row">	
    			 <div class="col-md-4">
    				<label>是否必要更新</label><form:errors path="isnecessary" style="color:red;"/>
					<form:select id="isnecessary" class="form-control" path="isnecessary">
						<form:option  value="0">否</form:option>
						<form:option  value="1">是</form:option>
					</form:select>
    			</div>
    			
    			<div class="col-md-4">
    				<label>更新时间</label><form:errors path="version_date" style="color:red;"/>
					 <form:input path="version_date" cssClass="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  readonly="true" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>项目简称</label><form:errors path="attr1" style="color:red;"/>
					 <form:input id="attr1" path="attr1" cssClass="form-control" /> 
    			</div>
    		</div>
    		
    	</div>
    		
			<input type="submit" class="btn btn-primary" value="提交" /> 
			<a href="<c:url value='/main/bas/version/list'/>" class="btn btn-success">返回</a>
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