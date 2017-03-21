<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<title>按岗位定义权限</title>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form  method="${method }" modelAttribute="bean">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
          <!--   <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button> -->
            <h4 class="modal-title" id="myModalLabel">
               	按岗位定义权限
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
    			<div class="col-md-4">
    				<label>权限编码</label><form:errors path="limid" style="color:red;"/>
    			  <form:select id="limid" name="limid"  class="form-control" path="limid">
					<c:forEach items="${datalimm}" var="datalimm">
						<form:option value="${datalimm.limid }">${datalimm.limid }</form:option>
					</c:forEach>
					</form:select> 
    			</div>
    			<div class="col-md-4">
    				<label>操作编码</label><form:errors path="opid" style="color:red;"/>
    				<form:select id="opid" name="opid"  class="form-control" path="opid">
					
					<c:forEach items="${datalimop}" var="datalimop">
						<form:option value="${datalimop.opid }">${datalimop.opid }</form:option>
					</c:forEach>
					</form:select> 
       			</div>
    			
    			 <div class="col-md-4">
    				<label>角色编码</label><form:errors path="ucode" style="color:red;"/>
    				 <form:select id="ucode" name="ucode"  class="form-control" path="ucode">
					 <c:forEach items="${role}" var="role">
						<form:option value="${role.roleid }">${role.roleid }</form:option>
					</c:forEach>
					</form:select> 
    			</div>
    			
    		</div>
    		
			<div class="row">
			
    			 <div class="col-md-4">
    				<label>序号</label><form:errors path="idx" style="color:red;"/>
					<form:input id="idx" path="idx" cssClass="form-control" />
    			</div>
    			<div class="col-md-4">
    				<label>字段</label><form:errors path="fldname01" style="color:red;"/>
    				 <form:select id="fldname01" name="fldname01"  class="form-control" path="fldname01">
					 <c:forEach items="${datalimflds}" var="datalimflds">
						<form:option value="${datalimflds.fldname }">${datalimflds.fldname }</form:option>
					</c:forEach>
					</form:select>
    			</div>
    			<div class="col-md-4">
    				<label>范围</label><form:errors path="values01" style="color:red;"/>
					<form:input path="values01" cssClass="form-control" />
    			</div>
    		</div>
    		
    		<div class="row">
    			
    			<div class="col-md-4">
    				<label>字段</label><form:errors path="fldname02" style="color:red;"/>
    				 <form:select id="fldname02" name="fldname02"  class="form-control" path="fldname02">
					 <c:forEach items="${datalimflds}" var="datalimflds">
						<form:option value="${datalimflds.fldname }">${datalimflds.fldname }</form:option>
					</c:forEach>
					</form:select>
    			</div>
    			<div class="col-md-4">
    				<label>范围</label><form:errors path="values02" style="color:red;"/>
					<form:input path="values02" cssClass="form-control" />
    			</div>
    			<div class="col-md-4">
    				<label>字段</label><form:errors path="fldname03" style="color:red;"/>
    				 <form:select id="fldname03" name="fldname03"  class="form-control" path="fldname03">
					 <c:forEach items="${datalimflds}" var="datalimflds">
						<form:option value="${datalimflds.fldname }">${datalimflds.fldname }</form:option>
					</c:forEach>
					</form:select>
    			</div>
    		</div>
    		
    		<div class="row">
    			
    			<div class="col-md-4">
    				<label>范围</label><form:errors path="values03" style="color:red;"/>
					<form:input path="values03" cssClass="form-control" />
    			</div>
    			<div class="col-md-4">
    				<label>启用日期</label><form:errors path="startdate" style="color:red;"/>
					<form:input path="startdate" cssClass="form-control"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" />
    			</div>
    			<div class="col-md-4">
    				<label>终止日期</label><form:errors path="enddate" style="color:red;"/>
					<form:input path="enddate" cssClass="form-control"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" />
    			</div>
    		</div>
    		
    		
            <div class="row">
    			
    		</div> 
 
				<input type="submit" class="btn btn-primary" onclick="return Info()" value="提交" /> <a
					href="<c:url value='/admin/datalimm/list?child=${child }&datalimmid=${datalimmid }'/>" class="btn btn-success">返回</a>
		</div>   
		</div>  
		</div>
		</div>   
   </form:form>
</div>
</body>
<script type="text/javascript">
function Info() {
	var opid=document.getElementById("opid").value;
	var ucode=document.getElementById("ucode").value;
	var idx=document.getElementById("idx").value;
	var patten=new RegExp(/^[0-9]+(.[0-9]{1,3})?$/);	
	if(opid.length==0){
		alert('操作编码不能为空！');	
		return false; 
	}
	
	
	if(ucode.length==0){
		alert('角色编码不能为空！');	
		return false; 
	}
	
	if(idx.length==0){
		alert('序号不能为空！');	
		return false; 
	}
	
	if (!patten.test(idx)) {
		alert('类型只能是数字！');	
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