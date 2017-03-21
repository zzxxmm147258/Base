<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<%
	String path = request.getContextPath();
	//获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/uploadify.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/common.css'/>">
<script type="text/javascript">
	var basePath = "<%=basePath%>";
</script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/bootstrap.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/common.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery.validate.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/choseModal.js'/>"></script>

<script type="text/javascript">
	$().ready(function (){
		var $specificationTable = $("#specificationTable");
		var $type = $("#type");
		var $addSpecificationValueButton = $("#addSpecificationValueButton");
		var $deleteSpecificationValue = $("a.deleteSpecificationValue");
		
		var specificationValueIndex = 1;
		// 修改规格类型
		$type.change(function() {
			if ($(this).val() == "0") {
				$("input.specificationValuesImage").val("").prop("disabled", true);
				$("input.browserButton").prop("disabled", true);
			} else {
				$("input.specificationValuesImage").prop("disabled", false);
				$("input.browserButton").prop("disabled", false);
			}
		});
		
		$("input.browserButton").browser();
		
		// 增加规格值
		$addSpecificationValueButton.click(function() {
					var trHtml = 
					'<tr class="specificationValueTr">'+
					'<td><input type="text"   name="shopbasinfo[' + specificationValueIndex + '].showPoint" class="form-control shopbasinfoshowPoint" maxlength="200" style="width: 50px;" oninput="checkInput(this)" \/><font color="red" style="display:none">必填</font><\/td>'+
					 '<td><select  name="shopbasinfo['+specificationValueIndex+'].showType"  class="form-control" >'+
	                       '<c:forEach items="${showType}" var="showType" >'+
			                 '<option value="${showType.code}" >${showType.cname }</option>'+
		                    '</c:forEach>'+
		                   '</select>'+
	                '</td>'+
					'<td><input type="text" class="form-control" name="shopbasinfo[' + specificationValueIndex + '].showTitle"  maxlength="200" o \/><font color="red" style="display:none">必填</font><\/td>'+
					'<td><input type="file" class="form-control"name="shopbasinfo[' + specificationValueIndex + '].imgs"  maxlength="200" \/><\/td>'+
					'<td><input type="text" class="form-control shopbasinfoshowExplain" name="shopbasinfo[' + specificationValueIndex + '].showExplain"  maxlength="250"  data-toggle="modal"  data-target="#editModal" \/><\/td>'+
					'<td><select  name="shopbasinfo[' + specificationValueIndex + '].aviliable"  class="form-control">'+
					    '<option value="1" >是</option>'+
			            '<option value="0" >否</option>'+
			            '</select>'+
	                 '</td>'+
					'<td><a  href="javascript:;" id="deleteSpecificationValue" class="btn btn-primary">[删除]<\/a><\/td>'+
					'<\/tr>';
			$specificationTable.append(trHtml).find("input.browserButton:last").browser();
			$deleteSpecificationValue = $("a.deleteSpecificationValue");
			specificationValueIndex ++;
		});
		
		// 删除规格值
		/* $deleteSpecificationValue.on("click", function() {
			
		});
		 */
		$(document).on('click','#deleteSpecificationValue',function(){
			var $this = $(this);
			if ($specificationTable.find("tr.specificationValueTr").size() <= 1) {
				$.message("warn", "最后一行不能删除");
			} else {
				$this.closest("tr").remove();
			}
		})
		
		$("#inputForm").validate({
			rules:{
				shopId:"required",
				shopname:"required",
				floorName:"required",
				longitude:{
					number:true
				},
				latitude:{
					number:true
				},
				perConsume:{
					number:true
				},
				nice:{
					digits:true
				},
				sort:{
					digits:true
				},
				districtIndex:{
					digits:true
				},
				blockIndex:{
					digits:true
				},
				buildingIndex:{
					digits:true
				},
				floorIndex:{
					digits:true
				},
				showPoint:{
					digits:true
				}
			}
		});
		 
		$.validator.addClassRules({
			shopbasinfoshowPoint: {
				digits:true
			}
		});
		
	});
</script>
<title>商户添加</title>
</head>
<body>

