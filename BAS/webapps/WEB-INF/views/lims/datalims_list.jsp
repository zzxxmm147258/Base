<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
	<div class="container" style="padding:0;margin:0;height：100%;width:100%;">
		<div class="col-sm-4" style="padding:0 2px;">
		<ul class="nav nav-tabs">
				<li ><a hasLode="false">数据权限设置</a></li>
	   </ul>
		<div style="overflow:auto;width:100%;" class="scrollWH" scrollH="90">
			<table class="table table-hover table-bordered" name="parent">
				<thead bgcolor="#F0F8FF"  class="text-center">
					<tr>
						 <td>操作
							<a href='<c:url value="/admin/datalimm/new"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
								data-toggle="tooltip" data-placement="right"
								title="添加数据">添加</a>
						</td>
						<td>权限编码</td>
						<td>权限名称</td>
					</tr>
				</thead>
				<tbody name="dictid">
					<c:forEach items="${datalimm }" var="datalimm">
						<tr datalimmid="${datalimm.limid}" onclick="getChild(this)">
						   <td class="text-center">
								<a href='<c:url value="/admin/datalimm/${datalimm.limid}/update"/>'><span name="edit" class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"></span></a>&nbsp&nbsp
							    <a name="datalimm"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
							</td>
							<td name="datalimmid">${datalimm.limid}</td>
							<td name="limnm">${datalimm.limnm }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</div>

		<div class="col-sm-8" style="padding:0 0px;margin:0;" >
			<ul class="nav nav-tabs">
				<li class="active"><a hasLode="false" url='<c:url value="/admin/datalimm/getDatalims"/>' onclick="getChild(this)" href="#datalims" name="datalims" data-toggle="tab">按岗位定义权限</a></li>
				<li ><a hasLode="false" url='<c:url value="/admin/datalimm/getDatalimop"/>' onclick="getChild(this)" href="#datalimop" name="datalimop" data-toggle="tab">权限操作</a></li>
				<li ><a hasLode="false" url='<c:url value="/admin/datalimm/getBusheetlim"/>' onclick="getChild(this)" href="#busheetlim" name="busheetlim" data-toggle="tab">单据权限关系</a></li>
				<li ><a hasLode="false" url='<c:url value="/admin/datalimm/getDatalimflds"/>' onclick="getChild(this)" href="#datalimflds" name="datalimflds" data-toggle="tab">权限字段</a></li>
			</ul>
			<div class="tab-content" >
				<!-- 按岗位定义权限菜单显示数据 -->
				<div class="scrollWH tab-pane fade in active"  style="overflow:auto;width:100%;" id="datalims" scrollH="90">
					<table class="table table-hover table-bordered table-responsive">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
							    <td rowspan="2">操作
									<a onclick="addChild(this)" style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
									data-toggle="tooltip" data-placement="right"
									title="添加数据">添加</a>
								</td>
						        <td rowspan="2">操作编号</td>
						        <td rowspan="2">角色编码</td>
						        <td rowspan="2">序号</td>
						        <td colspan="2">权限设置一</td>
						         <td colspan="2">权限设置二</td>
						         <td colspan="2">权限设置三</td>
						        <td rowspan="2">启用日期</td>
						        <td rowspan="2">终止日期</td>
							</tr>
							
							<tr>
							    
						        <td>字段</td>
						        <td>范围</td>
						        <td>字段</td>
						        <td>范围</td>
						        <td>字段</td>
						        <td>范围</td> 
							</tr>
							
							
						</thead>
						<tbody name="datalims">

						</tbody>
					</table>
				
				</div>
							 <!-- 权限操作菜单显示数据 -->
				<div class="scrollWH tab-pane fade" id="datalimop"   style="overflow:auto;width:100%;height:440px" scrollH="100" >
					<table class="table table-hover table-bordered table-responsive">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
							    <td>操作
									<a onclick="addChild(this)" style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
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
				
				 <!-- 单据权限关系菜单显示数据 -->
				<div class="scrollWH tab-pane fade"  style="overflow:auto;width:100%;height:440px" id="busheetlim"  scrollH="100">
					<table class="table table-hover table-bordered table-responsive">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
							    <td>操作
									<a onclick="addChild(this)" style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
									data-toggle="tooltip" data-placement="right"
									title="添加数据">添加</a>
								</td>
						        <td>序号</td>
								<td>资源号</td>
						        <td>资源名称</td>
							</tr>
						</thead>
						<tbody name="busheetlim">

						</tbody>
					</table>
				</div>
				
				 <!-- 权限字段菜单显示数据 -->
				 <div class="scrollWH tab-pane fade"  style="overflow:auto;width:100%;height:440px" id="datalimflds" scrollH="100">
					<table class="table table-hover table-bordered table-responsive">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
							    <td>操作
									<a onclick="addChild(this)" style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
									data-toggle="tooltip" data-placement="right"
									title="添加数据">添加</a>
								</td>
								
						        <td>字段名</td>
						        <td>字段显示名称</td>
						        <td>类型</td>
						       <!--  <td>序号</td>
						        <td>选项</td>
						        <td>不做权限检查的值</td> -->
							</tr>
						</thead>
						<tbody name="datalimflds">

						</tbody>
					</table>
			</div>
			
		</div>
		</div>
	</div>
	
