<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;">
	<div class="click_list row" style="margin:0;">
        <a href='<c:url value="/admin/shirolimit/list"/>'>刷新</a>
    </div>
    <div class="dataTables_wrapper form-inline dt-bootstrap no-footer">
            <div class="dataTables_scroll">
                <div class="dataTables_scrollHead">
                    <div class="dataTables_scrollHeadInner" role="grid">
                        <table class="table table-bordered table-striped">
                           <thead>
                              <tr class='THD_width'>
                              	<th width="50">序号</th>
                              	<th width="80">操作</td>
								<th width="80">系统号</th>
								<th width="80">名称</th>
								<th width="80">URL描述</th>
								<th width="150">User\角色\操作</th>
								<th width="80">序号</th>
								<th width="80">备注</th>
								<th width="80">是否启用</th>
								<th width="80">操作人</th>
								<th width="150">创建日期</th>
								<th width="150">最新修改时间</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr shiroid="${b.id }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a target="_self" menuid="${b.id}01" menuname="修改shiro" href='<c:url value="/admin/shirolimit/${b.id}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td>${b.sysid }</td>
								<td>${b.name }</td>
								<td>${b.url }</td>
								<td>${b.limits }</td>
								<td>${b.idx }</td>
								<td>${b.remark }</td>
								<td>${b.availabe }</td>
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
//删除按钮事件
$("a[name=remove]").click(function(){
if(confirm("是否确定要删除？")){
	var tr = this.parentNode.parentNode;
	var shiroid = tr.getAttribute("shiroid");
	$.ajax({
		url:'<c:url value="/admin/shirolimit/del.ajax" />',
		data:{shiroid:shiroid},
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
