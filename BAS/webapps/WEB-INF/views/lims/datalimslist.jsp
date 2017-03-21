<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<body>
	<div class="container" style="height:650px">
	<div class="col-sm-5">
		<div style="overflow-x:auto;overflow-y:auto;width:100%;height:600px">
			<table class="table table-hover table-bordered">
				<thead bgcolor="#F0F8FF"  class="text-center">
					<tr>
					    <td>操作
							<a href='<c:url value="/main/datalimm/new"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
								data-toggle="tooltip" data-placement="right"
								title="添加数据">添加</a>
						</td>
						<td>权限编码</td>
						<td>权限名称</td>
						<!-- <td>标记</td>
						<td>修改人</td>
						<td>修改日期</td>
						<td>启用标记</td>
						<td>序号</td> -->
						
					</tr>
				</thead>
				<tbody name="dictid">
					<c:forEach items="${datalimm }" var="datalimm">
						<tr datalimmid="${datalimm.limid}" onclick="getChild(this)">
						   <td class="text-center">
								<a href='<c:url value="/main/datalimm/${datalimm.limid}/update"/>'><span name="edit" class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"></span></a>&nbsp&nbsp
							    <a href='<c:url value="/main/datalimm/${datalimm.limid}/del"/>'><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
							</td>
							<td name="datalimmid">${datalimm.limid}</td>
							<td name="limnm">${datalimm.limnm }</td>
							<%-- <td >${datalimm.flags }</td>
							<td >${datalimm.modifier }</td>
							<td><fmt:formatDate value="${datalimm.modifydate}" pattern="yyyy-MM-dd"/> </td>
							<td >${datalimm.startflags }</td>
							<td >${datalimm.ord }</td> --%>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
		</div>
		</div>

		<div class="col-sm-7" >
			<ul class="nav nav-tabs">
				<li class="active"><a hasLode="false" url='<c:url value="/main/datalimm/getDatalims"/>' onclick="getChild(this)" href="#datalims" name="datalims" data-toggle="tab">按岗位定义权限</a></li>
				<li ><a hasLode="false" url='<c:url value="/main/datalimm/getDatalimop"/>' onclick="getChild(this)" href="#datalimop" name="datalimop" data-toggle="tab">权限操作</a></li>
				<li ><a hasLode="false" url='<c:url value="/main/datalimm/getBusheetlim"/>' onclick="getChild(this)" href="#busheetlim" name="busheetlim" data-toggle="tab">单据权限关系</a></li>
				<li ><a hasLode="false" url='<c:url value="/main/datalimm/getDatalimflds"/>' onclick="getChild(this)" href="#datalimflds" name="datalimflds" data-toggle="tab">权限字段</a></li>
			</ul>
			<div class="tab-content">
			   <!-- 按岗位定义权限菜单显示数据 -->
				<div class="tab-pane fade in active" id="datalims">
			   <div style="overflow:auto;width:100%;height:600px">
					<table class="table table-hover table-bordered table-responsive">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
							    <td>操作
							     <a href='<c:url value="/main/datalims/new"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
								  data-toggle="tooltip" data-placement="right"
								  title="添加数据">添加</a>
					  	        </td>
						        <td>操作编号</td>
						        <td>用户编码</td>
						        <td>序号</td>
						        <td>字段名1</td>
						        <td>范围1</td>
						        <td>字段名2</td>
						        <td>范围2</td>
						        <td>字段名3</td>
						        <td>范围3</td>
						        <td>启用日期</td>
						        <td>终止日期</td>
						        <td>修改日期</td>
						       
							</tr>
						</thead>
						<tbody name="datalims">

						</tbody>
					</table>
				</div>
				</div>
				 <!-- 权限操作菜单显示数据 -->
				<div class="tab-pane fade" id="datalimop">
			 <div style="overflow:auto;width:100%;height:600px">
					<table class="table table-hover table-bordered table-responsive">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
							     <td>操作
							       <a href='<c:url value="/main/datalimop/new"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
								   data-toggle="tooltip" data-placement="right"
								   title="添加数据">添加</a>
						         </td>
								
						        <td>操作编号</td>
						        <td>操作名称</td>
						        
							</tr>
						</thead>
						<tbody name="datalimop">

						</tbody>
					</table>
				</div>
				</div>
				 <!-- 单据权限关系菜单显示数据 -->
				<div class="tab-pane fade" id="busheetlim">
				 <div style="overflow:auto;width:100%;height:600px">
					<table class="table table-hover table-bordered table-responsive">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
							     <td>操作
							     <a href='<c:url value="/main/busheetlim/new"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
								  data-toggle="tooltip" data-placement="right"
								  title="添加数据">添加</a>
						         </td>
						      
								<td>资源号</td>
						        <td>序号</td>
						        <td>资源名称</td>
							</tr>
						</thead>
						<tbody name="busheetlim">

						</tbody>
					</table>
				</div>
				</div>
				 <!-- 权限字段菜单显示数据 -->
				 <div class="tab-pane fade" id="datalimflds">
				 <div style="overflow:auto;width:100%;height:600px">
					<table class="table table-hover table-bordered table-responsive">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
							     <td>操作
							      <a href='<c:url value="/main/datalimflds/new"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
								  data-toggle="tooltip" data-placement="right"
								  title="添加数据">添加</a>
						         </td>
								
						        <td>字段名</td>
						        <td>字段显示名称</td>
						        <td>类型</td>
						        <td>序号</td>
						        <td>选项</td>
						        <td>不做权限检查的值</td>
							</tr>
						</thead>
						<tbody name="datalimflds">

						</tbody>
					</table>
				</div>
				</div>
				
			</div>
		</div>
	
	</div>
	
