<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<title>国家添加/修改</title>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form  method="${method }" modelAttribute="bean">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
         
            <h4 class="modal-title" id="myModalLabel">
                                     国家添加/修改
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
    			<div class="col-md-4">
    				<label>国家名称</label><form:errors path="title" style="color:red;"/>
					 <form:input  path="title" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>编号</label><form:errors path="code" style="color:red;"/>
					 <form:input  path="code" cssClass="form-control" />  			
				</div>
				
				<div class="col-md-4">
					<label>是否启用</label><form:errors path="locked" style="color:red;"/>
					<form:select class="form-control" path="locked">
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
					</form:select>
				</div>
		
    		</div>
    		
				<input type="submit" class="btn btn-primary"  onclick="return Info()" value="提交" /> <a
					href="<c:url value='/admin/countrybas/list'/>" class="btn btn-success">返回</a>
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