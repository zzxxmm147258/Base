<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
	<div class="container">
		<div class="col-md-6">
			<table class="table table-hover table-bordered">
				<thead bgcolor="#F0F8FF"  class="text-center">
					<tr>
						<td>权限编码</td>
						<td>权限名称</td>
						<!-- <td>修改人</td>
						<td>修改日期</td>
						<td>启用标记</td>
						<td>序号</td> -->
						<td>操作</td>
					</tr>
				</thead>
				<tbody name="datalimmid">
					<c:forEach items="${datalimm }" var="datalimm">
						<tr datalimmid="${datalimm.limid}">
							<td name="datalimmid">${datalimm.limid}</td>
							<td name="limnm">${datalimm.limnm }</td>
							<%-- <td name="modifier">${datalimm.modifier }</td>
							<td name="modifydate"><fmt:formatDate value="${datalimm.modifydate}" pattern="yyyy-MM-dd"/> </td>
							<td name="startflags">${datalimm.startflags }</td>
							<td name="ord">${datalimm.ord }</td> --%>
							<td class="text-center">
							<a name="edit"><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>
							<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
							</td>
						</tr>
					</c:forEach>
					<tr name="dictidAdd">
						<td	name="addTd" colspan=3 class="text-center">
						<a><span class="glyphicon glyphicon-plus" /></a></td>
					</tr>
				</tbody>
			</table>
		</div>
		 <div class="col-md-6">
		<table class="table table-hover table-bordered">
			<thead bgcolor="#F0F8FF"  class="text-center">
					<tr>
						<td>权限编码</td>
						<td>操作编号</td>
						<td>用户类型</td>
						<td>用户编码</td>
						<td>序号</td>
						<td>组</td>
						<td>启用日期</td>
						<td>终止日期</td>
						<td>修改日期</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody name="limid">
                  
				</tbody>
		</table>
		</div> 
	</div>
	
	
</body>
<script type="text/javascript" src='<c:url value="/resources/js/listInput.js"/>'></script>
<script type="text/javascript">

	//操作td	
	var defControlTd = '<td class="text-center">'
	+'<a name="edit"><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>'
	+'<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>'
	+'</td>';
	//添加的提交按钮
	var addSubmitTd = '<td style="padding:1px"><input name="addSubmit" class="btn btn-primary" style="width:100px;height:27px" value="提交"></td>';
	//编辑的提交按钮
	var editSubmitTd = '<td style="padding:1px"><input name="editSubmit" class="btn btn-primary" style="width:100px;height:27px" value="提交"></td>';
	//参与增加和编辑事件的字段
	var defAttr = new Array("datalimmid","limnm");
	
	//编辑，添加，删除的后台地址
	var defEditSubmitUrl = '<c:url value="/main/datalimm/update" />';
	var defAddSubmitUrl = '<c:url value="/main/datalimm/add" />';
	var defRemoveUrl = '<c:url value="/main/datalimm/del" />';
	
	
		
		
	var infoAttr= new Array("datalimsid","datalimsid");	
	var infoEditSubmitUrl = '<c:url value="/main/datalims/update" />';
	var infoAddSubmitUrl = '<c:url value="/main/datalims/add" />';
	var infoRemoveUrl = '<c:url value="/main/datalims/del" />';
	var infoControlTd = '<td class="text-center">'
		+'<a name="edit"><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>'
		+'<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>'
		+'</td>';
	var defRalation = new Array("parent","datalimsid");
	var infoRalation = new Array("child","datalimmid"); //代表：存在关系，是子表，关联字段是"datalimmid"
	listInput("datalimmid",defAttr,defEditSubmitUrl,defAddSubmitUrl,defRemoveUrl,defControlTd,null,null,null,defRalation);
	listInput("datalimsid",infoAttr,infoEditSubmitUrl,infoAddSubmitUrl,infoRemoveUrl,infoControlTd,null,null,null,infoRalation);
	
	
	
	
	var addInfo = '<tr name="codeAdd"><td name="addTd" colspan=6 class="text-center"><a><span class="glyphicon glyphicon-plus"/></a></td></tr>';
	//点击主表，出现相应子表
	$('tbody[name=datalimmid]').on('click','tr',function(){
		var datalimmid = this.getAttribute('datalimmid');
		if(null != datalimmid){
		$.ajax({
			url:'<c:url value="/main/datalimm/getChild"/>',
			data:{datalimmid:datalimmid},
			type:'post',
			success:function(data){
				$('tbody[name=code]').attr("datalimmid",datalimmid);
				create(data,datalimmid);
			},
			error:function(){
				alert("提交失败");
			}
		})
		}
	})
	
	//遍历子表并显示
	function create(data,datalimmid){
		var json = eval(data);
		var html;
		for(var i=0;i<json.length;i++){
			html += '<tr datalimsid='+json[i].idx+'><td name="limid">'+json[i].limid+'</td><td name="opid">'+json[i].opid+'</td><td name="utype">'+json[i].utype+'</td>';
			html += '<td name="ucode">'+json[i].ucode+'</td><td name="idx">'+json[i].idx+'</td><td name="limgrp">'+json[i].limgrp+'</td>';
			html += '<td name="startdate">'+json[i].startdate+'</td><td name="enddate">'+json[i].enddate+'</td><td name="modifydate">'+json[i].modifydate+'</td>';
			
			html += infoControlTd +'</tr>';
		}
		html += addInfo;
		$('tbody[name=limid]').html(html);
	}

	
	
