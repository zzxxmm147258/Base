<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/js/choseModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<title>企业微信成员添加/修改</title>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form  method="${method }" modelAttribute="bean">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
         
            <h4 class="modal-title" id="myModalLabel">
                                     企业微信成员添加/修改
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
    			<div class="col-md-4">
    				<label>是否关注</label><form:errors path="subscribe" style="color:red;"/>
					<form:select class="form-control" path="subscribe">
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
					</form:select>
    			</div>
    			
    			<div class="col-md-4">
    				<label>会员ID</label><form:errors path="userid" style="color:red;"/>
    				 <form:input  path="userid" cssClass="form-control" /> 
    			</div>
				
				<div class="col-md-4">
    				<label>所属部门</label><form:errors path="department" style="color:red;"/>
    				 <form:input  path="department" cssClass="form-control" /> 
    			</div>
    		</div>
    		
    		<div class="row">
    		    <div class="col-md-4">
    				<label>手机号</label><form:errors path="mobile" style="color:red;"/>
    				 <form:input  path="mobile" cssClass="form-control" /> 
    			</div>
    			<div class="col-md-4">
    				<label>邮箱</label><form:errors path="email" style="color:red;"/>
    				 <form:input  path="email" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>微信账号</label><form:errors path="weixinid" style="color:red;"/>
    				 <form:input  path="weixinid" cssClass="form-control" /> 
    			</div>
    		</div>
			<div class="row">
			      <div class="col-md-4">
					<label>禁用</label><form:errors path="locked" style="color:red;"/>
					<form:select class="form-control" path="locked">
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
					</form:select>
					</div>
					
					<div class="col-md-4">
    				   <label>标识</label><form:errors path="flags" style="color:red;"/>
    				   <form:input  path="flags" cssClass="form-control" /> 
    			    </div>
    			    
    			    <div class="col-md-4">
    				   <label>备注</label><form:errors path="remark" style="color:red;"/>
    				   <form:input  path="remark" cssClass="form-control" /> 
    			    </div>
			</div>
		 
		    <div class="row">
					<div class="col-md-4">
    				   <label>设置编码</label><form:errors path="appkey" style="color:red;"/>
    				   <form:input  path="appkey" cssClass="form-control" /> 
    			    </div>
			</div>
    		
    		
				<input type="submit" class="btn btn-primary"  onclick="return Info()" value="提交" /> <a
					href="<c:url value='/main/mainte/weixinbus/list'/>" class="btn btn-success">返回</a>
		</div>   
		</div>  
		</div>
		</div>   
   </form:form>
</div>
		
</body>
<script type="text/javascript">

function Info() {
	var truename=document.getElementById("truename").value;
	if(truename.length==0){
		alert('用户名不能为空！');	
		return false; 
	}

	return true;
}


$('#myModal').modal('show');

function onlyNumber(obj){
	var value = obj.value;
	if(''!=value){
		obj.value = value.replace(/[^\d]/g,'');
	}
}
</script>
</html>