<div class="content scrollWH" scrollH='100'>
  

          <c:choose>
		     <c:when test="${not empty shopBas.shopId}">
		        <form id="inputForm"  class="form-horizontal" action="<c:url value='/admin/shop/update'/>"  enctype="multipart/form-data" method="post"> 
				<%-- <input type="hidden" value="${shopBas.shopId}" name="shopId">    --%>
		     </c:when>
			 <c:otherwise>
				<form id="inputForm"class="form-horizontal" action="<c:url value='/admin/shop/save'/>"  enctype="multipart/form-data" method="post">
			 </c:otherwise>		
		   </c:choose>
		
		 <div class="form-group">
            <label for="firstname" class="col-sm-1 control-label">商户编码</label>
			<div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.shopId}">
						<input type="text" name="shopId" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="shopId" class="form-control" value="${shopBas.shopId}"/>
					</c:otherwise>
				</c:choose>
			</div>
			
			<label for="firstname" class="col-sm-1 control-label">商户中文名称</label>
			<div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.shopname}">
						<input type="text" name="shopname" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="shopname" class="form-control" value="${shopBas.shopname}"/>
					</c:otherwise>
				</c:choose>
			</div>
			
			<label for="firstname" class="col-sm-1 control-label">商户英文名称</label>
			<div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.enshopname}">
						<input type="text" name="enshopname" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="enshopname" class="form-control" value="${shopBas.enshopname}"/>
					</c:otherwise>
				</c:choose>
			</div>
			
			<label for="firstname" class="col-sm-1 control-label">楼层</label>
			<div class="col-sm-2">
			     <input id="floorName" name="floorName" class="form-control" value="${floorName }"   readonly="true"/>
				 <input  type="hidden" id="floorId" name="floorId" class="form-control"   readonly="true"/>
				 <div class="btn btn-primary"  data-toggle="modal" data-target="#floorBasModal"><a href="javascript:;">选择</a></div>
				    
			    <%-- <select id="floorId"  name="floorId"  class="form-control">
               		 <c:forEach items="${floorBas }" var="floorBas">
               		    <c:choose>
						<c:when test="${(not empty shopBas.floorId) && (shopBas.floorId eq floorBas.floorId)}">
									<option value="${floorBas.floorId}" selected="selected">${floorBas.floorName}</option>
						 </c:when>
						 <c:otherwise>
			                    	<option value="${floorBas.floorId}">${floorBas.floorName}</option>
						 </c:otherwise>
						 </c:choose>
               		  </c:forEach>
                </select> --%>
			</div>
			
		 </div>
		
		 <div class="form-group">
		      <label for="firstname" class="col-sm-1 control-label">商户Logo</label>
		      <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.shopLogo}">
						<input type="file" id="img" name="img"    Class="form-control" /> 
					</c:when>
					<c:otherwise>
						<%-- <input type="text" name="shopLogo" class="form-control" value="${shopBas.shopLogo}"/> --%>
						<input type="file" id="img" name="img"    Class="form-control" />
					</c:otherwise>
				</c:choose>
			</div>
			
			<label for="firstname" class="col-sm-1 control-label">图片</label>
		      <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.images}">
						<input type="file" id="img1" name="img1"  multiple="multiple"    Class="form-control" /> 
					</c:when>
					<c:otherwise>
						<%-- <input type="text" name="images" class="form-control" value="${shopBas.images}"/> --%>
						<input type="file" id="img1" name="img1"   multiple="multiple"    Class="form-control" />
					</c:otherwise>
				</c:choose>
			</div>
			
			<label for="firstname" class="col-sm-1 control-label">特色图片</label>
		      <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.chaIcon}">
						<input type="file" id="img2" name="img2"   multiple="multiple"   Class="form-control" /> 
					</c:when>
					<c:otherwise>
						<%-- <input type="text" name="chaIcon" class="form-control" value="${shopBas.chaIcon}"/> --%>
						<input type="file" id="img2" name="img2"   multiple="multiple"   Class="form-control" />
					</c:otherwise>
				</c:choose>
			</div>
			
			<label for="firstname" class="col-sm-1 control-label">商户类型</label>
			<div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.shopType}">
						<input type="text" name="shopType" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="shopType" class="form-control" value="${shopBas.shopType}"/>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		 <div class="form-group">
			<label for="firstname" class="col-sm-1 control-label">人均消费</label>
			<div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.perConsume}">
						<input type="text" name="perConsume" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="perConsume" class="form-control" value="${shopBas.perConsume}"/>
					</c:otherwise>
				</c:choose>
			</div>
			<label for="firstname" class="col-sm-1 control-label">是否启用</label>
			    <div class="col-sm-2">
			     <select id="availabe"  name="availabe"  class="form-control">
               		    <c:choose>
						<c:when test="${(not empty shopBas.availabe) && (shopBas.availabe eq false)}">
									<option value="0" selected="selected">否</option>
						 </c:when>
						 <c:otherwise>
			                    	<option value="1">是</option>
			                    	<option value="0">否</option>
						 </c:otherwise>
						 </c:choose>
                </select>
			   </div>
			
			<label for="firstname" class="col-sm-1 control-label">经度</label>
			<div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.longitude}">
						<input type="text" name="longitude" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="longitude" class="form-control" value="${shopBas.longitude}"/>
					</c:otherwise>
				</c:choose>
			</div>
			<label for="firstname" class="col-sm-1 control-label">纬度</label>
			<div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.latitude}">
						<input type="text" name="latitude" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="latitude" class="form-control" value="${shopBas.latitude}"/>
					</c:otherwise>
				</c:choose>
			</div>
		      
		 </div>
		 
		  <div class="form-group">
		       <label for="firstname" class="col-sm-1 control-label">电话</label>
			    <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.phone}">
						<input type="text" name="phone" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="phone" class="form-control" value="${shopBas.phone}"/>
					</c:otherwise>
				</c:choose>
			   </div>
			   
			   <label for="firstname" class="col-sm-1 control-label">排序</label>
			    <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.sort}">
						<input type="text" name="sort" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="sort" class="form-control" value="${shopBas.sort}"/>
					</c:otherwise>
				</c:choose>
			   </div>
			   
			   <label for="firstname" class="col-sm-1 control-label">人气</label>
			    <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.nice}">
						<input type="text" name="nice" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="nice" class="form-control" value="${shopBas.nice}"/>
					</c:otherwise>
				</c:choose>
			   </div>
			   
			   <label for="firstname" class="col-sm-1 control-label">评论数</label>
			    <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.replyNo}">
						<input type="text" name="replyNo" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="replyNo" class="form-control" value="${shopBas.replyNo}"/>
					</c:otherwise>
				</c:choose>
			   </div>
		  </div>
		  
		   <div class="form-group">
		       <label for="firstname" class="col-sm-1 control-label">商圈排行</label>
			    <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.districtIndex}">
						<input type="text" name="districtIndex" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="districtIndex" class="form-control" value="${shopBas.districtIndex}"/>
					</c:otherwise>
				</c:choose>
			   </div>
			   
			   <label for="firstname" class="col-sm-1 control-label">街区排行</label>
			    <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.blockIndex}">
						<input type="text" name="blockIndex" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="blockIndex" class="form-control" value="${shopBas.blockIndex}"/>
					</c:otherwise>
				</c:choose>
			   </div>
			   
			   <label for="firstname" class="col-sm-1 control-label">单元排行</label>
			    <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.buildingIndex}">
						<input type="text" name="buildingIndex" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="buildingIndex" class="form-control" value="${shopBas.buildingIndex}"/>
					</c:otherwise>
				</c:choose>
			   </div>
			   
		       <label for="firstname" class="col-sm-1 control-label">楼层排行</label>
			    <div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.floorIndex}">
						<input type="text" name="floorIndex" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="floorIndex" class="form-control" value="${shopBas.floorIndex}"/>
					</c:otherwise>
				</c:choose>
			   </div>
		   </div>
		
	     <div class="form-group">
	        <label for="firstname" class="col-sm-1 control-label">商户地址</label>
			<div class="col-sm-2">
				<c:choose>
			        <c:when test="${empty shopBas.shopAddress}">
						<input type="text" name="shopAddress" class="form-control" />
					</c:when>
					<c:otherwise>
						<input type="text" name="shopAddress" class="form-control" value="${shopBas.shopAddress}"/>
					</c:otherwise>
				</c:choose>
			</div>
	     </div>
		
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<input type="button" id="addSpecificationValueButton" class="btn btn-default"
					value="增加店铺信息" />
			</div>
		</div>
		<table class="table" id="specificationTable">
			<thead>
				<tr>
					<th>展示序号</th>
					<th>展示类型</th>
					<th>展示标题</th>
					<th>展示图片</th>
					<th>展示说明</th>
					<th>是否启用</th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
			
			 <c:when test="${empty shopBas.shopId}">
			 <tr class="specificationValueTr">
				<td>
					<input type="text" name="shopbasinfo[0].showPoint" class="form-control shopbasinfoshowPoint" maxlength="200" style="width: 50px;" oninput="checkInput(this)" />
					<font color="red" style="display:none">必填</font>
				</td>
				<td>
					<select  name="shopbasinfo[0].showType" class="form-control">
				         <c:forEach items="${showType}" var="showType" >
						 <option value="${showType.code}" >${showType.cname }</option>
					</c:forEach>
				</td>
				<td>
					<input type="text" name="shopbasinfo[0].showTitle" class="form-control" maxlength="200"  />
					<font color="red" style="display:none">必填</font>
				</td>
				<td>
					<span class="fieldSet">				
						<input type="file" name="shopbasinfo[0].imgs" class="form-control"  maxlength="200"  />				
						<!-- <input type="button" class="button browserButton" value="选择图片"  /> -->
					</span>
				</td>
				<td>
					<input type="text" name="shopbasinfo[0].showExplain" class="form-control shopbasinfoshowExplain"   data-toggle="modal"  data-target="#editModal" maxlength="250"   />
					<font color="red" style="display:none">必填</font>
				</td>
				<td>
				     <select  name="shopbasinfo[0].aviliable"  class="form-control">
					    <option value="1" >是</option>
			            <option value="0" >否</option>
			            </select>
					
				</td>
				<td>
					<a href="javascript:;" id="deleteSpecificationValue"  class="btn btn-primary" >删除</a>
				</td>
			</tr>
			 </c:when>
			 <c:otherwise>
			<c:forEach  items="${shopBas.shopbasinfo}"  var="s" varStatus="abc">
				<tr class="specificationValueTr">
				<td>
				    <input type="text" name="shopbasinfo[${abc.index}].showPoint" class="form-control" style="width: 50px;" maxlength="200" oninput="checkInput(this)" value="${s.showPoint}"/>
				    <font color="red" style="display:none">必填</font>				
				</td>
				<td>
				    <select name="shopbasinfo[${abc.index}].showType"  class="form-control">
               			        <c:forEach items="${showType }" var="showType">
               				       <c:choose>
								   <c:when test="${(not empty s.showType) && (s.showType eq showType.code)}">
									<option value="${showType.code}" selected="selected">${showType.cname}</option>
								   </c:when>
								  <c:otherwise>
			                    	<option value="${showType.code}">${showType.cname}</option>
								  </c:otherwise>
							       </c:choose>
               			       </c:forEach>
               		    </select>				
				</td>
				<td>
				    <input type="text" name="shopbasinfo[${abc.index}].showTitle" class="form-control" maxlength="200"   value="${s.showTitle}"/>
				    <font color="red" style="display:none">必填</font>				
				</td>
				<td>
