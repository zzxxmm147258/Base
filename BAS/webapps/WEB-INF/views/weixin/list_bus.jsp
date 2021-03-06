<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>

<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	<form id="selectForm" action='<c:url value="/main/mainte/weixinbus/list"/>' method="post" >
        <div class="row" style="margin-bottom:10px;">
           <%--  <label class="control-label col-sm-1 col_padding">关注用户ID：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].openid" value="${selectMap.openid}" type="text" class="form-control">
                </div>
            </div> --%>
           <label class="control-label col-sm-1 col_padding">用户名：</label>
           <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].userid" value="${selectMap.userid}" type="text" class="form-control">
                </div>
            </div>
            
           <label class="control-label col-sm-1 col_padding">是否关注：</label>
           <div class="col-sm-3">
                <div class="input-group" style="width:50%">
                	<select name="filter.[v_=_0_,].subscribe" value="${selectMap.subscribe }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${subscribes }" var="subscribe">
               				<c:choose>
								<c:when test="${(not empty selectMap.subscribe) && (selectMap.subscribe eq subscribe.key)}">
									<option value="${subscribe.key}" selected="selected">${subscribe.value}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${subscribe.key}">${subscribe.value}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
                </div>
            </div>
           
            <label class="control-label col-sm-1 col_padding">关注时间：</label>
            <div class="col-sm-3">
                <div class="input-group">
                 <input name="filter.[v_like_3_,].substime" value="${selectMap.substime}" type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  readonly="true">               
                </div>
            </div>
        </div>
        
        <div class="row" style="margin-bottom:10px;">
              <label class="control-label col-sm-1 col_padding">所属部门：</label>
              <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].department" value="${selectMap.department}" type="text" class="form-control">
                </div>
              </div>
              
              <label class="control-label col-sm-1 col_padding">手机号：</label>
              <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].mobile" value="${selectMap.mobile}" type="text" class="form-control">
                </div>
              </div>
              
              <label class="control-label col-sm-1 col_padding">邮箱：</label>
              <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].email" value="${selectMap.email}" type="text" class="form-control">
                </div>
              </div>
        </div>
        
        <div class="row" style="margin-bottom:10px;">
              <label class="control-label col-sm-1 col_padding">微信账号：</label>
              <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].weixinid" value="${selectMap.weixinid}" type="text" class="form-control">
                </div>
              </div>
              
              <label class="control-label col-sm-1 col_padding">禁用：</label>
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
              
             <label class="control-label col-sm-1 col_padding">标识：</label>
              <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].flags" value="${selectMap.flags}" type="text" class="form-control">
                </div>
              </div>
        </div>
        
        <div class="row" style="margin-bottom:10px;">
             <label class="control-label col-sm-1 col_padding">备注：</label>
              <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].remark" value="${selectMap.remark}" type="text" class="form-control">
                </div>
              </div>
              <label class="control-label col-sm-1 col_padding">设置编码：</label>
              <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].appkey" value="${selectMap.appkey}" type="text" class="form-control">
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
                              		<a href='<c:url value="/main/mainte/weixinbus/add"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="right" title="添加数据">添加</a>
                              	</th>
								<th width="150">用户名</th>
								<th width="120">是否关注</th>
								<th width="200">关注时间</th>
								<th width="120">所属部门</th>
								<th width="120">手机号</th>
								<th width="120">邮箱</th>
								<th width="120">微信账号</th>
								<th width="120">禁用</th>
								<th width="120">标识</th>
								<th width="120">备注</th>
								<th width="120">设置编码</th>
								
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr openid="${b.openid }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a  menuname="修改" href='<c:url value="/main/mainte/weixinbus/${b.openid}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>&nbsp&nbsp
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td>${b.userid }</td>
								<td>
								<c:choose>
									<c:when test="${b.subscribe eq 1 }">
										<span class="glyphicon glyphicon-ok" style="font-size:16px" title="已关注">
									</c:when>
									<c:otherwise>
										<span class="glyphicon glyphicon-remove" style="font-size:16px" title="未关注">
									</c:otherwise>
								</c:choose>
								</td>
								<td><fmt:formatDate value="${b.substime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${b.department }</td>
								<td>${b.mobile }</td>
								<td>${b.email }</td>
								<td>${b.weixinid }</td>
								<td>
								<c:choose>
									<c:when test="${b.locked eq 1 }">
										<span class="glyphicon glyphicon-ok" style="font-size:16px" title="已禁用">
									</c:when>
									<c:otherwise>
										<span class="glyphicon glyphicon-remove" style="font-size:16px" title="未禁用">
									</c:otherwise>
								</c:choose>
								</td>
								<td>${b.flags }</td>
								<td>${b.remark }</td>
								<td>${b.appkey }</td>
								
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/main/mainte/weixinbus/list"/>'/></div>
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
		var openid = tr.getAttribute("openid");
		$.ajax({
			url:'<c:url value="/main/mainte/weixinbus/del.ajax" />',
			data:{openid:openid},
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