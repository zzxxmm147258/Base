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
					
					var articleCategory = $('#articleCategory');
					var $category = $("#category");
                    $category.change(function() {
                    	articleCategory.html("");
                    	$.ajax({
                    	    type: 'post',
                    	    url: basePath+"/main/articlenew/listCategory.ajax",
                    	    data:{category:$("#category").val()},
                    	    dataType: 'json',
                    	    success: function(data){
                    	    	var result =data;
                				var htm="";
                				if (result != null) {
                					for (i = 0; i < result.length; i++) {
                						htm +=  '<option value="'+result[i].code+'" ';
                						/* if(result[i].code.eq(article.articleCategory)){
                							htm +='selected="selected" ';
                						}  */
                						htm +='>'+result[i].cname+'</option>';
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
                    if(!$('#articleCategory').val()){
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
  <form id="inputForm" class="form-horizontal" action="save" enctype="multipart/form-data" method="post">
		<input type="hidden" value="${article.id}" name="id"/>
   </c:when>
   <c:otherwise>
   <form id="inputForm" class="form-horizontal" action="save" enctype="multipart/form-data" method="post">
   </c:otherwise>
</c:choose>
   
        <div class="form-group">
            <label for="firstname" class="col-sm-2 control-label">文章大类</label>
			<div class="col-sm-4">
			    <select id="category"  name="categoryId"  class="form-control">
               		 <c:forEach items="${categorys }" var="category">
               		    <c:choose>
						<c:when test="${(not empty article.categoryId) && (article.categoryId eq category.code)}">
									<option value="${category.code}" selected="selected">${category.cname}</option>
						 </c:when>
						 <c:otherwise>
			                    	<option value="${category.code}" >${category.cname}</option>
						 </c:otherwise>
						 </c:choose>
               		  </c:forEach>
                </select>
			</div>     
			
            <label for="firstname" class="col-sm-2 control-label">文章类型</label>
			<div class="col-sm-4">
			    <select id="articleCategory"  name="categorygId"  class="form-control">
               		  <c:forEach items="${articleCategorys }" var="articleCategory">
               		    <c:choose>
						<c:when test="${(not empty article.categorygId) && (article.categorygId eq articleCategory.code)}">
									<option value="${articleCategory.code}" selected="selected">${articleCategory.cname}</option>
						 </c:when>
						 <c:otherwise>
			                    	<option value="${articleCategory.code}" >${articleCategory.cname}</option>
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
			       <c:when test="${empty article.iconSmall} ">
			       <input type="file" id="iconFile" name="iconFile"    Class="form-control" /> 
			       自定义尺寸(已包含：200*200)：<input type="text" id="iconOtherSize" name="iconOtherSize"  /> 
			       </c:when>
			     <c:otherwise>
			       <input type="file" id="iconFile" name="iconFile" value="${article.iconSmall}"   Class="form-control" /> 
			      自定义尺寸(已包含：200*200)：<input type="text" id="iconOtherSize" name="iconOtherSize" value="${article.iconOtherSize }" /> 
			      </c:otherwise>
			   </c:choose>
			</div>
			    	<div class="form-group"> 
    	    <label for="firstname" class="col-sm-2 control-label">可选图片 </label>
			<div class="col-sm-4">
			   <c:choose>
			       <c:when test="${empty article.imgSmall} ">
			       <input type="file" id="imgFile" name="imgFile"    Class="form-control" /> 
			       自定义尺寸(已包含：200*200)：<input type="text" id="imgOtherSize" name="imgOtherSize"  /> 
			       </c:when>
			     <c:otherwise>
			       <input type="file" id="imgFile" name="imgFile" value="${article.imgSmall }"   Class="form-control" /> 
			     自定义尺寸(已包含：200*200)：<input type="text" id="imgOtherSize" name="imgOtherSize" value="${article.imgOtherSize }" /> 
			      </c:otherwise>
			   </c:choose>
			</div>
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
			  <c:when test="${empty article.effectiveDates} ">
			    <input id="effectiveDates"  name="effectiveDates" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="true"></input>	   
			  </c:when>
			  <c:otherwise>
			     <input id="effectiveDates" name="effectiveDates" type="text" class="form-control" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${article.effectiveDates}"  readonly="true"></input>
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
			  <c:when test="${empty article.activeDates} ">
			    <input id="activeDates"  name="activeDates" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="true"></input>	   
			  </c:when>
			  <c:otherwise>
			     <input id="activeDates" name="activeDates" type="text" class="form-control" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${article.activeDates}"  readonly="true"></input>
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
    	    <label for="firstname" class="col-sm-2 control-label">显示类型(若减少按钮需要清空相应按钮名称)</label>
			<div class="col-sm-2">
				<select class="form-control viewType" name="viewType"  maxlength="200">
				<c:forEach items="${viewTypes}" var="vt">
					<option class='${vt.code }' value="${vt.code }" >${vt.cname }</option>
				</c:forEach>
					
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
					<c:forEach items="${dictinfo}" var="dicfo">
				 		<c:choose>
				  			<c:when test="${(not empty article.returnType) && (article.returnType eq dicfo.code)}">
								<option value="${dicfo.code}" selected="selected">${dicfo.cname}</option>
							</c:when>
				  			<c:otherwise>
				     			<option value="${dicfo.code}" >${dicfo.cname}</option>
	 			  			</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
    	</div>
    	
    	
    	<div class="form-group button01" style="display: none;"> 
    	    <label for="firstname" class="col-sm-2 control-label">按钮一名称</label>
			<div class="col-sm-2">
				<input type="text"  id="btnName01" name="btnName01" value="${article.btnName01 }" maxlength="200"  class="form-control" />
			</div>
			<label for="firstname" class="col-sm-1 control-label" >按钮一URL</label>
			<div class="col-sm-2">
			    <input type="text"  id="btnUrl01" name="btnUrl01" value="${article.btnUrl01 }" maxlength="200"  class="form-control" />
			</div>
			<label for="firstname" class="col-sm-1 control-label">按钮一位置</label>
			<div class="col-sm-1">
			    <input type="text"  id="btnLoc01" name="btnLoc01" value="${article.btnLoc01 }" maxlength="200"  class="form-control" />
			</div>
			<label for="firstname" class="col-sm-1 control-label">图片</label>
			<div class="col-sm-2">
			    <input type="file"  id="btnIcon01" name="btnIcon01" value="${article.btnIcon01 }" class="form-control" />
			</div>
    	</div>
    	<div class="form-group button02" style="display: none;"> 
    	    <label for="firstname" class="col-sm-2 control-label">按钮二名称</label>
			<div class="col-sm-2">
				<input type="text"  id="btnName02" name="btnName02" value="${article.btnName02 }" maxlength="200"  class="form-control" />
			</div>
			<label for="firstname" class="col-sm-1 control-label">按钮二URL</label>
			<div class="col-sm-2">
			    <input type="text"  id="btnUrl02" name="btnUrl02" value="${article.btnUrl02 }" maxlength="200"  class="form-control" />
			</div>
			<label for="firstname" class="col-sm-1 control-label">按钮二位置</label>
			<div class="col-sm-1">
			    <input type="text"  id="btnLoc02" name="btnLoc02" value="${article.btnLoc02 }" maxlength="200"  class="form-control" />
			</div>
			<label for="firstname" class="col-sm-1 control-label">图片</label>
			<div class="col-sm-2">
			    <input type="file"  id="btnIcon02" name="btnIcon02" value="${article.btnIcon02 }" class="form-control" />
			</div>
    	</div>
    	<script type="text/javascript">
    		var viewType = '${article.viewType}';
    		if(viewType){
    			var attrs = viewType.split('_');
    			$('.'+viewType).attr('selected','selected');
    			$('.'+attrs[1]).attr('selected','selected');
    			btnChange(viewType);
    		}
    		$('.isTools').change(function(){
    			var ss= $(this).val();
    			$('.viewType').children('option').each(function(){
    				var clazz = $(this).attr('class');
    				$(this).val(ss+"_"+clazz);
    			});
    		});
    		$('.viewType').change(function(){
    			var ss= $(this).val();
    			btnChange(ss);
    		});
    		function btnChange(str){
    			var btn1 = str.indexOf('-01');
    			var btn2 = str.indexOf('-02');
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
    		
    	</script>
    	
    	
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
					</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>