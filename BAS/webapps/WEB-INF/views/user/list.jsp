<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>

<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	<form id="selectForm" action='<c:url value="/admin/main/list"/>' method="post" >
        <div class="row" style="margin-bottom:10px;">
            <label class="control-label col-sm-1 col_padding">用户账号：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].username" value="${selectMap.username}" type="text" class="form-control">
                </div>
            </div>
            <%-- <label class="control-label col-sm-1 col_padding">用户类型：</label>
            <div class="col-sm-3">
                <div class="input-group" style="width:50%">
               		<select name="filter.[v_=_0_,].utype" value="${selectMap.utype }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${utypes }" var="utype">
               				<c:choose>
								<c:when test="${(not empty selectMap.utype) && (selectMap.utype eq utype)}">
									<option value="${utype}" selected="selected">${utype}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${utype}">${utype}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
                </div>
            </div> --%>
            <label class="control-label col-sm-1 col_padding">帐套列表：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].worklist" value="${selectMap.worklist }" type="text" class="form-control">
                </div>
            </div>
            <label class="control-label col-sm-1 col_padding">系统列表：</label>
            <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].sysidlist" value="${selectMap.sysidlist }" type="text" class="form-control">
                </div>
            </div>
        </div>
         <div class="row" style="margin-bottom:10px;">
         	<label class="control-label col-sm-1 col_padding">商铺：</label>
            <div class="col-sm-3">
                <div class="input-group" style="width:50%">
                	<select name="filter.[v_=_0_,].shopid" value="${selectMap.shopid }" class="form-control">
               			<option value="">所有</option>
               			<c:forEach items="${shops }" var="shop">
               				<c:choose>
								<c:when test="${(not empty selectMap.shopid) && (selectMap.shopid eq shop.shopId)}">
									<option value="${shop.shopId}" selected="selected">${shop.shopId}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${shop.shopId}">${shop.shopId}</option>
								</c:otherwise>
							</c:choose>
               			</c:forEach>
               		</select>
                
                </div>
            </div>
            <label class="control-label col-sm-1 col_padding">是否锁定：</label>
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
<%--         <div class="row" style="margin-bottom:10px;">
           	<% 
				request.setAttribute("sysSelect", DataConfig.getSysIdMap());
			%>
            <label class="control-label col-sm-1 col_padding" for="roleidInput">系统号：</label>
            <div class="col-sm-3">
            	<div class="input-group">
	              		<select name="filter.[v_=_0_,].score" value="${selectMap.score }" class="form-control">
						<c:forEach items="${sysSelect }" var="sys">
							<c:choose>
								<c:when test="${(not empty selectMap.score) && (selectMap.score eq sys.key)}">
									<option value="${sys.key}" selected="selected">${sys.key}:${sys.value}</option>
								</c:when>
								<c:otherwise>
			                    	<option value="${sys.key}">${sys.key}:${sys.value}</option>
								</c:otherwise>
							</c:choose>
	                    </c:forEach>
	                   </select>
                </div>
            </div>
        </div> --%>
        
        
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
                              	<th width="50">序号</th>
                              	<th width="80">操作</th>
								<th width="80">用户账号</th>
								<th width="80">用户名称</th>
								<th width="200">帐套列表</th>
								<th width="120">系统列表</th>
								<th width="80">商铺</th>
								<th width="80">用户类型</th>
								<th width="80">是否锁定</th>
								<th width="150">有效开始时间</th>
								<th width="150">有效结束时间</th>
								<th width="120">操作人</th>
								<th width="150">创建日期</th>
								<th width="150">最新修改时间</th>
								
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="b" varStatus="i">
							<tr userid="${b.userid }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a href='<c:url value="/admin/main/${b.userid}/update"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>
									<%-- <a name="power" href='<c:url value="/admin/role/${b.roleid}/power"/>'><span class="glyphicon glyphicon-link" style="font-size:16px" title="分配菜单"></span></a> --%>
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td>${b.username }</td>
								<td>${b.truename }</td>
								<td>${b.worklist }</td>
								<td>${b.sysidlist }</td>
								<td>${b.shopid }</td>
								<td hidden="true">${b.utype }</td>
								<td>${b.uname }</td>
								<td>
								<c:choose>
									<c:when test="${b.locked eq true }">
										<span class="glyphicon glyphicon-ok" style="font-size:16px" title="已锁定">
									</c:when>
									<c:otherwise>
										<span class="glyphicon glyphicon-remove" style="font-size:16px" title="未锁定">
									</c:otherwise>
								</c:choose>
								</td>
								<td>${b.starttime }</td>
								<td>${b.endtime }</td>
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
        	<div style="float:left"><portal:page url='<c:url value="/admin/main/list"/>'/></div>
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
		var userid = tr.attr("userid");
		$.ajax({
			url:'<c:url value="/admin/main/del.ajax" />',
			data:{userid:userid},
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