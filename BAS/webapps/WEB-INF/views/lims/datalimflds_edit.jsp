<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<title>权限字段</title>

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
               	权限字段
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
    				<label>字段名</label><form:errors path="fldname" style="color:red;"/>
					<form:input id="fldname" path="fldname" cssClass="form-control" />
    			</div>
    			
    			<div class="col-md-4">
    				<label>字段显示名称</label><form:errors path="cfldname" style="color:red;"/>
					<form:input id="cfldname" path="cfldname" cssClass="form-control" />
    			</div>
    			
    		</div>
    		
			<div class="row">
    			
    			<div class="col-md-4">
    				<label>类型</label><form:errors path="fldtype" style="color:red;"/>
    				<form:select id="fldtype" name="fldtype"  class="form-control" path="fldtype">
						<form:option value="2">2</form:option>
						<form:option value="4">4</form:option>
						<form:option value="12">12</form:option>
						<form:option value="91">91</form:option>
					</form:select> 
    			</div>
    			
    			 <%-- <div class="col-md-4">
    				<label>序号</label><form:errors path="idx" style="color:red;"/>
					<form:input path="idx" cssClass="form-control" />
    			</div>  --%>
    			
    			<%-- <div class="col-md-4">
    				<label>选项</label><form:errors path="fldopts" style="color:red;"/>
					<form:input path="fldopts" cssClass="form-control" />
    			</div>  --%>
    			
    		</div>
    		
    		
           <%--  <div class="row">
    			<div class="col-md-4">
    				<label>不做权限检查的值</label><form:errors path="unlimckvals" style="color:red;"/>
					<form:input path="unlimckvals" cssClass="form-control" />
    			</div>

    		</div> --%>
  
 
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
	var fldname=document.getElementById("fldname").value;
	var cfldname=document.getElementById("cfldname").value;
	var fldtype=document.getElementById("fldtype").value;
	var patten=new RegExp(/^[0-9]+(.[0-9]{1,3})?$/);	
	if(fldname.length==0){
		alert('字段名不能为空！');	
		return false; 
	}
	
	if(cfldname.length==0){
		alert('字段显示名称不能为空！');	
		return false; 
	}
	
	if(fldtype.length==0){
		alert('类型不能为空！');	
		return false; 
	}
	
	if (!patten.test(fldtype)) {
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