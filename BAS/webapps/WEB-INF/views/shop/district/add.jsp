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
<title>商圈添加/修改</title>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form  method="${method }" modelAttribute="bean" enctype="multipart/form-data">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
         
            <h4 class="modal-title" id="myModalLabel">
                                     商圈添加/修改
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
			   <div class="col-md-4">
    				<label>商圈编码</label><form:errors path="district" style="color:red;"/>
					 <form:input  path="district" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>商圈名称</label><form:errors path="disname" style="color:red;"/>
					 <form:input  path="disname" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>城市</label><form:errors path="city" style="color:red;"/>
    				<div class="input-group">
					 <form:input id="userid" path="city" cssClass="form-control"  readonly="true"/> 
					 <div class="input-group-addon" data-toggle="modal" data-target="#userModal"><a href="javascript:;">选择</a></div>
    			     </div>
    			</div>
				
				
    		</div>
    		
    		<div class="row">
    		    <div class="col-md-4">
    				<label>商户logo</label>
					 <input type="file" id="img" name="img"    Class="form-control" /> 
    			</div>
    			
				<div class="col-md-4">
					<label>是否启用</label><form:errors path="availabe" style="color:red;"/>
					<form:select class="form-control" path="availabe">
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
					</form:select>
				</div>
				
				<div class="col-md-4">
    				<label>预览图</label>
					 <input type="file" id="tour_img" name="tour_img"    Class="form-control" /> 
    			</div>
    		</div>
    		
				<input type="submit" class="btn btn-primary"  onclick="return Info()" value="提交" /> <a
					href="<c:url value='/admin/district/list'/>" class="btn btn-success">返回</a>
		</div>   
		</div>  
		</div>
		</div>   
   </form:form>
</div>

<!-- user模态框（Modal） -->
		<div class="modal fade" name="userModal" id="userModal" tabindex="-1" role="dialog"  aria-labelledby="userModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="userModalLabel">
		               	选择城市
		            </h4>
		         </div>
		         <div class="modal-body" style="width:590px; height:300px;">
		         	<label class="col-sm-2 control-label" > 城市名称  </label>
		         	<input type="text" name="username" class="form-control" maxlength="100" style="width:150px;float: left" />
		         	<input type="button"   class="form-control" name="search" style="width:50px;" value="查询"><br>
		         	<div style="height:240px;overflow:scroll">
		            <table class="table table-bordered">
		            	<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
								<th>城市名称</th>
							</tr>
						</thead>
						<tbody name="user">
							<c:forEach items="${citys}" var="city">
								<tr>
								<td><input type="radio" name="radio" r_username="${city }" r_userid="${city }">${city}</td>
								</tr>
							</c:forEach>
						</tbody>
		            </table>
		            </div>
		         </div>
		         <div class="modal-footer">
		            <button class="btn btn-default" data-dismiss="modal">关闭</button>
		            <button class="btn btn-primary" data-dismiss="modal" name="go">确认</button>
		         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
		
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