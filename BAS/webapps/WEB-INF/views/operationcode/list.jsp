<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;">
	<div class="click_list row" style="margin:0;">
        <a href='<c:url value="/admin/operationcode/list"/>'>刷新</a>
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
                              	<th width="80">操作号</th>
                              	<th width="100">操作名称</th>
								<th width="200">备注</th>
								<th width="120">操作人</th>
								<th width="150">创建日期</th>
								<th width="150">最新修改时间</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr operaid="${b.operaid }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a target="_self" menuid="${b.operaid}01" menuname="修改操作码" href='<c:url value="/admin/operationcode/${b.operaid}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td>${b.operaid }</td>
								<td>${b.operaname }</td>
								<td>${b.remark }</td>
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
	var operaid = tr.attr("operaid");
	$.ajax({
		url:'<c:url value="/admin/operationcode/del.ajax" />',
		data:{operaid:operaid},
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
