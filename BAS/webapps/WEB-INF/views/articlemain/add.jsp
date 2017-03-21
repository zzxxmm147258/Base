<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%//获得项目完全路径
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>">
<script type="text/javascript">
	var basePath = "<%=basePath%>";
</script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/js/bootstrap.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery.validate.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/common.css'/>">
<script type="text/javascript"
	src="<c:url value='/resources/js/common.js'/>"></script>
<script type="text/javascript">
$().ready(function(){
	var serachButton = $("#searchButton");
	var addProductButton = $("#addProduct");
	var productTr = $('#productTr');
	var subProduct= $('#subProduct');
	var storageg=$('#storageg');
	//点击添加商品按钮触发事件
	serachButton.on('click',function(){
		productTr.html("");
		$.ajax({
			url:""+basePath+"/main/article/list.ajax",
			type : 'post',
			data: {"title":$("#ptitle").val(),"author":$("#pauthor").val(),"datefrom":$("#datefrom").val(),"dateto":$("#dateto").val(),"articleCategoryId":$("#articleCategoryId").val(),"categoryId":$("#categoryId").val()},
			dataType : 'json',
			success : function(data) {
				var result = eval(data.result);
				var line="";
				var index=0;
				if (result != null) {
					for (i = 0; i < result.length; i++) {
						line += '<tr>'+
						        '<td><input type="checkbox" name="ids" value="'+index+'"></input></td>'+
						        '<td id="id" style="display:none">'+result[i].id+'</td>'+
						        '<td id="title">'+result[i].title+'</td>'+
						        '<td id="author">'+result[i].author+'</td>'+
						        '<td id="articleCategory">'+result[i].articleCategory+'</td>'+
						        '<td id="id" style="display:none">'+result[i].id+'</td>'+ 
						        '</tr>';
						index++;
					}
					if(""!=line){
						productTr.append(line);
					}
				}
			}
		});
	});
	//点击弹出框确认事件
	var index = '${fn:length(bean.basArticle)}';
	subProduct.on('click',function(){
		var ids = document.getElementsByName('ids');
		var htm = "";
		var map = new $.hMap();
		if(ids.length>0){
			for(i=0;i<ids.length;i++){
				if(ids[i].checked){
					var line = $(ids[i]).closest("tr");
					htm +='<tr class="parameterTr">'+
					       '<td style="display:none"><input type="hide" readonly="readonly" name="basArticle['+index+'].id" value="'+line.find("#id").html()+'"></td>'+
					       '<td><input type="text" readonly="true" class="form-control" name="basArticle['+index+'].title" value="'+line.find("#title").html()+'"></td>'+
					       '<td><input type="text" readonly="true" class="form-control" name="basArticle['+index+'].author" value="'+line.find("#author").html()+'"></td>'+
					       '<td><input type="text" readonly="true" class="form-control" name="basArticle['+index+'].articleCategory" value="'+line.find("#articleCategory").html()+'"></td>'+
					       '<td><select  name="basArticle['+index+'].isTop" class="form-control" >'+
					            '<option value="1" >是</option>'+
					            '<option value="0" >否</option>'+
			               '</td>'+
					       '<td><input type="text" name="basArticle['+index+'].idx" class="form-control parametersIdx"  ></td>'+
				           '<td><a href="javascript:void(0);" id="deleteId" class="btn btn-primary">删除<\/a><\/td>'+
					       '</tr>';
					index++;
				}
			}
			if(""!=htm){
				storageg.append(htm);
				$('#myModal').modal('hide')
			}
		}
	});
	
	$("#inputForm").validate({
		rules:{
			title:"required",
			idx:"digits",
			}
	});
	
	$.validator.addClassRules({
		parametersIdx: {
			number:true,
			minlength:1
		}
	});
	
	$(document).on("click","#deleteId",function(){
		var $this = $(this);
		var $parameterTable = $("#parameterTable");
		if ($parameterTable.find("tr.parameterTr").size() <= 1) {
			$.message("warn", "只剩一行数据，不能删除");
		} else {
			$this.closest("tr").remove();
		}
	})
	
	
	var articleCategory = $('#articleCategory');
	var $category = $("#category");
    $category.change(function() {
    	articleCategory.html("");
    	$.ajax({
    	    type: 'post',
    	    url: basePath+"/main/article/listCategory.ajax",
    	    data:{category:$("#category").val()},
    	    dataType: 'json',
    	    success: function(data){
    	    	var result =data;
				var htm="";
				if (result != null) {
					htm +=  '<option value="allnews" selected="selected">全部</option>';
					for (i = 0; i < result.length; i++) {
						htm += '<option value="'+result[i].code+'">'+result[i].cname+'</option>';
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
    
    var articleCategoryId = $('#articleCategoryId');
	var $categoryId = $("#categoryId");
    $categoryId.change(function() {
    	articleCategoryId.html("");
    	$.ajax({
    	    type: 'post',
    	    url: basePath+"/main/article/listCategory.ajax",
    	    data:{category:$("#categoryId").val()},
    	    dataType: 'json',
    	    success: function(data){
    	    	var result =data;
				var htm="";
				if (result != null) {
					htm +=  '<option value="" selected="selected">所有</option>';
					for (i = 0; i < result.length; i++) {
						htm += '<option value="'+result[i].code+'">'+result[i].cname+'</option>';
					}
				if(""!=htm){
					articleCategoryId.append(htm);
					}
				}
    	    },
    	    error: function(e){
    	    	
    	    }
    	})
	});
	
}); 
</script>
<title>头条文章</title>
</head>
<body>
<div class="content scrollWH" scrollH='100'>

	<c:choose>
		     <c:when test="${not empty bean.articleCategory}">
		        <form id="inputForm" class="form-horizontal"   method="post">
				<input type="hidden" value="${bean.id}" name="id">   
		     </c:when>
			 <c:otherwise>
				<form id="inputForm" class="form-horizontal" action="save" method="post">
			 </c:otherwise>		
	</c:choose>
	
	  <div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">文章大类</label>
			<div class="col-sm-4">
			    <select id="category"  name="category"  class="form-control">
               		 <c:forEach items="${categorys }" var="category">
               		    <c:choose>
						<c:when test="${(not empty bean.category) && (bean.category eq category.code)}">
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
			    <select id="articleCategory"  name="articleCategory"  class="form-control">
               		 <option value="allnews" selected="selected">全部</option>
               		 <c:forEach items="${articleCategorys }" var="articleCategory">
               		    <c:choose>
						<c:when test="${(not empty bean.articleCategory) && (bean.articleCategory eq articleCategory.code)}">
									<option value="${articleCategory.code}" selected="selected">${articleCategory.cname}</option>
						 </c:when>
						 <c:otherwise>
			                    	<option value="${articleCategory.code}">${articleCategory.cname}</option>
						 </c:otherwise>
						 </c:choose>
               		  </c:forEach>
                </select>
			</div>
		</div> 
		
		<div class="form-group">
		    <label for="firstname" class="col-sm-2 control-label"><font color="red"></font>标题</label>
			<div class="col-sm-4">
			<c:choose>
		        <c:when test="${empty bean.title}">
					<input type="text" name="title" class="form-control" maxlength="200"/>
				</c:when>
				<c:otherwise>
					<input type="text" name="title" class="form-control" maxlength="200" value="${bean.title }"/>
				</c:otherwise>
			</c:choose>
			</div>
		
		    <label for="firstname" class="col-sm-2 control-label"><font color="red"></font>状态</label>
			<div class="col-sm-4">
			<c:choose>
			   <c:when test="${empty bean.status}">
				<select id="status" name="status" class="form-control"  >
					<option value="70">有效</option>
					<option value="10">准备</option>
				</select>
				</c:when>
				<c:otherwise>
				<select id="status" name="status"  class="form-control">
				    <c:choose> 
				    <c:when test="${bean.status==10}">
					<option value="10" selected="selected">准备</option>
					<option value="70">有效</option>
					</c:when>
					<c:otherwise>
					<option value="10">准备</option>
					<option value="70" selected="selected">有效</option>
					</c:otherwise>
					</c:choose>
				</select>				
				</c:otherwise>
			</c:choose>
			</div>
		      
	     </div>
		
		<div class="form-group">
		      <label for="endDate" class="col-sm-2 control-label">时间从:</label>
	          <div class="col-sm-4">
	            <c:choose>
	            <c:when test="${empty bean.dateFrom}">
	                <input id="date_from" name="date_from" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   readonly="true"></input>	   
	            </c:when>
	            <c:otherwise>
	                 <input id="date_from" name="date_from" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${date_from}"  readonly="true"></input>	   	   
	            </c:otherwise>
	           </c:choose>
	         </div>
	         
			 <label for="endDate" class="col-sm-2 control-label">时间到:</label>
	          <div class="col-sm-4">
	            <c:choose>
	            <c:when test="${empty bean.dateTo}">
	                <input id="date_to" name="date_to"  type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  readonly="true"></input>	   
	            </c:when>
	            <c:otherwise>
	                 <input id="date_to" name="date_to"  type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${date_to}"  readonly="true"></input>	   	   
	            </c:otherwise>
	           </c:choose>
	         </div>
		</div>
		
		
		     <input type="button"  id="addProduct" value="选择文章" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal"/>
		
		
		<table class="table table-bordered" id="parameterTable">
			<thead>
				<tr>
					<th width="150px">文章标题</th>
					<th width="150px">作者</th>
					<th width="150px">文章分类</th>
					<th width="100px">是否置顶</th>
					<th width="60px">排序</th>
					<th width="100px">操作</th>
				</tr>
			</thead>		
			<tbody id="storageg">
				<c:choose>
					<c:when test="${not empty list}">
						<c:forEach items="${list}" var="list" varStatus="abc">
						 <tr class="parameterTr">
						    <td style="display:none">
						        <input  type="hide" class="form-control" readonly="readonly" name="basArticle[${abc.index}].id"  value="${list.id}"/>
						    </td>
						 	<td>
						 	   <input  type="text" class="form-control" readonly="readonly" name="basArticle[${abc.index}].title"  value="${list.title}"/>
						 	</td>
							<td>
							    <input  type="text" class="form-control" readonly="readonly" name="basArticle[${abc.index}].author"  value="${list.author}"/>
							</td>
							<td>
							     <input  type="text" class="form-control" readonly="readonly" name="basArticle[${abc.index}].articleCategory"  value="${list.articleCategory}"/>
							</td>
							<td>
							    <select   name="basArticle[${abc.index}].isTop"  class="form-control">
			                    <c:choose> 
							       <c:when test="${list.isTop==false}">
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
							     <input  type="text" class="form-control parametersIdx"  name="basArticle[${abc.index}].idx"  value="${list.idx}"/>
							</td>
							<td><a href="javascript:void(0);" id="deleteId" class="btn btn-primary">删除</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
			<table class="table">
				<tr>
					<th>&nbsp;</th>
					<td>
					  <input type="submit" class="btn btn-default" value="确认" />
	                  <a href="<c:url value='/main/articlemain/list'/>" class="btn btn-default">返回</a>
	                  <c:if test="${not empty bean.articleCategory}">
	                  <a href='<c:url value="/main/articlemain/${bean.id}/update"/>'  class="btn btn-default">重置</a>
	                  </c:if>
					</td>
				</tr>
			</table>
	</form>

</div>	
	<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
		   <div class="modal-dialog" >
		      <div class="modal-content" style="width:800px; height:750px;">
		         <div class="modal-header"  >
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="myModalLabel">
		               	选择文章
		            </h4>
		         </div>
		         <div class="modal-body" style="width:780px; height:600px; overflow:scroll;">
		         	<div class="form-group">
		         	   <label for="firstname" class="col-sm-2 control-label" >文章标题</label>
		         	   <input type="text" id="ptitle" class="col-sm-4 form-control" maxlength="100" style="width:200px;float: left" />
		         	   <label for="firstname" class="col-sm-2 control-label" >作者</label>
		         	   <input type="text" id="pauthor" class="col-sm-4 form-control" maxlength="100" style="width:200px;float: left" />
		         	</div>
		         	
		         	<div class="form-group">
		         	   <label for="firstname" class="col-sm-2 control-label" >有效时间从</label>
		         	   <input type="text" id="datefrom" class="col-sm-4 form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true" maxlength="100" style="width:200px;float: left" />
		         	   <label for="firstname" class="col-sm-2 control-label" >有效时间到</label>
		         	   <input type="text" id="dateto" class="col-sm-4 form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true" maxlength="100" style="width:200px;float: left" />
		         	</div>
		         	
		         	<div class="form-group">
		         	<label for="firstname" class="col-sm-2 control-label" >文章大类</label>
		         	   <select type="text" id="categoryId"  name="categoryId"  class="col-sm-4 form-control"  maxlength="100" style="width:200px;float: left">
               		     <option value="">所有</option>
               		     <c:forEach items="${categorys }" var="category">
			               <option value="${category.code}">${category.cname}</option>
               		     </c:forEach>
                       </select>
		         	</div>
		         	
		         	<div class="form-group">
		         	<label for="firstname" class="col-sm-2 control-label" >文章类型</label>
		         	   <select type="text" id="articleCategoryId"  name="articleCategoryId"  class="col-sm-4 form-control"  maxlength="100" style="width:200px;float: left">
               		     <option value="">所有</option>
               		     <c:forEach items="${articleCategorys }" var="articleCategory">
			               <option value="${articleCategory.code}">${articleCategory.cname}</option>
               		     </c:forEach>
                       </select>
		         	</div>
		         	<input type="button"   class="btn btn-primary"   id="searchButton" style="width:50px;" value="查询">
		            
		            
		            <table id="productList" class="table table-bordered">
		            	<thead>
							<tr>
								<th width="10"><input type="checkbox" id="selectAll" /></th>
								<th width="100">文章标题</th>
								<th width="100">作者</th>
								<th width="100">文章类别</th>
							</tr>
						</thead>
						<tbody id="productTr">
						</tbody>
		            </table>
		         </div>
		         <div class="modal-footer">
		            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
		            </button>
		            <button type="button" class="btn btn-primary" id="subProduct">
						确认
		            </button>
		         </div>
		      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
</body>
</html>