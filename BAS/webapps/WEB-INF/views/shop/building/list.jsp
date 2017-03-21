<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	<form id="selectForm" action='<c:url value="/admin/building/list"/>' method="post" >
        <div class="row" style="margin-bottom:10px;">
            <label class="control-label col-sm-1 col_padding">单元楼号：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].buiding_no" value="${selectMap.buiding_no}" type="text" class="form-control">
                </div>
            </div>
            
            <label class="control-label col-sm-1 col_padding">单元名称：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].buiding_name" value="${selectMap.buiding_name}" type="text" class="form-control">
                </div>
            </div>
            
            <label class="control-label col-sm-1 col_padding">街区名称：</label>
            <div class="col-sm-3">
                 <div class="input-group" style="width:79%">
                <select name="filter.[v_=_0_,].block_id" value="${selectMap.block_id }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${blockBas }" var="blockBas">
               				<c:choose>
								<c:when test="${(not empty selectMap.block_id) && (selectMap.block_id eq blockBas.blockName)}">
									<option value="${blockBas.blockName}" selected="selected">${blockBas.blockName}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${blockBas.blockName}">${blockBas.blockName}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
                </div>
            </div> 
           </div>
          
           <div class="row" style="margin-bottom:10px;"> 
              <label class="control-label col-sm-1 col_padding">是否启用：</label>
              <div class="col-sm-3">
                <div class="input-group" style="width:50%">
                	<select name="filter.[v_=_0_,].availabe" value="${selectMap.availabe }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${availabes }" var="availabe">
               				<c:choose>
								<c:when test="${(not empty selectMap.availabe) && (selectMap.availabe eq availabe.key)}">
									<option value="${availabe.key}" selected="selected">${availabe.value}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${availabe.key}">${availabe.value}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
                </div>
              </div>
            
             <label class="control-label col-sm-1 col_padding">操作人：</label>
             <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].operator" value="${selectMap.operator}" type="text" class="form-control">
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
                              	<th width="200">操作
                              		<a href='<c:url value="/admin/building/add"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="right" title="添加数据">添加</a>
                              	</th>
                              	<th width="200">单元楼号</th>
								<th width="200">单元名称</th>
								<th width="200">街区名称</th>
								<th width="200">单元logo</th>
								<th width="200">导览图</th>
								<th width="200">排序</th>
								<th width="200">是否启用</th>
								<th width="200">操作人</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr buildingId="${b.buildingId }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a  menuname="修改" href='<c:url value="/admin/building/${b.buildingId}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>&nbsp&nbsp
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td style="text-align:center;">${b.buildingNo }</td>
								<td style="text-align:center;">${b.buildingName }</td>
								<td style="text-align:center;">${b.blockId }</td>
								<td style="text-align: center;"> 
     								<img alt="" src="${b.buildingLogo}"  style="width:30px;height:30px;">
     							</td>
     							<td style="text-align: center;"> 
     								<img alt="" src="${b.tourImg}"  style="width:30px;height:30px;">
     							</td>
								<td style="text-align:center;">${b.idx }</td>
								<td style="text-align:center;">
								<c:choose>
									<c:when test="${b.availabe eq true }">
										<span class="glyphicon glyphicon-ok" style="font-size:16px" title="已启用">
									</c:when>
									<c:otherwise>
										<span class="glyphicon glyphicon-remove" style="font-size:16px" title="未启用">
									</c:otherwise>
								</c:choose>
								</td>
								<td style="text-align:center;">${b.operator }</td>
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/admin/building/list"/>'/></div>
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
		var buildingId = tr.getAttribute("buildingId");
		$.ajax({
			url:'<c:url value="/admin/building/del.ajax" />',
			data:{buildingId:buildingId},
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