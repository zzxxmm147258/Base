<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<title>权限类型</title>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form action="${pageContext.request.contextPath}/admin/datalimm/limid" method="${method }" modelAttribute="bean">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
          <!--   <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button> -->
            <h4 class="modal-title" id="myModalLabel">
               	权限类型
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
    			<div class="col-md-4">
    				<label>权限编码</label><form:errors path="limid" style="color:red;"/>
					<form:input id="limid"  path="limid" cssClass="form-control" />
    			</div>
    			<div class="col-md-4">
    				<label>权限名称</label><form:errors path="limnm" style="color:red;"/>
					<form:input id="limnm" path="limnm" cssClass="form-control" />
    			</div>
    			<div class="col-md-4">
    				<label>标记</label><form:errors path="flags" style="color:red;"/>
					<form:input path="flags" cssClass="form-control" />
    			</div>
    		</div>
			<div class="row">
    			
    			<div class="col-md-6">
    				<label>启用标记</label><form:errors path="startflags" style="color:red;"/>
					<form:input path="startflags" cssClass="form-control" />
    			</div>
    			<div class="col-md-6">
    				<label>序号</label><form:errors path="ord" style="color:red;"/>
					<form:input path="ord" cssClass="form-control" />
    			</div>
    		</div>
 
				<input type="submit" class="btn btn-primary" onclick="return Info()" value="提交" /> <a
					href="<c:url value='/admin/datalimm/list'/>" class="btn btn-success">返回</a>
		</div>   
		</div>  
		</div>
		</div>   
   </form:form>
</div>
</body>
<script type="text/javascript">
function Info() {
	var limid=document.getElementById("limid").value;
	var limnm=document.getElementById("limnm").value;
	var patten=new RegExp(/^[0-9]+(.[0-9]{1,3})?$/);	
	if(limid.length==0){
		alert('权限编码不能为空！');	
		return false; 
	}
	if(limnm.length==0){
		alert('权限名称不能为空！');	
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