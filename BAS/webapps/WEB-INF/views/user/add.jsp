<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/bootstrap/bootstrap-multiselect/js/bootstrap-multiselect.js'/>"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/bootstrap-multiselect/css/bootstrap-multiselect.css'/>"
	type="text/css" />
<body>
	<strong>
		<h1 class="text-center" style="margin-top: 5%;">用户信息</h1>
	</strong>
	<c:if test="${not empty addSuccess }">
		<h3 style="text-align:center;color:red">添加成功！现在可以继续添加。</h3>
	</c:if>
	<div class="container" style="margin-top: 5%;">
		<form:form id="userForm" class="form-horizontal" method="post" modelAttribute="user">
			<form:hidden path="userid"/>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">用户名:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="username" />
				</div>
				<div>
					<form:errors style="color:red" path="username" />
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">真实姓名:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="truename" />
				</div>
				<div>
					<form:errors style="color:red" path="truename" />
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">密码:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" type="password" path="password" />
				</div>
				<div>
					<c:if test="${not empty user.salt}"><div style="color:red" >不填代表不修改</div></c:if>
					<form:errors style="color:red" path="password" />
				</div>
			</div>
			<c:if test="${empty user.salt}">
				<div class="form-group">
					<div style="text-align: right;" class="col-md-4">
						<label for="name">分配角色:</label>
					</div>
					<div class="col-md-4">
						<select id="roleStr" multiple="multiple">
						<c:forEach items="${roles}" var="role">
							<option value="${role.roleid }">${role.rolename }(${role.rotype })</option>
						</c:forEach>
						</select>
						<input name="roleStr" style="display:none">
					</div>
					<div>
						<c:if test="${noRole }">
							<div style="color:red" >请为此用户分配一个角色</div>
						</c:if>
						<c:if test="${empty roles}">
							<div style="color:red" >角色列表为空，请先去添加角色</div>
						</c:if>
					</div>
				</div>
			</c:if>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
						<label>分配商户:</label>
				</div>
				<div class="col-md-4">
					<form:select path="shopid" id="shopid" class="multiselect-search">
						<form:option value="">无</form:option>
						<c:forEach items="${shops}" var="shop">
							<form:option value="${shop.shopId }">${shop.shopname }</form:option>
						</c:forEach>
					</form:select>
					<%-- <form:input path="shopid" name="shopid" style="display:none" /> --%>
				</div>
			</div>
			<form:input path="salt" style="display:none" />
			    <div class="form-group">
                <div style="text-align: right;" class="col-md-4">
					<label for="starttimeInput">有效开始时间:  </label>
				</div>
				<div class="col-md-4">
                	<form:input path="starttime" cssClass="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true"/>
                </div>
            </div>
            <div class="form-group">
                <div style="text-align: right;" class="col-md-4">
					<label for="endtimeInput">有效结束时间:  </label>
				</div>
				<div class="col-md-4">
                     <form:input path="endtime" cssClass="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true"/>
                </div>
            </div>
            <div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>账套列表:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="worklist" />
				</div>
				<div>
					<form:errors style="color:red" path="worklist" />
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>系统列表:</label>
				</div>
				<div class="col-md-4">
					<form:input class="form-control" path="sysidlist" />
				</div>
				<div>
					<form:errors style="color:red" path="sysidlist" />
				</div>
			</div>
			<%-- <div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label >用户类型:</label>
				</div>
				<div class="col-md-4">
					<form:select class="form-control" path="utype">
						<form:option value="10">10</form:option>
						<form:option value="20">20</form:option>
					</form:select>
				</div>
			</div> --%>
			<form:hidden path="utype" value="20"/>
			
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="name">是否锁定:</label>
				</div>
				<div class="col-md-4">
					<%-- <form:input class="form-control" path="locked"/> --%>
					<form:select class="form-control" path="locked">
						<form:option value="0">否</form:option>
						<form:option value="1">是</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div style="padding:1px" class="col-md-5">
						<input id="submit" class="btn btn-primary col-md-12" type="submit" value="提交">
					</div><div class="col-md-2"></div>
					<div style="padding:1px" class="col-md-5">
						<a class="btn btn-default col-md-12" id="close" >取消</a>
					</div>
				</div>
			</div>
		</form:form>
	</div>	
</body>
<script type="text/javascript">
	$('#close').click(function(){
		$.MenuUtil.close();
	});
	$(document).ready(function() {
		$('#roleStr').multiselect({
			maxHeight: 290,
			buttonWidth: '100%',
			onDropdownHide: function (event) {
            	var li = $('ul.dropdown-menu li.active');
            	var roleStr = "";
            	for(var i=0; i<li.length;i++){
            		var value = $(li[i]).find('a label input').val();
            		var text = $(li[i]).find('a label').text().trim();
            		value = value+":"+text;
            		roleStr += value+";";
            	}
            	$('input[name=roleStr]').val(roleStr);
            	//var includeName = $('button.multiselect').attr("title");
            	//$('input[name=includeName]').val(includeName); 
        	 }
		});
		
		$('#shopid').multiselect({
			enableFiltering:true,
			maxHeight: 300,
			buttonWidth: '100%',
		})
	});
	
/* 	$('#submit').click(function(){
		var time =  $('#starttime').val();
		alert( parserDate(time));
		
		
	})
	
	var parserDate = function (date) {  
		    var t = Date.parse(date);  
		    if (!isNaN(t)) {  
		        return new Date(Date.parse(date.replace(/-/g, "/")));  
		    } else {  
		        return new Date();  
		    }  
		}; */ 
	
</script>
</html>