</body>
<script type="text/javascript">


var parent = "";       //父级ID
var table = $("div.tab-content .active").attr("id");   //当前显示的标签的table的id属性
var url = $(".nav-tabs .active a").attr("url"); 	   //当前显示的标签的url

function getChild(obj){
	if($(obj).attr("href")==$(".nav-tabs .active a").attr("href"))return;  //如果点击的是当前显示的标签则不做任何事
	var uu = $(obj).attr("url"); 
	if(uu){     //点的是标签
		url = uu;
		table = $(obj).attr("name");    
	}else{      //点的是父级
		url = $(".nav-tabs .active a").attr("url");   //当前显示的标签的url
		parent = $(obj).attr("datalimmid");
		$(obj).attr("style","background:#ECF0EA").siblings().attr("style",""); //修改tr背景色
		$(".nav-tabs li a").attr("hasLode","false");
		$(".nav-tabs .active a").attr("hasLode","true"); 
	}
	/* alert("父级ID："+parent+"  链接："+url+"   当前标签ID："+table); */
	if(!uu || $(obj).attr("hasLode")=="false"){
		$.ajax({
				url:url,
				data:{datalimmid:parent},
				type:'post',
				success:function(data){
					choseCreate(data);
					if(uu){    //点击的是标签
						$(obj).attr("hasLode","true");  //改变此标签的属性
					}
				},
				error:function(){
					//alert("提交失败");
				}
			})
	}
}


	function choseCreate(data){
		var json = eval(data);
		var html = "";
		if(json.length>0){
			for(var i=0;i<json.length;i++){
			switch (table){
			case "datalims":
				html += '<tr datalimmid='+json[i].limid+' opid='+json[i].opid+'>'
				           +'<td class="text-center">'
				           +'<a href=\'<c:url value="/main/datalims/'+json[i].limid+'/'+json[i].opid+'/'+json[i].utype+'/'+json[i].ucode+'/'+json[i].idx+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
			               +'<a href=\'<c:url value="/main/datalims/'+json[i].limid+'/'+json[i].opid+'/'+json[i].utype+'/'+json[i].ucode+'/'+json[i].idx+'/del"/>\' data-toggle="tooltip" title="删除"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px"/></a>'
				           +'</td>'
							
							+'<td>'+json[i].opid+'</td>'
							
							+'<td>'+json[i].ucode+'</td>'
							+'<td>'+json[i].idx+'</td>'
							+'<td>'+json[i].fldname01+'</td>'
							+'<td>'+json[i].values01+'</td>'
							+'<td>'+json[i].fldname02+'</td>'
							+'<td>'+json[i].values02+'</td>'
							+'<td>'+json[i].fldname03+'</td>'
							+'<td>'+json[i].values03+'</td>'
							+'<td>'+json[i].startdate+'</td>'
							+'<td>'+json[i].enddate+'</td>'
							+'<td>'+json[i].modifydate+'</td>'
   		                    +'</tr>';
						break;			
			case "datalimop":
				html += '<tr datalimmid='+json[i].limid+' opid='+json[i].opid+'>'
				             +'<td class="text-center">'
		                     +'<a href=\'<c:url value="/main/datalimop/'+json[i].limid+'/'+json[i].opid+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
	                         +'<a href=\'<c:url value="/main/datalimop/'+json[i].limid+'/'+json[i].opid+'/del"/>\' data-toggle="tooltip" title="删除"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px"/></a>'
		                     +'</td>'
							
							+'<td>'+json[i].opid+'</td>'
							+'<td>'+json[i].opnm+'</td>'
						+'</tr>';
						break;
			case "busheetlim":
				html += '<tr datalimmid='+json[i].limid+' idx='+json[i].idx+'>'
				            +'<td class="text-center">'
		                    +'<a href=\'<c:url value="/main/busheetlim/'+json[i].limid+'/'+json[i].resourceid+'/'+json[i].idx+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
	                        +'<a href=\'<c:url value="/main/busheetlim/'+json[i].limid+'/'+json[i].resourceid+'/'+json[i].idx+'/del"/>\' data-toggle="tooltip" title="删除"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px"/></a>'
		                    +'</td>'
						
							+'<td>'+json[i].resourceid+'</td>'
							+'<td>'+json[i].idx+'</td>'
							+'<td>'+json[i].resname+'</td>'
						+'</tr>';
						break;
			case "datalimflds":
				html += '<tr datalimmid='+json[i].limid+' opid='+json[i].idx+'>'
				            +'<td class="text-center">'
                            +'<a href=\'<c:url value="/main/datalimflds/'+json[i].limid+'/'+json[i].fldname+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
                            +'<a href=\'<c:url value="/main/datalimflds/'+json[i].limid+'/'+json[i].fldname+'/del"/>\' data-toggle="tooltip" title="删除"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px"/></a>'
                            +'</td>'
							
							+'<td>'+json[i].fldname+'</td>'
							+'<td>'+json[i].cfldname+'</td>'
							+'<td>'+json[i].fldtype+'</td>'
							+'<td>'+json[i].idx+'</td>'
							+'<td>'+json[i].fldopts+'</td>'
							+'<td>'+json[i].unlimckvals+'</td>'
						+'</tr>';
				break;
			default:
				break;
				}
			}
		}
		$('tbody[name='+table+']').html(html);
	}

	
	function renderTime(date){ 
		var da = new Date(parseInt(date.replace("/Date(","").replace(")/","").split("+")[0])); 
		return da.getFullYear()+"-"+ (da.getMonth()+1)+"-" +da.getDate()+" " +da.getHours()+":"+da.getSeconds()+":"+da.getMinutes(); 
		} 
	
	
	/* //遍历子表并显示
	function create(data){
		var json = eval(data);
		var html;
		for(var i=0;i<json.length;i++){
			html += '<tr code='+json[i].code+'><td name="code">'+json[i].code+'</td><td name="cname">'+json[i].cname+'</td>';
			html += infoControlTd +'</tr>';
		}
		html += addInfo;
		$('tbody[name=code]').html(html);
	} */
	
	
	//点击主表，出现相应子表
