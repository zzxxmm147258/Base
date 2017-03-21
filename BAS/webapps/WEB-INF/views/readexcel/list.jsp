<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
	<div class="container">
		<div>
		<div style="overflow:auto;width:100%;height:190px">
			<table class="table table-hover table-bordered" name="parent">
				<thead bgcolor="#F0F8FF"  class="text-center">
					<tr>
						<td>序号</td>
						<td>操作
							<a href='<c:url value="/admin/readexceldef/add"/>' style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
								data-toggle="tooltip" data-placement="right"
								title="添加数据">添加</a>
						</td>
						<td>编码</td>
						<td>名字</td>
						<td>表名</td>
						<td>数据模型</td>
						<td>读入Excel表单名</td>
						<td>Excel起始读入行</td>
						<td>Excel每行循环执行次数</td>
						<td>选项</td>
						<td>groovy</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="li" varStatus="i">
						<tr rxcode="${li.rxcode}" onclick="getChild(this)">
							<td style="text-align:center;">${i.count }</td>
							<td class="text-center">
								<a href='<c:url value="/admin/readexceldef/${li.rxcode}/update"/>' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp
								<a href='<c:url value="/admin/readexceldef/${li.rxcode}/view?view"/>' data-toggle="tooltip" title="查看"><span class="glyphicon glyphicon-eye-open" style="font-size:16px"/></a>&nbsp&nbsp
								<a href='<c:url value="/admin/readexceldef/${li.rxcode}/view?del"/>' data-toggle="tooltip" title="删除"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px"/></a>
							</td>
							<td>${li.rxcode}</td>
							<td>${li.rxname }</td>
							<td>${li.tblname }</td>
							<td>${li.model }</td>
							<td>${li.excelsheetname }</td>
							<td>${li.excelfromrow }</td>
							<td>${li.fortimes }</td>
							<td>${li.flags }</td>
							<td>${li.groovy }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<br>
		<div>
			<ul class="nav nav-tabs" style="display:none">
				<li class="active"><a hasLode="false" url='<c:url value="/admin/readexceldefg/list.ajax"/>' onclick="getChild(this)" href="#readexceldefg" name="readexceldefg" data-toggle="tab">字段</a></li>
				<!-- <li><a hasLode="false" url='www.baidu.com' onclick="getChild(this)" href="#test00" name="test00" data-toggle="tab">测试（百度）</a></li> -->
			</ul>
			<div class="tab-content scrollWH" scrollH="90">
				<div class="tab-pane fade in active" id="readexceldefg">
					<table class="table table-hover table-bordered table-responsive">
						<thead bgcolor="#F0F8FF" class="text-center">
							<tr>
								<td>序号</td>
								<td>操作
									<a onclick="addChild(this)" style="padding:1px;height:24px;width:50px" type="button" class="btn btn-success"
									data-toggle="tooltip" data-placement="right"
									title="添加数据">添加</a>
								</td>
								<td>字段名</td>
								<td>字段显示名</td>
								<td>该字段对应Excel列号</td>
								<td>字段类型</td>
								<td>大小</td>
								<td>小数</td>
								<td>缺省值</td>
								<td>分组描述</td>
								<td>选项</td>
								<td>groovy</td>
							</tr>
						</thead>
						<tbody name="readexceldefg">

						</tbody>
					</table>
				</div>

				<!-- <div class="tab-pane fade" id="test00">
					<p>测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容。</p>
				</div> -->
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
		parent = $(obj).attr("rxcode");               	//父级ID.
		$(obj).attr("style","background:#ECF0EA").siblings().attr("style",""); 	//修改tr背景色
		$(".nav-tabs li a").attr("hasLode","false");
		$(".nav-tabs .active a").attr("hasLode","true"); 
	}
	//alert("父级ID："+parent+"  链接："+url+"   当前标签ID："+table);
	if(!uu || $(obj).attr("hasLode")=="false"){
		$.ajax({
				url:url,
				data:{rxcode:parent},
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
					case "readexceldefg":
						html += '<tr rxcode='+json[i].rxcode+' fldname='+json[i].fldname+'>'
									+'<td>'+j+'</td>'
									+'<td class="text-center">'
									+'<a href=\'<c:url value="/admin/readexceldefg/'+json[i].rxcode+'/'+json[i].fldname+'/update"/>\' data-toggle="tooltip" title="修改"><span class="glyphicon glyphicon-edit" style="font-size:16px"/></a>&nbsp&nbsp'
									+'<a href=\'<c:url value="/admin/readexceldefg/'+json[i].rxcode+'/'+json[i].fldname+'/view?view"/>\' data-toggle="tooltip" title="查看"><span class="glyphicon glyphicon-eye-open" style="font-size:16px"/></a>&nbsp&nbsp'
									+'<a href=\'<c:url value="/admin/readexceldefg/'+json[i].rxcode+'/'+json[i].fldname+'/view?del"/>\' data-toggle="tooltip" title="删除"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px"/></a>'
									+'</td>'
									+'<td>'+json[i].fldname+'</td>'
									+'<td>'+json[i].flddisplayname+'</td>'
									+'<td>'+json[i].excelcol+'</td>'
									+'<td>'+json[i].fldtype+'</td>'
									+'<td>'+json[i].fldsize+'</td>'
									+'<td>'+json[i].flddecimal+'</td>'
									+'<td>'+json[i].flddefault+'</td>'
									+'<td>'+json[i].groupdesc+'</td>'
									+'<td>'+json[i].flags+'</td>'
									+'<td>'+json[i].groovy+'</td>'
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
			var rxcode = parentTr.attr("rxcode");
			$(obj).attr("href",'<c:url value="/admin/readexceldefg/'+rxcode+'/add"/>');
			
			//多个子标签时用以下方式拼href
			/*var tableName = $("div.tab-content div.active").attr("id");
			$(obj).attr("href",'<c:url value="/admin/'+tableName+'/'+rxcode+'/add"/>'); */
		}
	}
	
</script>
</html>