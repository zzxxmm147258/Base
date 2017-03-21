<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	<form id="selectForm" action='<c:url value="/admin/countrybas/list"/>' method="post" >
        <div class="row" style="margin-bottom:10px;">
            <label class="control-label col-sm-1 col_padding">名称：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].title" value="${selectMap.title}" type="text" class="form-control">
                </div>
            </div>
            
             <label class="control-label col-sm-1 col_padding">编号：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].code" value="${selectMap.code}" type="text" class="form-control">
                </div>
            </div>
            
            <label class="control-label col-sm-1 col_padding">是否启用：</label>
           <div class="col-sm-3">
                <div class="input-group" style="width:50%">
                	<select name="filter.[v_=_0_,].locked" value="${selectMap.locked }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${lockeds }" var="locked">
               				<c:choose>
								<c:when test="${(not empty selectMap.locked) && (selectMap.locked eq locked.key)}">
									<option value="${locked.key}" selected="selected">${locked.value}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${locked.key}">${locked.value}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
                </div>
            </div>
            
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
                              	<th width="210">操作
                              	<a href='<c:url value="/admin/countrybas/add"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="right" title="添加数据">添加</a>
                              	</th>
								<th width="210">国家名称</th>
								<th width="210">编号</th>
								<th width="210">是否启用</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr id="${b.id }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a target="_self" menuid="${b.id}01" menuname="修改用户" href='<c:url value="/admin/countrybas/${b.id}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>&nbsp&nbsp
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td>${b.title }</td>
								<td>${b.code }</td>
								<td>
								<c:choose>
									<c:when test="${b.locked eq true }">
										<span class="glyphicon glyphicon-ok" style="font-size:16px" title="已启用">
									</c:when>
									<c:otherwise>
										<span class="glyphicon glyphicon-remove" style="font-size:16px" title="未启用">
									</c:otherwise>
								</c:choose>
								</td>
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/admin/countrybas/list"/>'/></div>
        </c:if>
</div>
</body>
<script type="text/javascript">
$("table").htablesize();

	$("#selectForm input").attr("onkeydown","if(event.keyCode==13){$('#selectForm').submit();}"); //添加回车事件
	function onButFind(){
		$("#selectForm").submit();
	}

	//删除按钮事件
	$("a[name=remove]").click(function(){
	if(confirm("是否确定要删除？")){
		var tr = $(this).parent().parent().parent();
		var id = tr.attr("id");
		$.ajax({
			url:'<c:url value="/admin/countrybas/del.ajax" />',
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