/* 	$('tbody[name=dictid]').on('click','tr',function(){
		var dictid = this.getAttribute('dictid');
		if(null != dictid){
		$.ajax({
			url:'<c:url value="/admin/dictdef/getChild"/>',
			data:{dictid:dictid},
			type:'post',
			success:function(data){
				$('tbody[name=code]').attr("dictid",dictid);
				create(data,dictid);
			},
			error:function(){
				alert("提交失败");
			}
		})
		}
	}) */
	

	
	
/* 	//主表添加时的提交按钮
	$("#dictid").on("click",'input[name=addSubmit]',function(){
		var tr = this.parentNode.parentNode;
		var dictid = $('input[name="dictid"]').val();
		var cname = $('input[name="cname"]').val();
		if(dictid == null || dictid == ''){
			alert("dictid不能为空！");
		}else{
			$.ajax({
				url:'<c:url value="/admin/dictdef/add" />',
				data:{dictid:dictid,cname:cname},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data > 0){
						tr.remove();
						var html = '<tr dictid='+dictid+' onclick="getChild(this)"><td name="dictid">'+dictid+'</td><td name="cname">'+cname+'</td>';
						html += controlDef + '</tr>' + addDef;
						$('#dictid').append(html);
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
		var dictid = tr.parentNode.getAttribute('dictid');   //获取dictid
		var code = $('input[name="code"]').val();
		var cname = $('input[name="cname"]').val();
		if(code == null || code == ''){
			alert("code不能为空！");
		}else{
		$.ajax({
			url:'<c:url value="/admin/dictinfo/add" />',
			data:{dictid:dictid,code:code,cname:cname},
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
		var dictid = tr.getAttribute('dictid');
		$.ajax({
			url:'<c:url value="/admin/dictdef/del" />',
			data:{dictid:dictid},
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