<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	
</script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/uploadify.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/common.css'/>">
<%-- <link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/datePicker/skin/WdatePicker.css'/>">	 --%>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/bootstrap.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery.uploadify.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/editor/kindeditor.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/input.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.validate.js'/>"></script>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/datePicker/WdatePicker.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/datePicker/calendar.js'/>"></script>
	<script type="text/javascript"
	src="<c:url value='/resources/datePicker/config.js'/>"></script> --%>
<script type="text/javascript">

$().ready(function() {
					var $inputForm = $("#inputForm");
					var $type = $("#isUrl");	
					var oTypeval = $("#isUrl option:selected").val();
					var $content = $("#content");
					var $path=$("#img_path");
					var $browserButton = $("#browserButton");
					if (oTypeval == "0") {
						$content.show();
						$path.hide();
						$browserButton.prop("disabled", true);
					} else {
						$content.hide();
						$path.show();
						$browserButton.prop("disabled", false);
						$browserButton.browser();
					}
					$type.change(function() {
						
						if ($(this).val() == "0") {
							$content.show();
							$path.hide();
							
							$browserButton.prop("disabled", true);
						} else {
							$content.hide();
							$path.show();
							
							$browserButton.prop("disabled", false);
							$browserButton.browser();
						}
					});
					
					var articleCategory = $('#categorygId');
					var $category = $("#categoryId");
                    $category.change(function() {
                    	articleCategory.html("");
                    	$.ajax({
                    	    type: 'post',
                    	    url: basePath+"/common/bas/categroy/catelist",
                    	    data:{code:$("#categoryId").val()},
                    	    dataType: 'json',
                    	    success: function(data){
                    	    	var result =data.datas[0].cates;
                				var htm="";
                				if (result != null) {
                					for (i = 0; i < result.length; i++) {
                						htm +=  '<option value="'+result[i].code+'" ';
                						/* if(result[i].code.eq(article.articleCategory)){
                							htm +='selected="selected" ';
                						}  */
                						htm +='>'+result[i].name+'</option>';
                					}
                				if(""!=htm){
                					articleCategory.append(htm);
                					}
                				}
                    	    },
                    	    error: function(e){
                    	    	
                    	    }
                    	})
					});
                    if(!$('#categorygId').val()){
                    	$category.change();
                    }
					
					
					$("#inputForm").validate({
						rules:{
							title:"required",
							author:"required",
							seoDescription:"required",
							effective_date:"required",
							sort:{
									digits:true
								}
						}
					})
			});
</script>
<title>文章管理</title>
</head>
<body>
<div class="content scrollWH" scrollH='100'>

<c:choose>
  <c:when test="${article.id!=null}">
  <form id="inputForm" class="form-horizontal" action="update" enctype="multipart/form-data"
		method="post">
		<input type="hidden" value="${article.id}" name="id"/>
   </c:when>
   <c:otherwise>
   <form id="inputForm" class="form-horizontal" action="save" enctype="multipart/form-data" method="post">
   </c:otherwise>
