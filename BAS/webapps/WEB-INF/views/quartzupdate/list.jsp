<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>

<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	<form id="selectForm" action='<c:url value="/admin/quartzupdate/list"/>' method="post" >
        <div class="row" style="margin-bottom:10px;">
             <label class="control-label col-sm-1 col_padding">编号：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].update_no" value="${selectMap.update_no}" type="text" class="form-control">
                </div>
            </div>
         
            <label class="control-label col-sm-1 col_padding">描述：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].update_name" value="${selectMap.update_name}" type="text" class="form-control">
                </div>
            </div>
            
            <label class="control-label col-sm-1 col_padding">日期：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].update_date" value="${selectMap.update_date}" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  readonly="true">
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
                              	<th width="100">操作
                              		<a href='<c:url value="/admin/quartzupdate/add"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="right" title="添加数据">添加</a>
                              		</th>
                              	<th width="160">编号</th>
								<th width="200">描述</th>
								<th width="160">日期</th>
								<th width="100">自定义1</th>
								<th width="100">自定义2</th>
								
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr id="${b.id }">
								<td class="text-center">
									<a  menuname="修改" href='<c:url value="/admin/quartzupdate/${b.id}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>&nbsp&nbsp
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td style="text-align:center;">${b.id }</td>
								<td style="text-align:center;">${b.updateName }</td>
								<td style="text-align:center;"><fmt:formatDate value="${b.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td style="text-align:center;">${b.attrName1 }</td>
								<td style="text-align:center;">${b.attrName2 }</td>
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/admin/quartzupdate/list"/>'/></div>
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
		var tr = this.parentNode.parentNode.parentNode;
		var id = tr.getAttribute("id");
		$.ajax({
			url:'<c:url value="/admin/quartzupdate/del.ajax" />',
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