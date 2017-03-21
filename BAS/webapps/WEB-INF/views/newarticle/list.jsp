<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
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
    	    url: basePath+"/common/bas/categroy/catelist",
    	    data:{code:$("#category").val()},
    	    dataType: 'json',
    	    success: function(data){
    	    	var result =data.datas[0].cates;
				var htm="";
				if (result != null) {
					htm +=  '<option value="" selected="selected">所有</option>';
					for (i = 0; i < result.length; i++) {
						htm +=  '<option value="'+result[i].code+'" ';
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
	
});



</script>
<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	<form id="selectForm" action='<c:url value="/main/newarticle/list"/>' method="post" >
        <div class="row" style="margin-bottom:10px;">
             <label class="control-label col-sm-1 col_padding">文章大类:</label>
			 <div class="col-sm-3">
		            <div class="input-group"  style="width:70%" >
		             <select name="filter.[v_=_0_,].category_id" id="category" value="${selectMap.category_id }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${categorys }" var="category">
               				<c:choose>
								<c:when test="${(not empty selectMap.category_id) && (selectMap.category_id eq category.code)}">
									<option value="${category.code}" selected="selected">${category.name}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${category.code}">${category.name}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
				   </div>
			 </div>
        
            <label class="control-label col-sm-1 col_padding">文章类型:</label>
			<div class="col-sm-3">
		            <div class="input-group"  style="width:70%" >
		             <select name="filter.[v_=_0_,].categoryg_id" id="articleCategory" value="${selectMap.categoryg_id }" class="form-control">
               			<option value="">所有</option>
               			<%-- <c:forEach items="${articleCategorys }" var="articleCategory">
               				<c:choose>
								<c:when test="${(not empty selectMap.categoryg_id) && (selectMap.categoryg_id eq articleCategory.code)}">
									<option value="${articleCategory.code}" selected="selected">${articleCategory.name}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${articleCategory.code}">${articleCategory.name}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach> --%>
               		</select>
				   </div>
			</div>
        
            <label class="control-label col-sm-1 col_padding">标题：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].title" value="${selectMap.title}" type="text" class="form-control">
                </div>
            </div>
            
           </div>
           
           <div class="row" style="margin-bottom:10px;">
                 <label class="control-label col-sm-1 col_padding">作者：</label>
                 <div class="col-sm-3">
                    <div class="input-group">
                        <input name="filter.[v_like_3_,].author" value="${selectMap.author}" type="text" class="form-control">
                    </div>
                 </div>
            
                <label class="control-label col-sm-1 col_padding">是否发布：</label>
                <div class="col-sm-3">
                <div class="input-group" style="width:75%">
                	<select name="filter.[v_=_0_,].is_publication" value="${selectMap.is_publication }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${is_publications }" var="is_publication">
               				<c:choose>
								<c:when test="${(not empty selectMap.is_publication) && (selectMap.is_publication eq is_publication.key)}">
									<option value="${is_publication.key}" selected="selected">${is_publication.value}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${is_publication.key}">${is_publication.value}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
                </div>
               </div>
               
            <label class="control-label col-sm-1 col_padding">生效日期：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].effective_date" value="${selectMap.effective_date}" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" >
                </div>
            </div>
               <%-- <label class="control-label col-sm-1 col_padding">是否置顶：</label>
                <div class="col-sm-3">
                <div class="input-group" style="width:75%">
                	<select name="filter.[v_=_0_,].is_top" value="${selectMap.is_top }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${is_tops }" var="is_top">
               				<c:choose>
								<c:when test="${(not empty selectMap.is_top) && (selectMap.is_top eq is_top.key)}">
									<option value="${is_top.key}" selected="selected">${is_top.value}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${is_top.key}">${is_top.value}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
                </div>
              </div> --%>
            
           </div>
          
       </form>
       
        <div class="click_list row" style="margin:0;">
            <a id="ButFind" name="find" onclick="onButFind()">查询</a>
        </div>

    <div class="dataTables_wrapper form-inline dt-bootstrap no-footer">
            <div class="dataTables_scroll">
                <div class="dataTables_scrollHead">
                    <div class="dataTables_scrollHeadInner" role="grid">
                        <table class="table-bordered table-condensed table-striped">
                           <thead>
                              <tr class='THD_width'>
                              	<th width="120">序号</th>
                              	<th width="100">操作
                              		<a href='<c:url value="/main/newarticle/add"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="right" title="添加数据">添加</a>
                              	</th>
                              	<th width="200">标题</th>
					            <th width="200">作者</th>
					            <th width="200">文章大类</th>
					            <th width="200">文章分类</th>
					            <th width="100">是否发布</th>
					            <th width="100">是否置顶</th> 
					            <th width="100">预览图片</th>
					            <th width="100">备用图</th>
					            <th width="200">文章预览地址</th>
					            <th width="100">排序</th>
					            <th width="200">自定义日期</th>
					            <th width="200">生效日期</th>
					            <th width="100">备用字段1</th>
					            <th width="100">备用字段2</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr id="${b.id }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a  menuname="编辑" href='<c:url value="/main/newarticle/edit?id=${b.id}"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>&nbsp&nbsp
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>&nbsp&nbsp
								    <%-- <a target="_blank" menuid="${b.id}" menuname="预览" href='<c:url value="/main/article/details?id=${b.id}"/>'><span class="glyphicon glyphicon-eye-open" style="font-size:16px"/></a> --%>
								</td>
								<td style="text-align:center;">
								  <c:choose>
								    <c:when test="${b.isUrl eq true}">
								    	<a target="_blank" menuid="${b.id}" href='${b.contentUrl}'>${b.title }</a>
								    </c:when>
									<c:otherwise>
								        <a target="_blank" menuid="${b.id}" href='<c:url value="/common/article/article?id=${b.id}&title=${b.title}"/>'>${b.title }</a>
								    </c:otherwise>
							       </c:choose>
								</td>
								<td style="text-align:center;">${b.author }</td>
								<td style="text-align:center;">${b.categoryId }</td>
								<td style="text-align:center;">${b.categorygId }</td>
								<td style="text-align:center;">
							      <c:choose>
								    <c:when test="${b.isPublication eq true}">
										是
									</c:when>
									<c:when test="${b.isPublication eq false }">
										否
									</c:when>
							     </c:choose>
						        </td>
						        <td style="text-align:center;">
							      <c:choose>
								    <c:when test="${b.isTop eq true}">
										是
									</c:when>
									<c:when test="${b.isTop eq false }">
										否
									</c:when>
							     </c:choose>
						        </td>
						        <td style="text-align: center;"> 
     								<img alt="" src="${b.icon}"  style="width:30px;height:30px;">
     						    </td>
     						    <td style="text-align: center;"> 
     								<img alt="" src="${b.img}"  style="width:30px;height:30px;">
     						    </td>
     						    <td style="text-align:center;">
									${basePath }common/article/article?id=${b.id}
								</td>
     						    <td style="text-align:center;">${b.sort }</td>
     						    <td style="text-align:center;"><fmt:formatDate value="${b.activeDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
     						    <td style="text-align:center;"><fmt:formatDate value="${b.effectiveDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td style="text-align:center;">${b.attr5 }</td>
								<td style="text-align:center;">${b.attr6 }</td>
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/main/newarticle/list"/>'/></div>
        </c:if>
</div>
</body>
<script type="text/javascript">
	
	$("table").htablesize();
	$("#selectForm input").attr("onkeydown","if(event.keyCode==13){$('#selectForm').submit();}"); //添加回车事件
	function onButFind(){
		pageFormSubmit();
	}

	//删除按钮事件
	$("a[name=remove]").click(function(){
	if(confirm("是否确定要删除？")){
		var tr = this.parentNode.parentNode.parentNode;
		var id = tr.getAttribute("id");
		$.ajax({
			url:'<c:url value="/main/newarticle/del.ajax" />',
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
	});

</script>
<script type="text/javascript" src='<c:url value="/resources/js/table.js"/>'></script>
</html>