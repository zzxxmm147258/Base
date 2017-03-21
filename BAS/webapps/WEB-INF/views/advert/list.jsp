<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>

<script type="text/javascript" src="<c:url value='/resources/js/common.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/assets/js/sea.js'/>"></script>
<body>
	<div class="container" style="padding:0;margin:0;height：100%;width:100%;">
		<div class="col-sm-8" style="padding:0 2px;">
		<ul class="nav nav-tabs">
				<li ><a hasLode="false">广告组</a></li>
	   </ul>
		<div style="overflow:auto;width:100%;" class="scrollWH" scrollH="90">
			<table class="table table-hover table-bordered" name="parent">
				<thead bgcolor="#F0F8FF"  class="text-center">
					<tr>
						 <td width="150">操作
							<a href='<c:url value="/main/advertgroup/add"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
								data-toggle="tooltip" data-placement="right"
								title="添加数据">添加</a>
						</td>
						<td>组名</td>
						<td>位置</td>
						<td>开始时间</td>
						<td>结束时间</td>
						<td>是否启用</td>
						<td>创建时间</td>
						<td>修改时间</td>
						<td>备注</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="advertGroup">
						<tr parentId="${advertGroup.id}" onclick="getChild(this)">
						   <td class="text-center">
								<a href='<c:url value="/main/advertgroup/${advertGroup.id}/update"/>'><span name="edit" class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"></span></a>&nbsp
							    <%-- <a href='<c:url value="/main/weixin/menu/${wxmenu.appid}/del"/>'><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a> --%>
							    <a name="groupRemove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
							</td>
							<td class="text-center">${advertGroup.name}</td>
							<td class="text-center">${advertGroup.adPositionName }</td>
							<td class="text-center">${advertGroup.begin }</td>
							<td class="text-center">${advertGroup.end }</td>
							<td>
								<c:choose>
										<c:when test="${advertGroup.available eq true }">
											<span class="glyphicon glyphicon-ok" style="font-size:16px" title="启用">
										</c:when>
										<c:otherwise>
											<span class="glyphicon glyphicon-remove" style="font-size:16px" title="未启用">
										</c:otherwise>
									</c:choose>
							</td>
							<td class="text-center">
								<fmt:formatDate value="${advertGroup.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">
								<fmt:formatDate value="${advertGroup.modifyDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="text-center">${advertGroup.remark }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</div>

		<div class="col-sm-4" style="padding:0 0px;margin:0;" >
					<ul class="nav nav-tabs">
				<li class="active"><a hasLode="false" url='<c:url value="/main/advert/selectByGroupId.ajax"/>' onclick="getChild(this)" href="#advert" name="advert" data-toggle="tab">广告明细</a></li>
			</ul>
			<div class="tab-content" >
				<!-- 广告明细 -->
				<div class="scrollWH tab-pane fade in active"  style="overflow:auto;width:100%;" id="advert" scrollH="90">
					<table class="table table-hover table-bordered table-responsive" style="table-layout:fixed;">
					 	<thead bgcolor="#F0F8FF" class="text-center"> 
							
							<tr>
							    <td width="100">操作
									<a onclick="addChild(this)" style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
									data-toggle="tooltip" data-placement="right"
									title="添加数据">添加</a>
								</td>
						        <td>标题</td>
								<td>类型</td>
								<td>图片</td>
								<td>排序</td>
							</tr>
												
						 </thead> 
						<tbody name="advert">
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
		parent = $(obj).attr("parentId");               	//父级ID.
		$(obj).attr("style","background:#ECF0EA").siblings().attr("style",""); 	//修改tr背景色
		$(".nav-tabs li a").attr("hasLode","false");
		$(".nav-tabs .active a").attr("hasLode","true"); 
	}
	//alert("父级ID："+parent+"  链接："+url+"   当前标签ID："+table);
	if(!uu || $(obj).attr("hasLode")=="false"){
		$.ajax({
				url:url,
				data:{groupId:parent},
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
					case "advert":
						html += '<tr parentId='+json[i].groupId+' id='+json[i].id+'>'
				           +'<td class="text-center">'
				           +'<a href=\'<c:url value="/main/advert/'+json[i].id+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
			               +'<a name="advertRemove" ><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>'
				           +'</td>'
							+'<td class="text-center">'+json[i].title+'</td>'
							+'<td class="text-center">'+json[i].type+'</td>'
							+'<td class="text-center"><img style="height:50px;width:50px" src='+json[i].path+'></td>'
							+'<td class="text-center">'+json[i].orders+'</td>'
							
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
			var parentId = parentTr.attr("parentId");
			var id = $("div.tab-content .active").attr("id");   //当前显示的标签的table的id属性
			
			$(obj).attr("href",'<c:url value="/main/advert/'+parentId+'/add"/>');
			
		} 
	}
	
	
	
	
	$("a[name=groupRemove]").click(function(){
		if(confirm("是否确定要删除？")){
			var tr = this.parentNode.parentNode;
			var parentId = tr.getAttribute("parentId");
			$.ajax({
				url:'<c:url value="/main/advertgroup/del.ajax" />',
				data:{id:parentId},
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
	
	$("tbody[name=advert]").on('click','tr td a[name=advertRemove]',function(){
		if(confirm("是否确定要删除？")){
			var tr = this.parentNode.parentNode;
			var id = tr.getAttribute("id");
			$.ajax({
				url:'<c:url value="/main/advert/del.ajax" />',
				data:{id:id},
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
	
	
	$("tr[parentId=${parentId}]").click();

</script>
</html>