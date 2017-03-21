<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/choseModal.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/bootstrap-multiselect/js/bootstrap-multiselect.js'/>"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap-multiselect/css/bootstrap-multiselect.css'/>"
	type="text/css" />
<title>用户商户添加/修改</title>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form  method="${method }" modelAttribute="bean">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
         
            <h4 class="modal-title" id="myModalLabel">
                                     用户商户添加/修改
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
    			<div class="col-md-4">
    				<label>用户名</label><form:errors path="userid" style="color:red;"/>
    				  	<form:select id="userStr" path="userid"  class="multiselect-search" >
							<c:forEach items="${username}" var="user">
								<form:option value="${user.userid }">${user.username }</form:option>
							</c:forEach>
							</form:select>
							<input name="userStr" style="display:none">
    			</div>
    			
    			<div class="col-md-4">
    				<label>商户名称</label><form:errors path="shopId" style="color:red;"/>
    				     <form:select id="Str" path="shopId"  class="multiselect-search" >
							<c:forEach items="${shopname}" var="shopname">
								<form:option value="${shopname.shopId }">${shopname.shopname }</form:option>
							</c:forEach>
							</form:select>
							<input name="Str" style="display:none">
				</div>
				
				<div class="col-md-4">
					<label>是否启用</label><form:errors path="availabe" style="color:red;"/>
					<form:select class="form-control" path="availabe">
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
					</form:select>
				</div>
		
    		</div>
    		
				<input type="submit" class="btn btn-primary"  onclick="return Info()" value="提交" /> <a
					href="<c:url value='/admin/usershop/list'/>" class="btn btn-success">返回</a>
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

$(document).ready(function() {
	$('#userStr').multiselect({
		enableFiltering:true,
		maxHeight: 300,
		buttonWidth: '100%',
		onDropdownHide: function (event) {
        	var li = $('ul.dropdown-menu li.active');
        	var roleStr = "";
        	for(var i=0; i<li.length;i++){
        		var value = $(li[i]).find('a label input').val();
        		roleStr += value+";";
        	}
        	$('input[name=userStr]').val(roleStr);
    	 }
	})
})

$(document).ready(function() {
	$('#Str').multiselect({
		enableFiltering:true,
		maxHeight: 300,
		buttonWidth: '100%',
		onDropdownHide: function (event) {
        	var li = $('ul.dropdown-menu li.active');
        	var roleStr = "";
        	for(var i=0; i<li.length;i++){
        		var value = $(li[i]).find('a label input').val();
        		roleStr += value+";";
        	}
        	$('input[name=Str]').val(roleStr);
    	 }
	})
})

</script>
</html>