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
<script type="text/javascript" src="<c:url value='/resources/js/jquery.validate.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/bootstrap-multiselect/js/bootstrap-multiselect.js'/>"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap-multiselect/css/bootstrap-multiselect.css'/>"
	type="text/css" />
	
<script type="text/javascript">
	$().ready(function (){
		$("#inputForm").validate({
			rules:{
				buildingNo:"required",
				buildingName:"required",
				idx:{
					digits:true
				}
			}
		});
		
		$('#blockId').multiselect({
			enableFiltering:true,
			maxHeight: 290,
			buttonWidth: '100%',
		});
		
		var platformStr = "${bean.blockId}";
		pf = new Array;
		pf = platformStr.split(",");
		if(pf.length>0){
			for(var i=0;i<pf.length;i++){
				$('#platform').multiselect('select', pf[i]);
			}
		} 
		
	});
</script>

<title>单元添加/修改</title>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form  id="inputForm" method="${method }" modelAttribute="bean" enctype="multipart/form-data">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
         
            <h4 class="modal-title" id="myModalLabel">
                                     单元添加/修改
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
			   <div class="col-md-4">
    				<label>单元楼号</label><form:errors path="buildingNo" style="color:red;"/>
					 <form:input id="buildingNo" path="buildingNo" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>单元名称</label><form:errors path="buildingName" style="color:red;"/>
					 <form:input id="buildingName" path="buildingName" cssClass="form-control" /> 
    			</div>
    			
			    <div class="col-sm-4">
    			<label>街区</label><form:errors path="blockId" style="color:red;"/>
    			<form:select id="blockId" class="multiselect-search" path="blockId">
    				 <c:forEach items="${blockBas}" var="blockBas">
						<form:option value="${blockBas.blockId}">${blockBas.blockName}</form:option>
					 </c:forEach>
				</form:select>
			    </div>
    		</div>
    		
    		<div class="row">
    		    <div class="col-md-4">
    				<label>街区logo</label>
					 <input type="file" id="img" name="img"    Class="form-control" /> 
    			</div>
    			<div class="col-md-4">
    				<label>排序</label><form:errors path="idx" style="color:red;"/>
					 <form:input id="idx" path="idx" cssClass="form-control" /> 
    			</div>
				<div class="col-md-4">
					<label>是否启用</label><form:errors path="availabe" style="color:red;"/>
					<form:select class="form-control" path="availabe">
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
					</form:select>
				</div>
    		</div>
    		
    		<div class="row">
    		    <div class="col-md-4">
    				<label>预览图</label>
					 <input type="file" id="tour_img" name="tour_img"    Class="form-control" /> 
    			</div>
    		</div>
				<input type="submit" class="btn btn-primary"  onclick="return Info()" value="提交" /> <a
					href="<c:url value='/admin/building/list'/>" class="btn btn-success">返回</a>
		</div>   
		</div>  
		</div>
		</div>   
   </form:form>
</div>
</body>
<script type="text/javascript">
var updateSuccess = '${updateSuccess}';
if(updateSuccess != null && updateSuccess != ''){
	$('#close').attr("closeNow","true");//关闭页面
}


	var inputArray = new Array("userid","username"); 	//输入框的path
var fuzzyArray = inputArray;						//模糊查询条件的字段
		
//需要传入的参数：表名，list.ajax链接地址，输入框需要的字段，模糊查询条件
choseModal("user",'<c:url value="/common/areabas/list.ajax"/>',inputArray,fuzzyArray);

 
function getHtml(tableName,json){        //配合choseModal.js使用
	var html = "";
	if(tableName=="user"){
		for(var i=0;i<json.length;i++){    //拼radio的属性时候记得加 r_ 前缀
			html += '<tr><td><input type="radio" name="radio" r_username="'+json[i].username+'" r_userid="'+json[i].userid+'">'+json[i].username+'</td></tr>';
		}
	}
	return html;
} 

$('select').change(function() {  //角色ID的select改变事件
	var id = this.getAttribute('id');
	var name = $('#' + id + ' option:selected').attr('name');
	idName = id + 'Name';
	$('#' + idName).val(name);
})



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