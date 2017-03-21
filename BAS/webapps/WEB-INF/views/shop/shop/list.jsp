<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	<form id="selectForm" action='<c:url value="/admin/shop/list"/>' method="post" >
        <div class="row" style="margin-bottom:10px;">
             <label class="control-label col-sm-1 col_padding">商户编码：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].shop_id" value="${selectMap.shop_id}" type="text" class="form-control">
                </div>
            </div>
         
            <label class="control-label col-sm-1 col_padding">商户户名：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].shopname" value="${selectMap.shopname}" type="text" class="form-control">
                </div>
            </div>
            
            <label class="control-label col-sm-1 col_padding">楼层：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].floor_id" value="${selectMap.floor_id}" type="text" class="form-control">
                </div>
            </div>
        </div> 
        
        <div class="row" style="margin-bottom:10px;">   
            <label class="control-label col-sm-1 col_padding">商户地址：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].shop_address" value="${selectMap.shop_address}" type="text" class="form-control">
                </div>
            </div>
            
            <label class="control-label col-sm-1 col_padding">商户类型：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].shop_type" value="${selectMap.shop_type}" type="text" class="form-control">
                </div>
            </div>
        
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
         </div>
         
        <div class="row" style="margin-bottom:10px;">   
            <label class="control-label col-sm-1 col_padding">操作人：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].operator" value="${selectMap.operator}" type="text" class="form-control">
                </div>
            </div>
            
            <label class="control-label col-sm-1 col_padding">电话：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].phone" value="${selectMap.phone}" type="text" class="form-control">
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
                              		<a href='<c:url value="/admin/shop/addshop"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="right" title="添加数据">添加</a>
                              		</th>
                              	<th width="100">商户编码</th>
								<th width="100">商户名称</th>
								<th width="100">商户英文名称</th>
								<th width="100">楼层名称</th>
								<th width="100">商户地址</th>
								<th width="100">logo</th>
								<th width="100">商户类型</th>
								<th width="100">人均消费</th>
								<th width="100">图片</th>
								<th width="100">特色图片</th>
								<th width="100">是否启用</th>
								<th width="100">经度</th>
								<th width="100">纬度</th>
								<th width="100">电话</th>
								<th width="100">排序</th>
								<th width="100">人气</th>
								<th width="100">评论数</th>
								<th width="100">商圈排行</th>
								<th width="100">街区排行</th>
								<th width="100">单元排行</th>
								<th width="100">楼层排行</th>
								<th width="100">操作人</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr shopId="${b.shopId }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a  menuname="修改" href="edit?shopId=${b.shopId}" ><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>&nbsp&nbsp
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td style="text-align:center;">${b.shopId }</td>
								<td style="text-align:center;">${b.shopname }</td>
								<td style="text-align:center;">${b.enshopname }</td>
								<td style="text-align:center;">${b.floorId }</td>
								<td style="text-align:center;">${b.shopAddress }</td>
								<td style="text-align: center;"> 
     								<img alt="" src="${b.shopLogo}"  style="width:30px;height:30px;">
     							</td>
								<td style="text-align:center;">${b.shopType }</td>
								<td style="text-align:center;">${b.perConsume }</td>
								<td style="text-align: center;"> 
								   <c:forEach items="${b.imagess }" var="imagess" >
     								  <img alt="" src="${imagess}"  style="width:30px;height:30px;">
     							   </c:forEach>
     							</td>
     							<td style="text-align: center;"> 
     							   <c:forEach items="${b.chaIcons }" var="chaIcons" >
     								  <img alt="" src="${chaIcons}"  style="width:30px;height:30px;">
     							    </c:forEach>
     							</td>
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
								<td style="text-align:center;">${b.longitude }</td>
								<td style="text-align:center;">${b.latitude }</td>
								<td style="text-align:center;">${b.phone }</td>
								<td style="text-align:center;">${b.sort }</td>
								<td style="text-align:center;">${b.nice }</td>
								<td style="text-align:center;">${b.replyNo }</td>
								<td style="text-align:center;">${b.districtIndex }</td>
								<td style="text-align:center;">${b.blockIndex }</td>
								<td style="text-align:center;">${b.buildingIndex }</td>
								<td style="text-align:center;">${b.floorIndex }</td>
								<td style="text-align:center;">${b.operator }</td>
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/admin/shop/list"/>'/></div>
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
		var shopId = tr.getAttribute("shopId");
		$.ajax({
			url:'<c:url value="/admin/shop/del.ajax" />',
			data:{shopId:shopId},
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