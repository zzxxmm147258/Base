<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>

<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	<form id="selectForm" action='<c:url value="/admin/tetete/list"/>' method="post" >
        <div class="row" style="margin-bottom:10px;">
            <label class="control-label col-sm-1 col_padding">模板号：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].temp_no" value="${selectMap.temp_no}" type="text" class="form-control">
                </div>
            </div>
            <label class="control-label col-sm-1 col_padding">短信有效时间：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].time_limit" value="${selectMap.time_limit}" type="text" class="form-control">
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
                              	<th width="100">操作
                              		<a href='<c:url value="/admin/tetete/add"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="right" title="添加数据">添加</a>
                              	</th>
                              	<th width="200">模板号</th>
					            <th width="200">短信有效时间</th>
					            <th width="500">模板内容</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr tempNo="${b.tempNo }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a  menuname="编辑" href='<c:url value="/admin/tetete/edit?tempNo=${b.tempNo}"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>&nbsp&nbsp
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td style="text-align:center;">${b.tempNo }</td>
								<td style="text-align:center;">${b.timeLimit }</td>
								<td style="text-align:center;">${b.temp }</td>
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/admin/tetete/list"/>'/></div>
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
		var tempNo = tr.getAttribute("tempNo");
		$.ajax({
			url:'<c:url value="/admin/tetete/del.ajax" />',
			data:{tempNo:tempNo},
			type:'post',
			success:function(data){
				if(data){
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