</c:choose>
   
        <div class="form-group">
            <label for="firstname" class="col-sm-2 control-label">文章大类</label>
			<div class="col-sm-4">
			    <select id="categoryId"  name="categoryId"  class="form-control">
               		 <c:forEach items="${categorys }" var="category">
               		    <c:choose>
						<c:when test="${(not empty article.categoryId) && (article.categoryId eq category.code)}">
									<option value="${category.code}" selected="selected">${category.name}</option>
						 </c:when>
						 <c:otherwise>
			                    	<option value="${category.code}" >${category.name}</option>
						 </c:otherwise>
						 </c:choose>
               		  </c:forEach>
                </select>
			</div>     
			
            <label for="firstname" class="col-sm-2 control-label">文章类型</label>
			<div class="col-sm-4">
			    <select id="categorygId"  name="categorygId"  class="form-control">
               		  <c:forEach items="${articleCategorys }" var="articleCategory">
               		    <c:choose>
						<c:when test="${(not empty article.categorygId) && (article.categorygId eq articleCategory.code)}">
									<option value="${articleCategory.code}" selected="selected">${articleCategory.name}</option>
						 </c:when>
						 <c:otherwise>
			                    	<option value="${articleCategory.code}" >${articleCategory.name}</option>
						 </c:otherwise>
						 </c:choose>
               		  </c:forEach> 
                </select>
			</div>         
                     
                     
        </div>
        
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">标题 </label>
			<div class="col-sm-4">
			<c:choose>
			  <c:when test="${empty article.title} ">
			    <input type="text" name="title"  maxlength="200"  class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="title"  maxlength="200" value="${article.title}"  class="form-control"/>
			  </c:otherwise>
			</c:choose>
			</div>
			
			<label for="firstname" class="col-sm-2 control-label">作者</label>
			<div class="col-sm-4">
			<c:choose>
			  <c:when test="${empty article.author} ">
			    <input type="text" name="author"  maxlength="200"  class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="author"  maxlength="200" value="${article.author}"  class="form-control"/>
			  </c:otherwise>
			</c:choose>
			</div>
		</div>
		
		
		<div class="form-group">
		    <label for="firstname" class="col-sm-2 control-label">预览图 </label>
			<div class="col-sm-4">
			   <c:choose>
			       <c:when test="${empty article.icon} ">
			       <input type="file" id="img_url" name="img_url"    Class="form-control" /> 
			       </c:when>
			     <c:otherwise>
			       <input type="file" id="img_url" name="img_url"    Class="form-control" /> 
			      </c:otherwise>
			   </c:choose>
			</div>
			
			<label for="firstname" class="col-sm-2 control-label">是否发布</label>
			<div class="col-sm-4">
			<c:choose>
			   <c:when test="${empty article.isPublication}">
				<select id="isPublication" name="isPublication" class="form-control"  >
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
				</c:when>
				<c:otherwise>
				<select id="isPublication" name="isPublication"  class="form-control">
				    <c:choose> 
				    <c:when test="${article.isPublication eq false}">
					<option value="0" selected="selected">否</option>
					<option value="1">是</option>
					</c:when>
					<c:otherwise>
					<option value="1" selected="selected">是</option>
					<option value="0" >否</option>
					</c:otherwise>
					</c:choose>
				</select>				
				</c:otherwise>
			</c:choose>
			</div>
		</div>	
		
		<div class="form-group"> 
		   <label for="firstname" class="col-sm-2 control-label">生效时间</label>
			<div class="col-sm-4">
			<c:choose>
			  <c:when test="${empty article.effectiveDate} ">
			    <input id="effective_date"  name="effective_date" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="true"></input>	   
			  </c:when>
			  <c:otherwise>
			     <input id="effective_date" name="effective_date" type="text" class="form-control" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${effective_date}"  readonly="true"></input>
			  </c:otherwise>
			</c:choose>
			</div>
    		
    		<label for="firstname" class="col-sm-2 control-label">是否使用外部链接</label>
			<div class="col-sm-4">
			  <c:choose>
			     <c:when test="${empty article.isUrl}">
				  <select id="isUrl" name="isUrl"   class="form-control">
					<option value="0">否</option>
					<option value="1">是</option>
				  </select>
				</c:when>
				<c:otherwise>
				<select id="isUrl" name="isUrl"  class="form-control">
				    <c:choose> 
				    <c:when test="${article.isUrl eq false}">
					<option value="0" selected="selected">否</option>
					<option value="1">是</option>
					</c:when>
					<c:otherwise>
					<option value="0">否</option>
					<option value="1" selected="selected">是</option>
					</c:otherwise>
					</c:choose>
				</select>
				</c:otherwise>
			</c:choose>
			</div>
    	</div>
    	
    	<div class="form-group"> 
    	    <label for="firstname" class="col-sm-2 control-label">自定义时间</label>
			<div class="col-sm-4">
			<c:choose>
			  <c:when test="${empty article.activeDate} ">
			    <input id="a_date"  name="a_date" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="true"></input>	   
			  </c:when>
			  <c:otherwise>
			     <input id="a_date" name="a_date" type="text" class="form-control" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${a_date}"  readonly="true"></input>
			  </c:otherwise>
			</c:choose>
			</div>
			<label for="firstname" class="col-sm-2 control-label">排序</label>
			<div class="col-sm-4">
			<c:choose>
			  <c:when test="${empty article.sort} ">
			    <input type="text"  id="sort" name="sort"  maxlength="200"  class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" id="sort" name="sort"  maxlength="200" value="${article.sort}"  class="form-control"/>
			  </c:otherwise>
			</c:choose>
			</div>
    	</div>
    	
    	<div class="form-group"> 
    	    <label for="firstname" class="col-sm-2 control-label">显示类型</label>
			<div class="col-sm-2">
				<select class="form-control attr1" name="attr1"  maxlength="200">
					<option class='TITCON' value="YES_TITCON">标题-内容</option>
					<option class='CONCONT' value="YES_CONCONT">内容</option>
					<option class='CONIMG' value="YES_CONIMG">内容内的图</option>
					<option class='IMG1BTN' value="YES_IMG1BTN">图和一个按钮</option>
					<option class='IMG2BTN' value="YES_IMG2BTN">图和两个按钮</option>
					<option class='IFM' value="YES_IFM">框架内内容</option>
					<option class='IFM1BTN' value="YES_IFM1BTN">框架内一个按钮</option>
					<option class='IFM2BTN' value="YES_IFM2BTN">框架内两个按钮</option>
				</select>
			</div>
			<label for="firstname" class="col-sm-2 control-label">是否有顶部工具条</label>
			<div class="col-sm-1">
				<select class="form-control isTools" name="isTools"  maxlength="200">
					<option class='YES' value="YES">有</option>
					<option class='NO' value="NO">无</option>
				</select>
			</div>
			
			<label for="firstname" class="col-sm-2 control-label">跳转类型</label>
			<div class="col-sm-3">
				<select class="form-control returnType" name="returnType"  maxlength="200">
					<c:forEach items="${returnType}" var="returnType">
				 		<c:choose>
				  			<c:when test="${(not empty article.returnType) && (article.returnType eq returnType.code)}">
								<option value="${returnType.code}" selected="selected">${returnType.name}</option>
							</c:when>
				  			<c:otherwise>
				     			<option value="${returnType.code}" >${returnType.name}</option>
	 			  			</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
    	</div>
    	
    	<div class="form-group"> 
    	    <label for="firstname" class="col-sm-2 control-label">备用图</label>
			<div class="col-sm-4">
			   <c:choose>
			       <c:when test="${empty article.img} ">
			       <input type="file" id="img_logo" name="img_logo"    Class="form-control" /> 
			       </c:when>
			     <c:otherwise>
			       <input type="file" id="img_logo" name="img_logo"    Class="form-control" /> 
			      </c:otherwise>
			   </c:choose>
			</div>
			
		 <div class="form-group">
		    <label for="firstname" class="col-sm-2 control-label">是否置顶</label>
			<div class="col-sm-4">
			<c:choose>
			   <c:when test="${empty article.isTop}">
				<select id="isTop" name="isTop" class="form-control"  >
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
				</c:when>
				<c:otherwise>
				<select id="isTop" name="isTop"  class="form-control">
				    <c:choose> 
				    <c:when test="${article.isTop eq false}">
					<option value="0" selected="selected">否</option>
					<option value="1">是</option>
					</c:when>
					<c:otherwise>
					<option value="1" selected="selected">是</option>
					<option value="0" >否</option>
					</c:otherwise>
					</c:choose>
				</select>				
				</c:otherwise>
			</c:choose>
			</div>
		</div>
			
    	</div>
    	
    	<div class="form-group button01" style="display: none;"> 
    	    <label for="firstname" class="col-sm-2 control-label">按钮一名称</label>
			<div class="col-sm-2">
				<input type="text"  id="btnName01" name="btnName01"  maxlength="200"  class="form-control" />
			</div>
			<label for="firstname" class="col-sm-1 control-label">按钮一URL</label>
			<div class="col-sm-7">
			    <input type="text"  id="btnUrl01" name="btnUrl01"  maxlength="200"  class="form-control" />
			</div>
			 <input class='btnAttr01' type="hidden" name="attr2" value=''/>
    	</div>
    	<div class="form-group button02" style="display: none;"> 
    	    <label for="firstname" class="col-sm-2 control-label">按钮二名称</label>
			<div class="col-sm-2">
				<input type="text"  id="btnName02" name="btnName02"  maxlength="200"  class="form-control" />
			</div>
			<label for="firstname" class="col-sm-1 control-label">按钮二URL</label>
			<div class="col-sm-7">
			    <input type="text"  id="btnUrl02" name="btnUrl01=2"  maxlength="200"  class="form-control" />
			</div>
			<input class='btnAttr02' type="hidden" name="attr3" value=''/>
    	</div>
    	<script type="text/javascript">
    		var attr1 = '${article.attr1}';
    		if(attr1){
    			var attrs = attr1.split('_');
    			$('.'+attrs[0]).attr('selected','selected');
    			$('.'+attrs[1]).attr('selected','selected');
    			$('.'+attrs[1]).val(attr1);
    			btnChange(attr1);
    		}
    		$('.isTools').change(function(){
    			var ss= $(this).val();
    			$('.attr1').children('option').each(function(){
    				var clazz = $(this).attr('class');
    				$(this).val(ss+"_"+clazz);
    			});
    		});
    		$('.attr1').change(function(){
    			var ss= $(this).val();
    			btnChange(ss);
    		});
    		function btnChange(str){
    			var btn1 = str.indexOf('1BTN');
    			var btn2 = str.indexOf('2BTN');
    			if(btn2>=0){
    				$('.button01').show();
    				$('.button02').show();
    			}else{
    				$('.button02').hide();
    				if(btn1>=0){
        				$('.button01').show();
        			}else{
        				$('.button01').hide();
        			}
    			}
    		}
    		var attr2 = '${article.attr2}';
    		if(attr2){
    			var attrs = attr2.split(';');
    			$('#btnName01').val(attrs[0]);
    			$('#btnUrl01').val(attrs[1]);
    			if((!attrs[0]||''==attrs[0])&&(!attrs[1]||''==attrs[1])){
    				$('.btnAttr01').val("");
    			}else{
    				$('.btnAttr01').val(attrs[0]+";"+attrs[1]);
    				
    			}
    		}
    		var attr3 = '${article.attr3}';
    		if(attr3){
    			var attrs = attr3.split(';');
    			$('#btnName02').val(attrs[0]);
    			$('#btnUrl02').val(attrs[1]);
    			if((!attrs[0]||''==attrs[0])&&(!attrs[1]||''==attrs[1])){
    				$('.btnAttr02').val("");
    			}else{
    				$('.btnAttr02').val(attrs[0]+";"+attrs[1]);
    				
    			}
    		}
    		$('#btnName01').change(function(){
    			btnCon('01');
    		});
    		$('#btnUrl01').change(function(){
    			btnCon('01');
    		});
    		$('#btnName02').change(function(){
    			btnCon('02');
    		});
    		$('#btnUrl02').change(function(){
    			btnCon('02');
    		});
    		function btnCon(no){
    			var n = $('#btnName'+no).val();
    			var u = $('#btnUrl'+no).val();
    			$('.btnAttr'+no).val(n+";"+u);
    		}
    	</script>
    	
    	<div class="form-group">
    	     <label for="firstname" class="col-sm-2 control-label">备用字段1</label>
			<div class="col-sm-4">
			<c:choose>
			  <c:when test="${empty article.attr5} ">
			    <input type="text" name="attr5"  maxlength="200"   class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="attr5"  maxlength="200"  value="${article.attr5}"  class="form-control"/>
			  </c:otherwise>
			</c:choose>
			</div>
			
			<label for="firstname" class="col-sm-2 control-label">备用字段2</label>
			<div class="col-sm-4">
			<c:choose>
			  <c:when test="${empty article.attr6} ">
			    <input type="text" name="attr6"  maxlength="200"   class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="attr6"  maxlength="200"  value="${article.attr6}"  class="form-control"/>
			  </c:otherwise>
			</c:choose>
			</div>
    	</div>
    	
    	<div class="form-group">
    	     <label for="firstname" class="col-sm-2 control-label">文章概述</label>
			<div class="col-sm-10">
			<c:choose>
			  <c:when test="${empty article.attr10} ">
			    <input type="text" name="attr10"  maxlength="200"  style="height:80px" class="form-control" />
			  </c:when>
			  <c:otherwise>
			     <input type="text" name="attr10"  maxlength="200" style="height:80px" value="${article.attr10}"  class="form-control"/>
			  </c:otherwise>
			</c:choose>
			</div>
    	</div>
    	
    	<div class="form-group" id="img_path">
			<label for="" class="col-sm-2 control-label">外部链接URl</label>
		    <div class="col-sm-10">
		    <c:choose>
			<c:when test="${empty article.outerUrl}">
		      <input type="text" name="outerUrl"  maxlength="200"   class="form-control" />
			</c:when>
			<c:otherwise>
			   <input type="text" name="outerUrl"  maxlength="200"  value="${article.outerUrl}"  class="form-control"/>
			</c:otherwise>
			</c:choose>
			</div>
		</div>
		
		
		<div class="form-group"  id="content">
			<label for="introduce" class="col-sm-2 control-label"> 内容</label>
		    <div class="col-sm-10">
		    <c:choose>
			<c:when test="${empty article.content}">
		    <textarea id="editor" name="content" class="editor" class="form-control"
							style="width: 100%;"></textarea>
			</c:when>
			<c:otherwise>
			<textarea id="editor" name="content" class="editor"  class="form-control"
							style="width: 100%;">${article.content}</textarea>
			</c:otherwise>
			</c:choose>
			</div>
		</div>
						
		<table class="table">
			<tr>
				<th>&nbsp;</th>
				<td><input type="submit" class="btn btn-default" value="确认" />
					<input type="button" class="btn btn-default" value="返回"
					onclick="location.href='list'" />
					<c:if test="${not empty article.categorygId}">
					<a  menuname="重置" href='<c:url value="/main/newarticle/edit?id=${article.id}"/>'  class="btn btn-default" >重置</a>
					</c:if>
					</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>