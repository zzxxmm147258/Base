<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/js/choseModal.js'/>"></script>
<body>
	<div class="cintainer" style="height:510px">
		<form:form class="form-horizontal" method="post"
			modelAttribute="roleUsersKey">
			<div class="form-group">
				<div class="col-md-3"></div>
				<div class="col-md-3">
					<strong>
						<h2 class="text-center" style="margin-top: 5%;">
							用户<span class="glyphicon glyphicon-transfer"
								style="color: black;">角色 
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
					<label for="name">用户ID:</label>
				</div>
				<div class="col-md-3">
				<div class="input-group">
					<form:input id="userid" class="form-control" path="userid" readonly="true"/>
					<div class="input-group-addon" data-toggle="modal" data-target="#userModal"><a href="javascript:;">选择</a></div>
				</div>
				</div>
				<div>
					<form:errors style="color:red" path="userid" />
				</div>
			</div>
			<div class="form-group">

				<div class="col-md-3" style="text-align: right;">
					<label for="name">用户名称:</label>
				</div>
				<div class="col-md-3">
					<form:input id="username" class="form-control" path="username" readonly="true"/>
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
					<a class="btn btn-default col-md-12" id="close"/>取消</a>
				</div>
				</div>
			</div>
		</form:form>
	</div>

<!-- user模态框（Modal） -->
		<div class="modal fade" name="userModal" id="userModal" tabindex="-1" role="dialog"  aria-labelledby="userModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="userModalLabel">
		               	选择用户
		            </h4>
		         </div>
		         <div class="modal-body" style="width:590px; height:300px;">
		         	<!-- <label class="col-sm-2 control-label" > 用户ID </label>
		         	<input type="text" name="userid" class="form-control" maxlength="100" style="width:150px;float: left" /> -->
		         	<label class="col-sm-2 control-label" > 用户名  </label>
		         	<input type="text" name="username" class="form-control" maxlength="100" style="width:150px;float: left" />
		         	<input type="button"   class="form-control" name="search" style="width:50px;" value="查询"><br>
		         	<div style="height:240px;overflow:scroll">
		            <table class="table table-bordered">
		            	<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
								<th>ID</th>
								<th>名称</th>
							</tr>
						</thead>
						<tbody name="user">
							<c:forEach items="${users}" var="user">
								<tr>
									<td><input type="radio" name="radio" r_username="${user.username }" r_userid="${user.userid }">${user.userid }</td>
									<td>${user.username }</td>
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
	
 	var inputArray = new Array("userid","username"); 	//输入框的path
	var fuzzyArray = inputArray;						//模糊查询条件的字段
			
	//需要传入的参数：表名，list.ajax链接地址，输入框需要的字段，模糊查询条件
	choseModal("user",'<c:url value="/admin/main/list.ajax"/>',inputArray,fuzzyArray);
	
	 
	function getHtml(tableName,json){        //配合choseModal.js使用
		var html = "";
		if(tableName=="user"){
			for(var i=0;i<json.length;i++){    //拼radio的属性时候记得加 r_ 前缀
				html += '<tr><td><input type="radio" name="radio" r_username="'+json[i].username+'" r_userid="'+json[i].userid+'">'+json[i].userid+'</td><td>'+json[i].username+'</td></tr>';
			}
		}
		return html;
	} 
	
	$('select').change(function() {  //角色ID的select改变事件
		var id = this.getAttribute('id');
		var name = $('#' + id + ' option:selected').attr('name');
		idName = id + 'Name';
		$('#' + idName).val(name);
	})
	
	
</script>
</html>