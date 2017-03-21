<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>

<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	   <form id="selectForm" action='<c:url value="/main/bas/version/list"/>' method="post" >
           <div class="row" style="margin-bottom:10px;">
               <label class="control-label col-sm-1 col_padding">版本号：</label>
                <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].number" value="${selectMap.number}" type="text" class="form-control">
                </div>
               </div>
               
               <label class="control-label col-sm-1 col_padding">版本名称：</label>
                <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].name" value="${selectMap.name}" type="text" class="form-control">
                </div>
               </div>
               
               <label class="control-label col-sm-1 col_padding">更新时间：</label>
               <div class="col-sm-3">
                 <div class="input-group">
                 <input name="filter.[v_like_3_,].version_date" value="${selectMap.version_date}" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true">               
                  </div>
               </div>
               
            </div>
            
       </form>
       <div class="click_list row" style="margin:0;">
            <a id="ButFind" name="find" onclick="onButFind()">查询</a>
            <a style="margin-left: 20px;" href='<c:url value="/main/bas/version/add"/>' >添加</a>
        </div>

    <div class="dataTables_wrapper form-inline dt-bootstrap no-footer">
            <div class="dataTables_scroll">
                <div class="dataTables_scrollHead">
                    <div class="dataTables_scrollHeadInner" role="grid">
                        <table class="table table-bordered table-striped">
                           <thead>
                              <tr class='THD_width'>
                              	<th width="80">序号</th>
                                <th width="150">操作</th>
                                <th width="150">版本号</th>
                                <th width="150">版本名称</th>
								<th width="120">更新类型</th>
                         		<th width="120">是否必要更新</th>
                                <th width="200">更新时间</th>
                         		<th width="200">下载链接</th>
                         		<th width="300">更新内容</th>
                         		<th width="120">项目简称</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="p" varStatus="i">
							<tr id="${p.id }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a  menuname="修改" href='<c:url value="/main/bas/version/add?id=${p.id }"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>&nbsp&nbsp
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td>${p.number }</td>
								<td>${p.name }</td>
								<td>${p.type }</td>
								<td>
								    <c:if test="${p.isnecessary eq 'true'}">是</c:if>
									<c:if test="${p.isnecessary eq 'false'}">否</c:if> 
								</td>
								<td><fmt:formatDate value="${p.versionDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${p.url }</td>
								<td>${p.content }</td>
								<td>${p.attr1 }</td>
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/main/bas/version/list"/>'/></div>
        </c:if>
</div>
</body>
<script type="text/javascript">

	$("#selectForm input").attr("onkeydown","if(event.keyCode==13){$('#selectForm').submit();}"); //添加回车事件
	function onButFind(){
		pageFormSubmit();
	}

	//删除按钮事件
	$("a[name=remove]").click(function(){
	if(confirm("是否确定要删除？")){
		var tr = this.parentNode.parentNode;
		var id = tr.getAttribute("id");
		$.ajax({
			url:'<c:url value="/main/bas/version/del.ajax" />',
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