<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<title>商户添加/修改</title>

</head>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form  method="${method }" modelAttribute="bean" enctype="multipart/form-data">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
         
            <h4 class="modal-title" id="myModalLabel">
                                     商户添加/修改
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
			    <div class="col-md-4">
    				<label>商户编码</label><form:errors path="shopId" style="color:red;"/>
					 <form:input id="shopId" path="shopId" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>商户名称</label><form:errors path="shopname" style="color:red;"/>
					 <form:input  path="shopname" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>商圈名称</label><form:errors path="district" style="color:red;"/>
    				<form:select class="form-control" path="district">
    				<c:forEach items="${district}" var="district">
						<form:option value="${district.district}">${district.disname }</form:option>
					</c:forEach>
					</form:select> 			
				</div>
				
				
    	</div>
    	
    	<div class="row">
    	        <div class="col-md-4">
    				<label>商户地址</label><form:errors path="shopAddress" style="color:red;"/>
					 <form:input  path="shopAddress" cssClass="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>商户logo</label>
					 <input type="file" id="img" name="img"    Class="form-control" /> 
    			</div>
    			
    			<div class="col-md-4">
    				<label>商户类型</label><form:errors path="shopType" style="color:red;"/>
					 <form:input  path="shopType" cssClass="form-control" /> 
    			</div>
    	</div>
    	
    	<div class="row">
    	     <div class="col-md-4">
    				<label>人均消费</label><form:errors path="perConsume" style="color:red;"/>
					 <form:input id="perConsume"  path="perConsume" cssClass="form-control" /> 
    		 </div>
    	     <div class="col-md-4">
    				<label>图片</label>
					<input type="file" id="img1" name="img1"  multiple="multiple"  Class="form-control" />  
    		 </div>
    	     <div class="col-md-4">
    				<label>特色图片</label>
    				
					<input type="file" id="img2" name="img2"   multiple="multiple"  Class="form-control" />  
    		 </div>
    	</div>
    	
		<div class="row">	
				<div class="col-md-4">
					<label>是否启用</label><form:errors path="availabe" style="color:red;"/>
					<form:select class="form-control" path="availabe">
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
					</form:select>
				</div>
				<div class="col-md-4">
    				<label>经度</label><form:errors path="longitude" style="color:red;"/>
					 <form:input id="longitude"  path="longitude" cssClass="form-control" /> 
    			</div>
    			<div class="col-md-4">
    				<label>纬度</label><form:errors path="latitude" style="color:red;"/>
					 <form:input id="latitude"  path="latitude" cssClass="form-control" /> 
    			</div>
    	</div>
    	<div class="row">
    	     <div class="col-md-4">
    				<label>电话</label><form:errors path="phone" style="color:red;"/>
					 <form:input id="phone"  path="phone" cssClass="form-control" /> 
    		 </div>
    	     <div class="col-md-4">
    				<label>排序</label><form:errors path="sort" style="color:red;"/>
					 <form:input id="sort"  path="sort" cssClass="form-control" /> 
    		 </div>
    	     <div class="col-md-4">
    				<label>人气</label><form:errors path="nice" style="color:red;"/>
					 <form:input id="nice"  path="nice" cssClass="form-control" /> 
    		 </div>
    	</div>
    	
    	<div class="row">
    	       <div class="col-md-4">
    				<label>商圈排行</label><form:errors path="districtIndex" style="color:red;"/>
					 <form:input id="districtIndex"  path="districtIndex" cssClass="form-control" /> 
    		   </div>
    		   <div class="col-md-4">
    				<label>街区</label><form:errors path="block" style="color:red;"/>
					 <form:input id="block"  path="block" cssClass="form-control" /> 
    		   </div>
    		   <div class="col-md-4">
    				<label>街区排行</label><form:errors path="blockIndex" style="color:red;"/>
					 <form:input id="blockIndex"  path="blockIndex" cssClass="form-control" /> 
    		   </div>
    	</div>
    	<div class="row">
    	        <div class="col-md-4">
    				<label>楼层</label><form:errors path="floors" style="color:red;"/>
					 <form:input id="floors"  path="floors" cssClass="form-control" /> 
    		   </div>
    		   <div class="col-md-4">
    				<label>楼号</label><form:errors path="buildingNo" style="color:red;"/>
					 <form:input id="buildingNo"  path="buildingNo" cssClass="form-control" /> 
    		   </div>
    	</div>
    	
    		
				<input type="submit" class="btn btn-primary"  onclick="return Info()" value="提交" /> <a
					href="<c:url value='/admin/shop/list'/>" class="btn btn-success">返回</a>
		</div>   
		</div>  
		</div>
		</div>   
   </form:form>
</div>

<div class="modal fade" id="pictureModal" tabindex="-1" role="dialog" 
   aria-labelledby="editModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div id="wrapper">
	            <div id="container">
	                <div id="uploader">
	                    <div class="queueList">
	                        <div id="dndArea" class="placeholder">
	                            <div id="filePicker"></div>
	                            <p>或将照片拖到这里，单次最多可选300张</p>
	                        </div>
	                    </div>
	                    <div class="statusBar" style="display:none;">
	                        <div class="progress">
	                            <span class="text">0%</span>
	                            <span class="percentage"></span>
	                        </div><div class="info"></div>
	                        <div class="btns">
	                            <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
<script type="text/javascript">
function Info() {
	
	var districtIndex=document.getElementById("districtIndex").value;
	var blockIndex=document.getElementById("blockIndex").value;
	var floors=document.getElementById("floors").value;
	
	var shopId=document.getElementById("shopId").value;
	var longitude=document.getElementById("longitude").value;
	var latitude=document.getElementById("latitude").value;
	var nice=document.getElementById("nice").value;
	var sort=document.getElementById("sort").value;
	var phone=document.getElementById("phone").value;
	
	var perConsume=document.getElementById("perConsume").value;
	var patten=new RegExp(/^[0-9]+(.[0-9]{1,7})?$/);
	var du=new RegExp(/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/);
	var zeng=new RegExp(/^(0|[1-9][0-9]*)$/);
	if(shopId.length==0){
		alert('商户编码不能为空！');	
		return false; 
	}
	if(longitude.length>0){
		if (!du.test(longitude)) {
			alert('经度只能是数字！');	
			return false; 
		}	 
	}
	if(latitude.length>0){
		if (!du.test(latitude)) {
			alert('纬度只能是数字！');	
			return false; 
		}	 
	}
	if(perConsume.length>0){
		if (!patten.test(perConsume)) {
			alert('人均消费只能是数字！');	
			return false; 
		}	 
	}
	if(nice.length>0){
		if (!zeng.test(nice)) {
			alert('人气数只能是数字！');	
			return false; 
		}	 
	}
	if(sort.length>0){
		if (!zeng.test(sort)) {
			alert('排序只能是数字！');	
			return false; 
		}	 
	}
	if(phone.length>0){
		if (!patten.test(phone)) {
			alert('填写正确手机号！');	
			return false; 
		}	 
	}

	if(districtIndex.length>0){
		if (!patten.test(districtIndex)) {
			alert('商圈排行只能是数字！');	
			return false; 
		}	 
	}
	if(blockIndex.length>0){
		if (!patten.test(blockIndex)) {
			alert('街区排行只能是数字！');	
			return false; 
		}	 
	}
	if(floors.length>0){
		if (!patten.test(floors)) {
			alert('楼层只能是数字！');	
			return false; 
		}	 
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

function showPicture(){
	$("#pictureModal").modal("show");	
}
</script>
</html>