/* 	//主表添加时的提交按钮
	$("#datalimmid").on("click",'input[name=addSubmit]',function(){
		var tr = this.parentNode.parentNode;
		var datalimmid = $('input[name="datalimmid"]').val();
		var cname = $('input[name="cname"]').val();
		if(datalimmid == null || datalimmid == ''){
			alert("dictid不能为空！");
		}else{
			$.ajax({
				url:'<c:url value="/admin/dictdef/add" />',
				data:{datalimmid:datalimmid,cname:cname},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data > 0){
						tr.remove();
						var html = '<tr datalimmid='+datalimmid+' onclick="getChild(this)"><td name="datalimmid">'+datalimmid+'</td><td name="cname">'+cname+'</td>';
						html += controlDef + '</tr>' + addDef;
						$('#datalimmid').append(html);
					}else{
						alert("添加失败！");
					}
				},
				error:function(){
					alert("提交失败！");
				}
			})
		}
	}) */
	/* //子表添加时的提交按钮
	$("#code").on("click",'input[name=addSubmit]',function(){
		var tr = this.parentNode.parentNode;
		var datalimmid = tr.parentNode.getAttribute('datalimmid');   //获取dictid
		var code = $('input[name="code"]').val();
		var cname = $('input[name="cname"]').val();
		if(code == null || code == ''){
			alert("code不能为空！");
		}else{
		$.ajax({
			url:'<c:url value="/admin/dictinfo/add" />',
			data:{datalimmid:datalimmid,code:code,cname:cname},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data > 0){
				tr.remove();
				var html = '<tr code='+code+'><td name="code">'+code+'</td><td name="cname">'+cname+'</td>';
				html += controlInfo + '</tr>' + addInfo;
				$('#code').append(html);
				}else{
					alert("添加失败！");
				}
			},
			error:function(){
				alert("提交失败！");
			}
		})
		}
	}) */
	
/* 	//主表的删除按钮事件
 	function removeDef(obj){
	if(confirm("是否确定要删除？")){
		var tr = obj.parentNode.parentNode.parentNode;
		var datalimmid = tr.getAttribute('datalimmid');
		$.ajax({
			url:'<c:url value="/admin/dictdef/del" />',
			data:{datalimmid:datalimmid},
			type:'post',
			success:function(data){
				if(data > 0){
				tr.remove();
				$('#code').html('');   // 删除页面中的子表数据
				}
			},
			error:function(){
				alert("提交失败");
			}
		})
	}
	}; */
/* 
	//子表的删除按钮事件
	function removeInfo(obj){
		if(confirm("是否确定要删除？")){
		var tr = obj.parentNode.parentNode.parentNode;
		var code = tr.getAttribute('code');
 		$.ajax({
			url:'<c:url value="/admin/dictinfo/del" />',
			data:{code:code},
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
	};
	} */
</script>
</html>