<!-- 模态框 -->	
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" 
               aria-hidden="true">×
            </button>
            <h4 class="modal-title" id="myModalLabel">
               提示信息
            </h4>
         </div>
         <div class="modal-body">
            请先选择一条父表记录
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal">
               	确定
            </button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div>
</body>
<script type="text/javascript">
$(function () { $("[data-toggle='tooltip']").tooltip(); });  //触发提示工具（tooltip）


var parent = "";       //父级ID
var table = $("div.tab-content .active").attr("id");   //当前显示的标签的table的id属性
var url = $(".nav-tabs .active a").attr("url"); 	   //当前显示的标签的url

function getChild(obj){
	
	if($(obj).attr("href")==$(".nav-tabs .active a").attr("href"))return;  		//如果点击的是当前显示的标签则不做任何事
	var uu = $(obj).attr("url"); 
	if(uu){     										//点的是标签
		url = uu;
		table = $(obj).attr("name");    
	}else{      										//点的是父级
		url = $(".nav-tabs .active a").attr("url");   	//当前显示的标签的url
		parent = $(obj).attr("datalimmid");               	//父级ID.
		$(obj).attr("style","background:#ECF0EA").siblings().attr("style",""); 	//修改tr背景色
		$(".nav-tabs li a").attr("hasLode","false");
		$(".nav-tabs .active a").attr("hasLode","true"); 
	}
	//alert("父级ID："+parent+"  链接："+url+"   当前标签ID："+table);
	if(!uu || $(obj).attr("hasLode")=="false"){
		$.ajax({
				url:url,
				data:{datalimmid:parent},
				type:'post',
				success:function(data){
					choseCreate(data);
					if(uu){    							//点击的是标签
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
				var j = i+1;
				switch (table){
					case "datalims":
						html += '<tr limid='+json[i].limid+' opid='+json[i].opid+' utype='+json[i].utype+' ucode='+json[i].ucode+' idx='+json[i].idx+'  >'
				           +'<td class="text-center">'
				           +'<a href=\'<c:url value="/admin/datalims/'+json[i].limid+'/'+json[i].opid+'/'+json[i].utype+'/'+json[i].ucode+'/'+json[i].idx+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
			               +'<a name="datalims" ><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>'
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
							
		                    +'</tr>';
						break;
						
					case "datalimop":
						html += '<tr limid='+json[i].limid+' opid='+json[i].opid+'>'
						             +'<td class="text-center">'
				                     +'<a href=\'<c:url value="/admin/datalimop/'+json[i].limid+'/'+json[i].opid+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
			                         +'<a name="datalimop" ><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>'
			                         +'</td>'
									
									+'<td>'+json[i].opid+'</td>'
									+'<td>'+json[i].opnm+'</td>'
								+'</tr>';
								break;
					case "busheetlim":
						html += '<tr limid='+json[i].limid+' idx='+json[i].idx+' resourceid='+json[i].resourceid+' >'
						            +'<td class="text-center">'
				                    +'<a href=\'<c:url value="/admin/busheetlim/'+json[i].limid+'/'+json[i].resourceid+'/'+json[i].idx+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
			                        +'<a name="busheetlim" ><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>'
			                        +'</td>'
								
									+'<td>'+json[i].idx+'</td>'
									+'<td>'+json[i].resourceid+'</td>'
									+'<td>'+json[i].resname+'</td>'
								+'</tr>';
								break;
					case "datalimflds":
						html += '<tr limid='+json[i].limid+' fldname='+json[i].fldname+'>'
						            +'<td class="text-center">'
		                            +'<a href=\'<c:url value="/admin/datalimflds/'+json[i].limid+'/'+json[i].fldname+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
		                            +'<a name="datalimflds" ><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>'
		                            +'</td>'
									
									+'<td>'+json[i].fldname+'</td>'
									+'<td>'+json[i].cfldname+'</td>'
									+'<td>'+json[i].fldtype+'</td>'
									/* +'<td>'+json[i].idx+'</td>'
									+'<td>'+json[i].fldopts+'</td>'
									+'<td>'+json[i].unlimckvals+'</td>' */
								+'</tr>';
								break;
					default:
						break;
					}
			}
		}
		$('tbody[name='+table+']').html(html);
		$(function () { $("[data-toggle='tooltip']").tooltip(); });  //触发提示工具（tooltip）
	}
	
	
	function addChild(obj){
		var parentTr = $('table[name=parent] tbody tr[style="background:#ECF0EA"]');
		if(parentTr.length==0){
			$('#myModal').modal('show');
		}else{
			var datalimmid = parentTr.attr("datalimmid");
			var id = $("div.tab-content .active").attr("id");   //当前显示的标签的table的id属性
			
			$(obj).attr("href",'<c:url value="/admin/'+id+'/'+datalimmid+'/add"/>');
			
		}
	}

	$("tr[datalimmid='${datalimmid}']").click();
	var ali = $("ul.nav-tabs li a[name=${child}]");
	ali.click();
	
	
	//删除datalimm
	$("a[name=datalimm]").click(function(){
		if(confirm("是否确定要删除？")){
			var tr = this.parentNode.parentNode;
			var datalimmid = tr.getAttribute("datalimmid");
			$.ajax({
				url:'<c:url value="/admin/datalimm/del.ajax" />',
				data:{datalimmid:datalimmid},
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
	
	//删除busheetlim
	$("tbody[name=busheetlim]").on('click','tr td a[name=busheetlim]',function(){
		if(confirm("是否确定要删除？")){
			var tr = this.parentNode.parentNode;
			var limid = tr.getAttribute("limid");
			var idx = tr.getAttribute("idx");
			var resourceid = tr.getAttribute("resourceid");
			$.ajax({
				url:'<c:url value="/admin/busheetlim/del.ajax" />',
				data:{limid:limid,idx:idx,resourceid:resourceid},
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
		
	})
	
	//删除datalims
	$("tbody[name=datalims]").on('click','tr td a[name=datalims]',function(){
		if(confirm("是否确定要删除？")){
			var tr = this.parentNode.parentNode;
			var limid = tr.getAttribute("limid");
			var opid = tr.getAttribute("opid");
			var utype = tr.getAttribute("utype");
			var ucode = tr.getAttribute("ucode");
			var idx = tr.getAttribute("idx");
			$.ajax({
				url:'<c:url value="/admin/datalims/del.ajax" />',
				data:{limid:limid,opid:opid,utype:utype,ucode:ucode,idx:idx},
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
		
	})
	
	
	//删除datalimop
	$("tbody[name=datalimop]").on('click','tr td a[name=datalimop]',function(){
		if(confirm("是否确定要删除？")){
			var tr = this.parentNode.parentNode;
			var limid = tr.getAttribute("limid");
			var opid = tr.getAttribute("opid");
			
			$.ajax({
				url:'<c:url value="/admin/datalimop/del.ajax" />',
				data:{limid:limid,opid:opid},
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
		
	})
	
	//删除datalimop
	$("tbody[name=datalimflds]").on('click','tr td a[name=datalimflds]',function(){
		if(confirm("是否确定要删除？")){
			var tr = this.parentNode.parentNode;
			var limid = tr.getAttribute("limid");
			var fldname = tr.getAttribute("fldname");
			
			$.ajax({
				url:'<c:url value="/admin/datalimflds/del.ajax" />',
				data:{limid:limid,fldname:fldname},
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
		
	})
	
</script>
</html>