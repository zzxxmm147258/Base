<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/js/choseModal.js'/>"></script>
<body>
	<div class="cintainer" style="height:510px">
		<form:form class="form-horizontal" method="post"
			modelAttribute="roleMenuKey">
			<div class="form-group">
				<div class="col-md-3"></div>
				<div class="col-md-3">
					<strong>
						<h2 class="text-center" style="margin-top: 5%;">
							角色 <span class="glyphicon glyphicon-transfer"
								style="color: black;">菜单
						</h2>
					</strong>
					<c:if test="${error != null && error eq 1 }">
						<div class="text-center" style="color:red">此关系可能已存在！</div>
					</c:if>
					<c:if test="${not empty addSuccess }">
						<h3 style="text-align:center;color:red">添加成功！现在可以继续添加。</h3>
					</c:if>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-3" style="text-align: right;">
					<label for="name">角色ID:</label>
				</div>
				<div class="col-md-3">
				<form:select class="form-control" path="roleid" id="roleid">
						<option value="">角色ID,角色名称
						<c:forEach items="${roles}" var="role">
							<form:option value="${role.roleid }" name="${role.rolename }">${role.roleid }&nbsp&nbsp,&nbsp&nbsp${role.rolename }</form:option>
						</c:forEach>
				</form:select>
				</div>
				<div>
					<form:errors style="color:red" path="roleid" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-3" style="text-align: right;">
					<label for="name">角色名称:</label>
				</div>
				<div class="col-md-3">
					<form:input id="roleidName" class="form-control" path="rolename" readonly="true"/>
				</div>
			</div>
			<div class="form-group">

				<div class="col-md-3" style="text-align: right;">
					<label for="name">菜单ID:</label>
				</div>
				<div class="col-md-3">
				<div class="input-group">
					<form:input id="menuid" class="form-control" path="menuid" readonly="true"/>
					<div class="input-group-addon" data-toggle="modal" data-target="#menuModal"><a href="javascript:;">选择</a></div>
				</div>
				</div>
				<div>
					<form:errors style="color:red" path="menuid" />
				</div>
			</div>
			<div class="form-group">

				<div class="col-md-3" style="text-align: right;">
					<label>菜单名称:</label>
				</div>
				<div class="col-md-3">
					<form:input id="menuname" class="form-control" path="menuname" readonly="true"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-3" style="text-align: right;">
					<label for="name">是否启用:</label>
				</div>
				<div class="col-md-3">
					<form:select class="form-control" path="availabe">
						<form:option value="1">是</form:option>
						<form:option value="0">否</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-3"></div>
				<div class="col-md-3">
				<div style="padding:1px" class="col-md-5">
					<input id="submit" class="btn btn-primary col-md-12" type="submit"
						class="btn btn-default" value="提交">
				</div><div class="col-md-2"></div>
				<div style="padding:1px" class="col-md-5">
					<a class="btn btn-default col-md-12" id="close">取消</a>
				</div>
				</div>
			</div>
		</form:form>
	</div>
	
	<!-- menu模态框（Modal） -->
		<div class="modal fade" name="menuModal" id="menuModal" tabindex="-1" role="dialog"  aria-labelledby="menuModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="menuModalLabel">
		               	选择用户
		            </h4>
		         </div>
		         <div class="modal-body" style="width:590px; height:300px;">
		         	<label class="col-sm-2 control-label" > 菜单ID:</label>
		         	<input type="text" name="menuid" class="form-control" maxlength="100" style="width:150px;float: left" />
		         	<label class="col-sm-2 control-label" > 菜单名:</label>
		         	<input type="text" name="menuname" class="form-control" maxlength="100" style="width:150px;float: left" />
		         	<input type="button"   class="form-control" name="search" style="width:50px;" value="查询"><br>
		            <div style="height:240px;overflow:scroll">
		            <table class="table table-bordered">
		            	<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
								<th>ID</th>
								<th>名称</th>
							</tr>
						</thead>
						<tbody name="menu">
							<c:forEach items="${menus}" var="menu">
								<tr>
									<td><input type="radio" name="radio" r_menuname="${menu.menuname }" r_menuid="${menu.menuid }">${menu.menuid }</td>
									<td>${menu.menuname }</td>
								</tr>
							</c:forEach>
						</tbody>
		            </table>
		            </div>
		         </div>
		         <div class="modal-footer">
		            <button class="btn btn-default" data-dismiss="modal">关闭</button>
		            <button class="btn btn-primary" data-dismiss="modal" name="go">确认</button>
		         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	
</body>
<script type="text/javascript">
	var updateSuccess = '${updateSuccess}';
	if(updateSuccess != null && updateSuccess != ''){
		$('#close').attr("closeNow","true");//关闭页面
	}
	
	$('#close').click(function(){
		$.MenuUtil.close();
	});
	
	var inputArray = new Array("menuid","menuname"); 	//输入框的path
	var fuzzyArray = inputArray;						//模糊查询条件的字段
			
	//需要传入的参数：表名，list.ajax链接地址，输入框需要的字段，模糊查询条件
	choseModal("menu",'<c:url value="/admin/menu/list.ajax"/>',inputArray,fuzzyArray);
	
	 
	function getHtml(tableName,json){        //配合choseModal.js使用
		var html = "";
		if(tableName=="menu"){
			for(var i=0;i<json.length;i++){    //拼radio的属性时候记得加 r_ 前缀
				html += '<tr><td><input type="radio" name="radio" r_menuname="'+json[i].menuname+'" r_menuid="'+json[i].menuid+'">'+json[i].menuid+'</td><td>'+json[i].menuname+'</td></tr>';
			}
		}
		return html;
	}
	
	
	$('select').change(function() {
		var id = this.getAttribute('id');
		var name = $('#' + id + ' option:selected').attr('name');
		idName = id + 'Name';
		$('#' + idName).val(name);
	})
</script>
</html>