<%-- 					<input type="text" name="shopbasinfo[${abc.index}].showImage" class="text shopbasinfoshowImage" maxlength="200" disabled="disabled" value="${s.showImage}"/>					
 --%>					<input type="file" name="shopbasinfo[${abc.index}].imgs" class="form-control"  maxlength="200"  />
				</td>
				<td>			
					<input type="text" name="shopbasinfo[${abc.index}].showExplain" class="form-control shopbasinfoshowExplain" maxlength="250" style="width: 200px;" data-toggle="modal"  data-target="#editModal" value="${s.showExplain}"/>				
				</td>
				<td>			
			        <select   name="shopbasinfo[${abc.index}].aviliable"  class="form-control">
			            <c:choose> 
							 <c:when test="${s.aviliable==false}">
								<option value="0" selected="selected">否</option>
								<option value="1">是</option>
							 </c:when>
							<c:otherwise>
								<option value="0">否</option>
								<option value="1" selected="selected">是</option>
							</c:otherwise>
						</c:choose>
			         </select>
				</td>
				<td>
				     <input type="hidden" value="${s.id}"   name="shopbasinfo[${abc.index}].id"  class="form-control">
				</td>
				<td>
					<a href="javascript:;"  id="deleteSpecificationValue"  class="btn btn-primary">[删除]</a>
				</td>
			</tr>
			</c:forEach>
			</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		<table class="table">
			<tr>
				<th>&nbsp;</th>
				<td><input name="submit1" type="button" class="btn btn-default" value="确认" />
					<input type="button" class="btn btn-default" value="返回"
					onclick="location.href='list'" /></td>
			</tr>
		</table>
	</form>
	
	</div>
	
	<!-- 模态框（Modal） -->
		<div class="modal fade" id="editModal" tabindex="-10" role="dialog"  aria-labelledby="editModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content" style="width:400px; height:280px;">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="myModalLabel">
		               	编辑
		            </h4>
		         </div>
		         <div class="modal-body" style="width:400px; height:150px; ">						  
						  <div class="form-group">
						    <label for="name">展示说明</label>
						    <textarea   type="text"  class="form-control" maxlength="250" style="width:365px; height:100px;" id="tetxVal"> 
						    </textarea>
						  </div>
						  
		         </div>
		         <div class="modal-footer">
		            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
		            </button>
		            <button type="button" class="btn btn-primary" id="editFloorg" data-dismiss="modal">
						确认
		            </button>
		         </div>
		      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
		</div>
		
	<!-- shop模态框（Modal） -->
		<div class="modal fade" name="floorBasModal" id="floorBasModal" tabindex="-1" role="dialog"  aria-labelledby="floorBasModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="floorBasModalLabel">
		               	选择楼层
		            </h4>
		         </div>
		         <div class="modal-body" style="width:590px; height:300px;">
		         	<label class="col-sm-2 control-label" >楼层名称</label>
		         	<input type="text" name="floorName" class="form-control" maxlength="100" style="width:150px;float: left" />
		         	<input type="button"   class="form-control" id="search" name="search" style="width:50px;" value="查询"><br>
		         	<div style="height:240px;overflow:scroll">
		            <table class="table table-bordered">
		            	<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
								<th>楼层名称</th>
								<th>楼层号</th>
							</tr>
						</thead>
						<tbody name="floorBas">
							<c:forEach items="${floorBas}" var="floorBas">
								<tr>
									<td><input type="radio" name="radio" r_floorName="${floorBas.floorName }" r_floorId="${floorBas.floorId }">${floorBas.floorName }</td>
									<td>${floorBas.floorNo }</td>
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

