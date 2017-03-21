<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<title>更新记录添加/修改</title>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form  method="${method }" modelAttribute="bean">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
         
            <h4 class="modal-title" id="myModalLabel">
                                     更新记录表添加/修改
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
			    <div class="col-md-4">
    				<label>编号</label><form:errors path="id" style="color:red;"/>
					 <form:input  path="id" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>描述</label><form:errors path="updateName" style="color:red;"/>
					 <form:input  path="updateName" cssClass="form-control" /> 
    			</div>
    			
				<div class="col-md-4">
					<label>时间</label><form:errors path="supdateDate" style="color:red;"/>
	               	<form:input path="supdateDate" cssClass="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true"/>
	               </div>
	           </div>
    			
    			<div class="col-md-4">
    				<label>自定义1</label><form:errors path="attrName1" style="color:red;"/>
					 <form:input  path="attrName1" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>自定义2</label><form:errors path="attrName2" style="color:red;"/>
					 <form:input  path="attrName2" cssClass="form-control" /> 
    			</div>
    	</div>
    	
				<input type="submit" class="btn btn-primary"  onclick="return Info()" value="提交" /> 
				<a href="<c:url value='/admin/quartzupdate/list'/>" class="btn btn-success">返回</a>
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