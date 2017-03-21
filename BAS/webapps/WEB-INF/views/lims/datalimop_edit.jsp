<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<title>权限操作</title>

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
                                               权限操作
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
					<%-- <form:input path="limid" cssClass="form-control" /> --%>
    			</div>
    			
    			<div class="col-md-4">
    				<label>操作编码</label><form:errors path="opid" style="color:red;"/>
					<form:input id="opid" path="opid" cssClass="form-control" />
    			</div>
    			
    			<div class="col-md-4">
    				<label>操作名称</label><form:errors path="opnm" style="color:red;"/>
					<form:input id="opnm" path="opnm" cssClass="form-control" />
    			</div>
    			
    			
    		</div>
    		
				<input type="submit" class="btn btn-primary"  onclick="return Info()" value="提交" /> <a
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
	var opnm=document.getElementById("opnm").value;
	if(opid.length==0){
		alert('操作编码不能为空！');	
		return false; 
	}
	if(opnm.length==0){
		alert('操作名称不能为空！');	
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