/*  $('.shopbasinfoshowExplain ').click(function(){
	$("#editModal").modal("show");
})  */

function checkInput(obj){
	var value = $(obj).val();
	if(value==null || value==""){
		$(obj).css("border","1px solid red");
		$(obj).next().show();
		return false;
	}else{
		$(obj).removeAttr("style");
		$(obj).next().hide();
		return true;
	}
}

$('input[name=submit1]').click(function(){
  		$("#inputForm").submit();
}) 
	
 var that = ''; 
 $(document).on('click','.shopbasinfoshowExplain',function(){ 
	 $("#editModal").modal("show");
	  that = this; 
	  $('#tetxVal').val($(that).val())
	  $('.btn-primary').click(function(){
			var oThis = $('#tetxVal').val();
			$("#editModal").modal("hide"); 
			$(that).val(oThis);
	})
}) 

	var inputArray = new Array("floorId","floorName"); 	//输入框的path
	var fuzzyArray = inputArray;						//模糊查询条件的字段
	//需要传入的参数：表名，list.ajax链接地址，输入框需要的字段，模糊查询条件
	choseModal("floorBas",'<c:url value="/admin/floor/list.ajax"/>',inputArray,fuzzyArray);
	function getHtml(tableName,json){        //配合choseModal.js使用
		var html = "";
		if(tableName=="floorBas"){
			for(var i=0;i<json.length;i++){    //拼radio的属性时候记得加 r_ 前缀
				html += '<tr><td><input type="radio" name="radio" r_floorName="'+json[i].floorName+'" r_floorId="'+json[i].floorId+'">'+json[i].floorName+'</td><td>'+json[i].floorNo+'</td></tr>';
			}
		}
		return html;
	} 


$('#myModal').modal({backdrop: 'static', keyboard: false});

function onlyNumber(obj){
	var value = obj.value;
	if(''!=value){
		obj.value = value.replace(/[^\d]/g,'');
	}
}

</script>
</html>