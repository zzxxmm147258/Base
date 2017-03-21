<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;">
<form id="selectForm" action='<c:url value="/admin/menu/list"/>' method="post">
        <div class="row" style="margin-bottom:10px;">
           	<% 
				request.setAttribute("sysSelect", DataConfig.getSysIdMap());
			%>
            <label class="control-label col-sm-1 col_padding" for="roleidInput">系统号：</label>
            <div class="col-sm-3">
            
            <div class="input-group">
                	<c:choose>
                		<c:when test="${empty sys}">
                			<select name="filter.[v_=_0_,].sysid" value="${selectMap.sysid }" class="form-control">
							<c:forEach items="${sysSelect }" var="sys">
								<c:choose>
									<c:when test="${(not empty selectMap.sysid) && (selectMap.sysid eq sys.key)}">
										<option value="${sys.key}" selected="selected">${sys.key}:${sys.value}</option>
									</c:when>
									<c:otherwise>
				                    	<option value="${sys.key}">${sys.key}:${sys.value}</option>
									</c:otherwise>
								</c:choose>
		                    </c:forEach>
		                   </select>
                		</c:when>
                		<c:otherwise>
                			<input name="filter.[v_=_0_,].sysid" value="${sys }" class="form-control" readonly="true">
                		</c:otherwise>
                	</c:choose>
                </div>
            </div>
        </div>
       </form>
        <div class="click_list row" style="margin:0;">
            <a onclick="onButFind()">查询</a>
        </div>

    <div class="dataTables_wrapper form-inline dt-bootstrap no-footer">
            <div class="dataTables_scroll">
                <div class="dataTables_scrollHead">
                    <div class="dataTables_scrollHeadInner" role="grid">
                        <table class="table-bordered table-condensed table-striped">
                           <thead>
                              <tr class='THD_width'>
                              	<th width="50">序号</th>
                              	<th width="80">操作</td>
								<th width="80">菜单ID</th>
								<th width="120">菜单名称</th>
								<th width="80">级次</th>
								<th width="80">系统号</th>
								<th width="300">菜单URL</th>
								<th width="120">显示方式</th>
								<th width="80">是否启用</th>
								<th width="120">操作人</th>
								<th width="180">创建日期</th>
								<th width="180">最新修改时间</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr menuid="${b.menuid }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a target="_self" menuid="${b.menuid}01" menuname="修改菜单" href='<c:url value="/admin/menu/${b.menuid}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td>${b.menuid }</td>
								<td>${b.menuname }</td>
								<td>${b.level }</td>
								<td>${b.sysid }</td>
								<td>${b.url }</td>
								<td>${b.showType }</td>
								<td>
								<c:choose>
									<c:when test="${b.availabe eq true }">
										<span class="glyphicon glyphicon-ok" style="font-size:16px" title="启用">
									</c:when>
									<c:otherwise>
										<span class="glyphicon glyphicon-remove" style="font-size:16px" title="不启用">
									</c:otherwise>
								</c:choose>
								</td>
								<td>${b.operator }</td>
								<td><fmt:formatDate value="${b.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${b.modifyDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/admin/menu/list"/>'/></div>
        </c:if>
</div>
</body>
<script type="text/javascript">
$("table").htablesize();

function onButFind(){
	$("#selectForm").submit();
}

//删除按钮事件
$("a[name=remove]").click(function(){
if(confirm("是否确定要删除？")){
	var tr = $(this).parent().parent().parent();
	var menuid = tr.attr("menuid");
	$.ajax({
		url:'<c:url value="/admin/menu/del.ajax" />',
		data:{menuid:menuid},
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
</script>
<script type="text/javascript" src='<c:url value="/resources/js/table.js"/>'></script>
</html>