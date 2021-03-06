<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;">
        <div class="row" style="margin-bottom:10px;">
            <label class="control-label col-sm-1 col_padding" for="roleidInput">角色ID：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input id="roleidInput" value="${co.roleid }" type="text" class="form-control" onkeydown="if(event.keyCode==13){document.getElementById('ButFind').click();}">
                </div>
            </div>
            <label class="control-label col-sm-1 col_padding" for="rotypeInput">角色类型：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input id="rotypeInput" value="${co.rotype }" type="text" class="form-control" onkeydown="if(event.keyCode==13){document.getElementById('ButFind').click();}">
                </div>
            </div>
            <label class="control-label col-sm-1 col_padding" for="rolenameInput">角色名称：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input id="rolenameInput" value="${co.rolename }" type="text" class="form-control" onkeydown="if(event.keyCode==13){document.getElementById('ButFind').click();}">
                </div>
            </div>
        </div>
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
                              	<th width="50">序号</th>
                              	<th width="80">操作</td>
								<th width="80">角色ID</th>
								<th width="80">角色类型</th>
								<th width="80">角色名称</th>
								<th width="300">描述</th>
								<th width="80">是否启用</th>
								<th width="120">操作人</th>
								<th width="150">创建日期</th>
								<th width="150">最新修改时间</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr roleid="${b.roleid }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a target="_self" menuid="${b.roleid}01" menuname="修改角色" href='<c:url value="/admin/role/${b.roleid}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>
									<%-- <a name="power" href='<c:url value="/admin/role/${b.roleid}/power"/>'><span class="glyphicon glyphicon-link" style="font-size:16px" title="分配菜单"></span></a> --%>
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td>${b.roleid }</td>
								<td>${b.rotype }</td>
								<td>${b.rolename }</td>
								<td>${b.description }</td>
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
        	<div style="float:left"><portal:page url="${url }"/></div>
        </c:if>
</div>
</body>
<script type="text/javascript">
$("table").htablesize();

//删除按钮事件
$("a[name=remove]").click(function(){
if(confirm("是否确定要删除？")){
	var tr = $(this).parent().parent().parent();
	var roleid = tr.attr("roleid");
	$.ajax({
		url:'<c:url value="/admin/role/del.ajax" />',
		data:{roleid:roleid},
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

function onButFind(){
	var url = "";
	var roleid = $('#roleidInput').val();
	var rotype = $('#rotypeInput').val();
	var rolename = $('#rolenameInput').val();
	
	url = "${pageContext.request.contextPath}/admin/role/list?";
	if(null!=roleid && ''!=roleid){
		url += "&roleid="+roleid;
	};
	if(null!=rotype && ''!=rotype){
		url += "&rotype="+rotype;
	};
	if(null!=rolename && ''!=rolename){
		url += "&rolename="+rolename;
	};
	
	$('a[name=find]').attr("href",url);
}
</script>
<script type="text/javascript" src='<c:url value="/resources/js/table.js"/>'></script>
</html>