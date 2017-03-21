<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/js/choseModal.js'/>"></script>
<body>
	<div class="cintainer">
		<form:form class="form-horizontal" method="post"
			modelAttribute="roleResourceKey">
			<div class="form-group">
				<div class="col-md-3"></div>
				<div class="col-md-3">
					<strong>
						<h2 class="text-center" style="margin-top: 5%;">
							用户<span class="glyphicon glyphicon-transfer"
								style="color: black;">资源
						</h2>
					</strong>
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
					<label for="name">资源ID:</label>
				</div>
				<div class="col-md-3">
				<div class="input-group">
					<form:input id="resourceid" class="form-control" path="resourceid" readonly="true"/>
					<div class="input-group-addon" data-toggle="modal" data-target="#resourceModal"><a href="javascript:;">选择</a></div>
				</div>
				</div>
				<div>
					<form:errors style="color:red" path="resourceid" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-3" style="text-align: right;">
					<label for="name">资源名称:</label>
				</div>
				<div class="col-md-3">
					<form:input id="resname" class="form-control" path="resname" readonly="true"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-3" style="text-align: right;">
					<label for="name">操作ID:</label>
				</div>
				<div class="col-md-3">
				<div class="input-group">
					<form:input id="operaid" class="form-control" path="operaid" readonly="true"/>
					<div class="input-group-addon" data-toggle="modal" data-target="#operationcodeModal"><a href="javascript:;">选择</a></div>
				</div>
				</div>
				<div>
					<form:errors style="color:red" path="operaid" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-3" style="text-align: right;">
					<label for="name">操作名称:</label>
				</div>
				<div class="col-md-3">
					<form:input id="operaname" class="form-control" path="operaname" readonly="true"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-3" style="text-align: right;">
					<label for="name">系统ID:</label>
				</div>
				<div class="col-md-3">
					<% 
						request.setAttribute("sysSelect", DataConfig.getSysIdMap());
					%>
					<form:select class="form-control" path="sysid">
						<c:forEach items="${sysSelect}" var="sys">
							<form:option value="${sys.key}">${sys.key}:${sys.value}</form:option>
						</c:forEach>
					</form:select>
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

<!-- resource模态框（Modal） -->
		<div class="modal fade" name="resourceModal" id="resourceModal" tabindex="-1" role="dialog"  aria-labelledby="resourceModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="resourceModalLabel">
		               	选择资源
		            </h4>
		         </div>
		         <div class="modal-body" style="width:590px; height:300px;">
		         	<label class="col-sm-2 control-label" > 资源ID:</label>
		         	<input type="text" name="resourceid" class="form-control" maxlength="100" style="width:150px;float: left" />
		         	<label class="col-sm-2 control-label" > 资源名称:</label>
		         	<input type="text" name="resname" class="form-control" maxlength="100" style="width:150px;float: left" />
		         	<input type="button"   class="form-control" name="search" style="width:50px;" value="查询"><br>
		            <div style="height:240px;overflow:scroll">
		            <table class="table table-bordered">
		            	<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
								<th>ID</th>
								<th>名称</th>
							</tr>
						</thead>
						<tbody name="resource">
							<c:forEach items="${resources}" var="resource">
								<tr>
									<td><input type="radio" name="radio" r_resname="${resource.resname }" r_resourceid="${resource.resourceid }">${resource.resourceid }</td>
									<td>${resource.resname }</td>
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
	
	
		<!-- operationcode模态框（Modal） -->
		<div class="modal fade" name="operationcodeModal" id="operationcodeModal" tabindex="-1" role="dialog"  aria-labelledby="operationcodeModalLabel" aria-hidden="true">
		   <div class="modal-dialog">
		      <div class="modal-content">
		         <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                  &times;
		            </button>
		            <h4 class="modal-title" id="operationcodeModalLabel">
		               	选择操作
		            </h4>
		         </div>
		         <div class="modal-body" style="width:590px; height:300px;">
		         	<label class="col-sm-2 control-label" > 操作ID:</label>
		         	<input type="text" name="operaid" class="form-control" maxlength="100" style="width:150px;float: left" />
		         	<label class="col-sm-2 control-label" > 操作名称:</label>
		         	<input type="text" name="operaname" class="form-control" maxlength="100" style="width:150px;float: left" />
		         	<input type="button"   class="form-control" name="search" style="width:50px;" value="查询"><br>
		            <div style="height:240px;overflow:scroll">
		            <table class="table table-bordered">
		            	<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
								<th>ID</th>
								<th>名称</th>
							</tr>
						</thead>
						<tbody name="operationcode">
							<c:forEach items="${operationcodes}" var="operationcode">
								<tr>
									<td><input type="radio" name="radio" r_operaname="${operationcode.operaname }" r_operaid="${operationcode.operaid }">${operationcode.operaid }</td>
									<td>${operationcode.operaname }</td>
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
	
	var reInputArray = new Array("resourceid","resname"); 	//输入框的path
	var opInputArray = new Array("operaid","operaname");
	var reFuzzyArray = reInputArray;						//模糊查询条件的字段
	var opFuzzyArray = opInputArray;
			
	//需要传入的参数：表名，list.ajax链接地址，输入框需要的字段，模糊查询条件
	choseModal("resource",'<c:url value="/admin/resource/list.ajax"/>',reInputArray,reFuzzyArray);
	choseModal("operationcode",'<c:url value="/admin/operationcode/list.ajax"/>',opInputArray,opFuzzyArray);
	
	 
	function getHtml(tableName,json){        //配合choseModal.js使用
		var html = "";
		if(tableName=="resource"){
			for(var i=0;i<json.length;i++){    //拼radio的属性时候记得加 r_ 前缀
				html += '<tr><td><input type="radio" name="radio" r_resname="'+json[i].resname+'" r_resourceid="'+json[i].resourceid+'">'+json[i].resourceid+'</td><td>'+json[i].resname+'</td></tr>';
			}
		}else if(tableName=="operationcode"){
			for(var i=0;i<json.length;i++){  
				html += '<tr><td><input type="radio" name="radio" r_operaname="'+json[i].operaname+'" r_operaid="'+json[i].operaid+'">'+json[i].operaid+'</td><td>'+json[i].operaname+'</td></tr>';
			}
		}
		return html;
	}

	
	$('select').change(function() {     //角色的select
		var id = this.getAttribute('id');
		var name = $('#' + id + ' option:selected').attr('name');
		idName = id + 'Name';
		$('#' + idName).val(name);
	})
</script>
</html>