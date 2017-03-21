<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/assets/js/sea.js'/>"></script>
<%
	String path = request.getContextPath();
	//获得项目完全路径（假设你的项目叫MyApp，那么获得到的地址就是
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
var basePath = "<%=basePath%>";
$().ready(function() {
	var $inputForm = $("#selectForm");
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
					htm +=  '<option value="" selected="selected">所有</option>';
					for (i = 0; i < result.length; i++) {
						htm +=  '<option value="'+result[i].code+'" ';
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
	
});



</script>

<body>
	<div class="container" style="padding:0;margin:0;height：100%;width:100%;">
	    <div class="col-sm-12" style="padding:0 2px;">
	    
	    </div>
	
		<div class="col-sm-6" style="padding:0 2px;">
		<%-- <ul class="nav nav-tabs">
				<li class="active"><a hasLode="false" url='<c:url value="/main/articlemain/getArticle"/>' onclick="getChild(this)" href="#texttempg" name="texttempg" data-toggle="tab">文章类别</a></li>
		</ul> --%>
		<form id="selectForm" action='<c:url value="/main/articlemain/list"/>' method="post" >
         <div class="row" style="margin-bottom:10px;">
             <label class="control-label col-sm-1 col_padding">大类:</label>
			 <div class="col-sm-3">
		            <div class="input-group"  >
		             <select name="filter.[v_=_0_,].category" id="category" value="${selectMap.category }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${categorys }" var="category">
               				<c:choose>
								<c:when test="${(not empty selectMap.category) && (selectMap.category eq category.code)}">
									<option value="${category.code}" selected="selected">${category.cname}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${category.code}">${category.cname}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
				   </div>
			 </div>
			<label class="control-label col-sm-1 col_padding">类型:</label>
			<div class="col-sm-3">
		            <div class="input-group"  >
		             <select name="filter.[v_=_0_,].article_category" id="articleCategory" value="${selectMap.article_category }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${articleCategorys }" var="articleCategory">
               				<c:choose>
								<c:when test="${(not empty selectMap.article_category) && (selectMap.article_category eq articleCategory.code)}">
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
			 
              <a id="ButFind" name="find" onclick="onButFind()" class="btn btn-primary" style="float:left;margin-left:10px;margin-top:3px;">查询</a>
         </div> 
       </form>
		
		<div style="overflow:auto;width:100%;" class="scrollWH" scrollH="90">
			<table class="table table-hover table-bordered" name="parent">
				<thead bgcolor="#F0F8FF"  class="text-center">
					<tr>
						 <td>操作
							<a href='<c:url value="/main/articlemain/add"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
								data-toggle="tooltip" data-placement="right"
								title="添加数据">添加</a>
						</td>
						<td >标题</td>
						<td >文章大类</td>
						<td >文章类型</td>
						<td >状态</td>
						<td >生效时间</td>
						<td >时间从</td>
						<td >时间到</td>
					</tr>
				</thead>
				<tbody name="dictid">
					<c:forEach items="${basArticleMain }" var="b">
						<tr id="${b.id}" onclick="getChild(this)">
						   <td class="text-center">
								<a href='<c:url value="/main/articlemain/${b.id}/update"/>'><span name="edit" class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"></span></a>&nbsp&nbsp
							    <a id="deleteTr" name="tagname"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
							</td>
							<td style="text-align:center;">${b.title }</td>
							<td style="text-align:center;">${b.category }</td>
							<td style="text-align:center;">
							    <c:choose>
								    <c:when test="${b.articleCategory eq allnews}">
										全部
									</c:when>
									<c:otherwise>
										${b.articleCategory }
									</c:otherwise>
							     </c:choose>
							</td>
							<td style="text-align:center;">
							    <c:choose>
								    <c:when test="${b.status eq 10}">
										准备
									</c:when>
									<c:when test="${b.status eq 70 }">
										有效
									</c:when>
							     </c:choose>
							</td>
							<td><fmt:formatDate value="${b.effectiveDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${b.dateFrom }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${b.dateTo }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</div>

		<div class="col-sm-6" style="padding:0 0px;margin:0;" >
			<ul class="nav nav-tabs">
				<li class="active"><a hasLode="false" url='<c:url value="/main/articlemain/getArticle"/>' onclick="getChild(this)" href="#texttempg" name="texttempg" data-toggle="tab">文章列表</a></li>
			</ul>
			<div class="tab-content" >
				<!--信息明细 -->
				<div class="scrollWH tab-pane fade in active"  style="overflow:auto;width:100%;" id="texttempg" scrollH="90">
					<table class="table table-hover table-bordered table-responsive" style="table-layout:fixed;">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
							    <td width="50">操作</td>
						        <td width="200">标题</td>
						        <td width="80">作者</td>
						        <td width="50">是否发布</td>
						        <td width="50">排序</td>
						        <td width="50">预览图片</td>
							</tr>
						</thead>
						<tbody name="texttempg">

						</tbody>
					</table>
				
				</div>

				
			
		</div>
		</div>
	</div>
	

</body>
<script type="text/javascript">
$(function () { $("[data-toggle='tooltip']").tooltip(); });  //触发提示工具（tooltip）


var parent = "";       //父级ID
var table = $("div.tab-content .active").attr("id");   //当前显示的标签的table的id属性
var url = $(".nav-tabs .active a").attr("url"); 	   //当前显示的标签的url

function getChild(obj){
	
	if($(obj).attr("href")==$(".nav-tabs .active a").attr("href"))return;  		//如果点击的是当前显示的标签则不做任何事
	var uu = $(obj).attr("url"); 
	if(uu){     										//点的是标签
		url = uu;
		table = $(obj).attr("name");    
	}else{      										//点的是父级
		url = $(".nav-tabs .active a").attr("url");   	//当前显示的标签的url
		parent = $(obj).attr("id");               	//父级ID.
		$(obj).attr("style","background:#ECF0EA").siblings().attr("style",""); 	//修改tr背景色
		$(".nav-tabs li a").attr("hasLode","false");
		$(".nav-tabs .active a").attr("hasLode","true"); 
	}
	//alert("父级ID："+parent+"  链接："+url+"   当前标签ID："+table);
	if(!uu || $(obj).attr("hasLode")=="false"){
		$.ajax({
				url:url,
				data:{id:parent},
				type:'post',
				success:function(data){
					choseCreate(data);
					if(uu){    							//点击的是标签
						$(obj).attr("hasLode","true");  //改变此标签的属性
					}
				},
				error:function(){
					//alert("提交失败");
				}
			})
	}
}

	function choseCreate(data){
		var json = eval(data);
		var html = "";
		if(json.length>0){
			for(var i=0;i<json.length;i++){
				var j = i+1;
				switch (table){
					case "texttempg":
						html+= '<tr id='+json[i].id+' mainid='+json[i].id+'>'
						    +'<td class="text-center">'
			                +'<a name="texttempg" ><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>'
			                +'</td>'
							+'<td class="text-center">';
							if(json[i].isUrl){
								html+='<a target="_blank" menuid="'+json[i].id+'" href="'+json[i].contentUrl+'">'+json[i].title+'</a>';
							}else{
								html+='<a target="_blank" menuid="'+json[i].id+'" href="<c:url value="/common/article/details?id='+json[i].id+'"/>">'+json[i].title+'</a>';
							}
							html+='</td>'
							+'<td class="text-center">'+json[i].author+'</td>'
							+'<td class="text-center">'+json[i].seoKeywords+'</td>'
							+'<td class="text-center">'+json[i].idx+'</td>'
							+'<td class="text-center"> <img alt="" src='+json[i].url+' style="width:30px;height:30px;"> </td>'
		                    +'</tr>';
						break;
					default:
						break;
					}
			}
		}
		$('tbody[name='+table+']').html(html);
		$(function () { $("[data-toggle='tooltip']").tooltip(); });  //触发提示工具（tooltip）
	}
	
	function addChild(obj){
		var parentTr = $('table[name=parent] tbody tr[style="background:#ECF0EA"]');
		if(parentTr.length==0){
			$('#myModal').modal('show');
		}else{
			var mId = parentTr.attr("id");
			/* var id = $("div.tab-content .active").attr("id");   //当前显示的标签的table的id属性 */
			
			$(obj).attr("href",'<c:url value="/main/weixin/texttempg/'+mId+'/add"/>');
			
		}
	}
	
	$(document).on('click','#deleteTr',function(){
		if(confirm("是否确定要删除？")){
			var tr = $(this).parent().parent().parent();
			var id = tr.attr("id");
			$.ajax({
				url:'<c:url value="/main/articlemain/delmain.ajax" />',
				data:{id:id},
				type:'post',
				success:function(data){
					if(data > 0){
					tr.remove();
					}
				},
				error:function(){
					alert("提交失败");
				}
			})
		} 
	})

	
	$("tbody[name=texttempg]").on('click','tr td a[name=texttempg]',function(){
		if(confirm("是否确定要删除？")){
			var tr = this.parentNode.parentNode;
			var id = tr.getAttribute("id");
			$.ajax({
				url:'<c:url value="/main/articlemain/del.ajax" />',
				data:{id:id},
				type:'post',
				success:function(data){
					if(data > 0){
					tr.remove();
					}
				},
				error:function(){
					alert("提交失败");
				}
			})
		}
		
	})
$("tr[id='${id}']").click();
	
	
	$("table").htablesize();
	$("#selectForm input").attr("onkeydown","if(event.keyCode==13){$('#selectForm').submit();}"); //添加回车事件
	function onButFind(){
		$("#selectForm").submit();
	}
</script>
</html>