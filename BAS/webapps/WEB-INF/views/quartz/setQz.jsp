<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"> 
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <link href="<c:url value='/resources/bootstrap/css/bootstrap.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/bootstrap/js/jquery.min.js'/>"></script>
    <script type="text/javascript" src='<c:url value="/resources/js/menu.js"/>'></script>
    <style>
      .box{height:43px; width:1100px;border-bottom: 1px solid #ccc;position:relative;}
      .tab-content{width:1100px;position:absolute;left:0;top:44px;}
      .switch{vertical-align:middle;float:left;margin-right:2px;width:60px;height: 30px;border:1px solid #ccc;background: #C0C0C0;font:16px/30px '微软雅黑';color:#000;text-align: center;margin-top: 5px;}
      #myTabContent{margin-top: 5px;}
      th{height: 30px;width:10%;border:1px solid #ccc;margin:-1px;padding:0;text-align: center;}
      td{border:1px solid #ccc;text-align: center;}
      input{font-size: 12px;margin:2px 0;width:100%;height: 30px;}
      td .Modify,.Pause,.Restart,.Delete,.cancle{width:32px;height: 22px;margin:2px 1px;}
      .append a{font-size:20px;}
      .append{text-align: center;}
    </style>
</head>
<div class="scrollWH" scrollH="100">
<div class="box" >
    <ul id="myTab" class="nav nav-tabs" style="float:left;">
    <c:forEach var="map" items="${qznames}" varStatus="status">
    	<c:choose>
    	 	<c:when test="${status.index==0}">
    	 		<li class='active' isqserver="${map.isqserver}">
	    	 		<a href="javascript:;" data-toggle="tab" name='${map.schename}'>【${map.schename}】
	    	 			<c:if test="${map.isqserver}">
	    	 				<span id="switchStatus" style="margin-top:3px;float:right; height: 15px;width: 15px;border-radius:50%;background-color: ${map.status};">
	    	 			</c:if>
	    	 		</a>
	    	 	</span></li>
    	 	</c:when>
    	 	<c:otherwise>
    	 		<li isqserver="${map.isqserver}">
    	 			<a href="javascript:;" data-toggle="tab" name='${map.schename}'>【${map.schename}】
    	 				<c:if test="${map.isqserver}">
    	 					<span id="switchStatus" style="margin-left:4px;margin-top:3px;float:right; height: 15px;width: 15px;border-radius:50%;background-color: ${map.status};">
    	 				</c:if>
    	 			</a>
    	 		</li>
    	 	</c:otherwise>
    	</c:choose>
    </c:forEach>
    <li>
    	<a id='switchAdd' href="javascript:;" data-toggle="tab" onclick="switchAdd(this)">+</a>
    </li>
  </ul>
  <div style="float: right;">
  <c:if test="${not empty qznames}">
	  <a href="javascript:;" class="switch" onclick="QZSwitch(this)">开关</a>
  </c:if>
  </div>
  <div id="myTabContent" class="tab-content">
    <div class="tab-pane fade in active" id="show"> 
    </div>
</div>
</div>
</div>
<script>
	var Urls = "<c:url value='/admin/quartz/operation/'/>";
	$(function() {
		$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
			// Get the name of active tab
			var activeTab = $(e.target).text();
			// Get the name of previous tab
			var previousTab = $(e.relatedTarget).text();
			$(".active-tab span").html(activeTab);
			$(".previous-tab span").html(previousTab);
		});
	});

	$('#myTab li').bind('click', function() {
		$('#myTab li').removeClass("active");
		switchloadData(this);
	})

	function loadAllDate(){
		location.reload();
	}
	
	switchloadData($(".active"));
	
	function switchloadData(obj){
		$(obj).addClass("active");
		loadData($(obj).find("a")[0].name);
		var isqserver = $(obj).attr("isqserver");
		if(isqserver&&'true'==isqserver){
			$(".switch").show();
		}else{
			$(".switch").hide();
		}
	}
	function loadData(qzname) {
		var hh = window.screen.availHeight-260;
		var str = '<div><table><tr><th style="display:none;">ID</th><th style="color:red">调度器[英文]</th><th style="color:red">任务编号</th><th>任务描述</th><th>参数</th><th style="color:red">任务方法</th><th style="color:red">执行时间</th><th>当前状态</th><th>启用</th><th style="color:red">服务器</th><th>操作</th></tr>';
		$('.switch').attr('qzuri', qzname + '/standby');
		var url = Urls + qzname;
		if (qzname) {
			$.ajax({
				type : 'post',
				url : url,
				dataType : "json",
				/*                 async:false,
				  jsonp: "callbackparam",//服务端用于接收callback调用的function名的参数
				  jsonpCallback:"success_ajax_aross_domain",//callback的function名称 */
				success : function(data) {
					var p = eval(data);
					var list = data.list;
					for (var i = 0; i < list.length; i++) {
						str = str + '<tr>';
						str = str + '<td name="id" style="display:none;"><input type="text" name="id" value="'+list[i].id+'"></td>';
						str = str + '<td name="schename">' + list[i].schename + '</td>';
						str = str + '<td name="jobname">' + list[i].jobname + '</td>';
						str = str + '<td name="description">' + list[i].description + '</td>';
						str = str + '<td name="jobparams">' + list[i].jobparams + '</td>';
						str = str + '<td name="executeclass">' + list[i].executeclass + '</td>';
						str = str + '<td name="executetime">' + list[i].executetime + '</td>';
						str = str + '<td name="qtype">' + list[i].qtypename + '</td>';
						var checked = "";
						if(list[i].utype){
							checked = 'checked="checked"';
						}
						str = str + '<td name="utype"><input type="checkbox" name="utype" disabled="disabled" value="1" ' + checked + ' onclick="checkBox(this)"/></td>';
						str = str + '<td name="qserver">' + list[i].qserver + '</td>';
						//不在本机同时启用状态，不能操作
						if (list[i].disabled && list[i].utype==1) {
							//if(list[i].qtype==-1||list[i].qtype==8){
							//	str =str + '<td  name="btn" qzname="'+list[i].schename+'"><input type="button" value="删除" class="Delete" onclick=DeleteOption(this)></td>';
							//}
							str =str + '<td  name="btn" qzname="'+list[i].schename+'"></td>';
						} else {
							//str =str + '<td  name="btn" qzname="'+list[i].schename+'"><input type="button" value="修改" class="Modify" onclick=ModifyOption(this)><input type="button" value="暂停" class="Pause" onclick=PauseOption(this)><input type="button" value="重启" class="Restart" onclick=RestartOption(this)><input type="button" value="删除" class="Delete" onclick=DeleteOption(this)></td>';
							str =str + '<td  name="btn" qzname="'+list[i].schename+'"><input type="button" value="修改" class="Modify" onclick=ModifyOption(this)><input type="button" value="删除" class="Delete" onclick=DeleteOption(this)><input type="button" value="清空" class="Delete" onclick=ClearOption(this)></td>';
						}
						str =str + '</tr>';
					}
					str = str + '<tr class="append"><td colspan="11"><a href="javascript:;" title=""  onclick=AddOption(this,"add")><span>+</span></a></td></tr></table></div>';
					$('#show').html(str);
					if (data.type == 'open') {
						$('.switch').text('关闭');
						$('.active').find('#switchStatus').css({
							background : "green",
							border : "1px solid #000"
						});
						$('.switch').css({
							background : "red",
							border : "1px solid #000"
						});
						$('.switch').attr('qzuri', qzname + '/standby');
					} else {
						$('.switch').attr('qzuri', qzname + '/start');
						$('.switch').text('开启');
						$('.active').find('#switchStatus').css({
							background : "red",
							border : "1px solid #000"
						});
						$('.switch').css({
							background : "green",
							border : "1px solid #ccc"
						});
					}
				},
				error : function() {
					console.log(qzname+":ERROR");
				}

			})
			$('.switch').show();
		}else{
			str = str + '<tr class="append"><td colspan="11"><a href="javascript:;" title=""  onclick=AddOption(this,"add")><span>+</span></a></td></tr></table>';
			$('#show').html(str);
			$('.switch').hide();
		}
	}
	function QZSwitch(obj) {
		var href = $(obj).attr('qzuri');
		ajaxCom(null, null, href, true);
	}

	var html = null;
	var btn = null;

	function ModifyOption(obj) {//修改按钮
		var tr = obj.parentNode.parentNode;
		resumeSumbit(btn)
		btn = tr;
		html = tr.innerHTML;
		var attr = ['jobname','description', 'jobparams', 'executeclass', 'executetime','utype','qserver']
		for (var i = 0; i < attr.length; i++) {
			var td = $(tr).find('td[name="' + attr[i] + '"]')[0];
			if('utype'==attr[i]){
				var checked = "";
				if($(td).children("input")[0].checked){
					checked = 'checked="checked" value="1"';
				}else{
					checked = ' value="1"';
				}
				td.innerHTML = '<input type="checkbox" name="' + attr[i] + '"' + checked+' onclick="checkBox(this)"/>';
			}else{
				td.innerHTML = '<input type="text" name="' + attr[i] +'" value="' + td.innerText +'"/>';
			}
		}
		ModifySubmitBtn(obj.parentNode, "modify");
	}

	function ModifySubmitBtn(btn, type) {//这个是提交按钮的事件
		btn.innerHTML = '<input type="button" value="提交" name="btnsubmit" class="Modify" onclick="submitOption(this,\'' + type + '\')"><input class="cancle" type="button" value="取消" onclick="submitCancle(this)">';
	}

	function submitCancle(obj){
		var tr = obj.parentNode.parentNode;
		resumeSumbit(tr);
		$("#input[type='button']").removeAttr("disabled");
		btn = null;
		html = null;
	}
	
	function resumeSumbit(obj){
		if(obj&&null!=obj){
			obj.innerHTML=html;
		}
	}
	
	
	function PauseOption(obj) {//这个是暂停按钮
		var tr = obj.parentNode.parentNode;
		resumeSumbit(btn)
		btn = tr;
		params = [ 'id', 'schename', 'jobname' ];
		ajaxCom(tr, params, '/pasue', false);
	}

	function DeleteOption(obj) {//这个是删除按钮
		if (window.confirm("是否删除？") == 0)
			return;
		var tr = obj.parentNode.parentNode;
		resumeSumbit(btn)
		btn = tr;
		params = [ 'id', 'schename', 'jobname' ];
		ajaxCom(tr, params, '/remove', false);
	}
	
	function ClearOption(obj) {//这个是删除按钮
		if (window.confirm("是否清空？") == 0)
			return;
		var tr = obj.parentNode.parentNode;
		resumeSumbit(btn)
		btn = tr;
		params = [ 'id', 'schename', 'jobname' ];
		ajaxCom(tr, params, '/clear', false);
	}

	function RestartOption(obj) {//这个是重启按钮
		var tr = obj.parentNode.parentNode;
		resumeSumbit(btn)
		btn = tr;
		params = ['id', 'schename', 'jobname', 'jobparams', 'executeclass', 'executetime', 'utype'];
		ajaxCom(tr, params, '/resume', false);
	}

	function AddOption(obj, type) { //下面+号按钮添加
		resumeSumbit(btn)
		btn = obj.parentNode.parentNode;
		html = $('.append').html();
		var schename = $('#myTab .active a').attr("name");
		var html_str = '<td name="schename"><input type="text" name="schename" value=""></td>';
		if(schename){
			html_str = '<td name="schename">'+schename+'</td>';
		}
		var adtr = document.createElement('tr');
		var params = [ 'jobname','description','jobparams', 'executeclass', 'executetime', 'qtype', 'utype', 'qserver' ]//根据name找到所有的td值
		for (var i = 0; i < params.length; i++) {
			if('qtype'==params[i]){
				html_str += '<td name="'+params[i]+'"></td>';
			}else if('utype'==params[i]){
				html_str += '<td name="'+params[i]+'"><input type="checkbox" name="' + params[i] +'" value="0" onclick="checkBox(this)"/></td>';
			}else{
				html_str += '<td name="'+params[i]+'"><input type="text" name="'+params[i]+'"/></td>';
			}
		}
		var adtr = html_str + '<td><input type="button" value="提交" class="Modify" onclick="submitOption(this,\'' + type + '\')"><input class="cancle" type="button" value="取消" onclick="submitCancle(this)"></td></tr>';
		$('.append').html(adtr);
		ModifySubmitBtn(obj.parentNode, "add");
	}
	
	function checkBox(obj){
		var checked = obj.checked;
		if(checked){
			$(obj).val("1");
		}else{
			$(obj).val("0");
		}
	}

	function submitOption(obj, type) {//修改以后点击提交按钮。把修改后的数据发送到后台，后台返回数据，是否请求成功.
		//修改操作
		if ("modify" == type) {
			var tr = obj.parentNode.parentNode;
			var attrs = [ 'id', 'schename', 'jobname','description','jobparams', 'executeclass', 'executetime','utype','qserver' ];
			ajaxCom(tr, attrs, '/modify', false);
		}
		//添加操作
		if ("add" == type) {
			var tr = obj.parentNode.parentNode;
			var params = ['schename','jobname','jobparams','description','executeclass', 'executetime', 'utype', 'qserver' ];//根据name找到所有的td值
			ajaxCom(tr, params, '/add', true);
		}
	}

	//公共的ajax请求.操作的那个tr,修改的数组,请求的链接

	function ajaxCom(tr, attrs, type, isLoadAll) {
		var datass = null;//这个datass是我返给后台的数据值，这个值是根据改的东西name值为属性,value值为属性值;
		var qzname = "";
		if (tr && null != tr) {
				qzname = $(tr).find('td[name="schename"] input').val();
			if (!qzname || null == qzname || "" == qzname) {
				qzname = $(tr).find('td[name="schename"]')[0].innerText;
			}
		}
		if (!qzname || null == qzname) {
			qzname = "";
		}
		if (tr && null != tr && attrs && null != attrs) {
			for (var i = 0; i < attrs.length; i++) {
				var input = $(tr).find('td[name="' + attrs[i] + '"] input');
				var data = null;
				if (input && null != input && input.length > 0) {
					data = input.val();
				} else {
					data = $(tr).find('td[name="' + attrs[i] + '"]')[0].innerText;
				}
				if (data && null != data) {
					if (null == datass) {
						datass = attrs[i] + "=" + data; //这个值是根据改的东西name值为属性,value值为属性值;
					} else {
						datass = datass + "&" + attrs[i] + "=" + data;
					}
				}
			}
		}
		$.ajax({
			type : 'post',
			url : Urls + qzname + type,
			data : datass,
			dataType : "json",
			/*                  async:false,
			  jsonp: "callbackparam",//服务端用于接收callback调用的function名的参数
			  jsonpCallback:"success_ajax_aross_domain",//callback的function名称 */
			beforeSend: function () {
			        // 禁用按钮防止重复提交
			        $("input[type='button']").attr({ disabled: "disabled" });
			        $(".cancle").removeAttr("disabled");
			},
			success : function(data) {
				if (data.success) {
					if (isLoadAll) {
						loadAllDate();
					} else {
						loadData(qzname);
					}
				} else {
					alert(data.result);
					loadData(qzname);
				}
			},
			complete: function () {
		        $("#input[type='button']").removeAttr("disabled");
		    },
		    error: function (data) {
		        console.info("error: " + data.responseText);
		    }
		});
	}
</script>

</body>
</html>