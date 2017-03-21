<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<body>
 <div class="container" style="width:100%;padding:0 15px;margin:0;"> 
  <form id="selectForm" action='<c:url value="/main/sysconfig/selectALitle"/>' method="post" >
	<div class="col-sm-12" style="margin-bottom:10px;">
		<label class="control-label col-sm-1 col_padding">编号:</label>
		<div class="col-sm-3">
          <div class="input-group">
		    <input id="connokey" name="filter.[v_=_0_,].code" value="${selectMap.code}" type="text" class="date form-control"/>
		  </div>
		</div>
		<label class="control-label col-sm-1 col_padding">中文名称:</label>
		<div class="col-sm-3">
          <div class="input-group">
		    <input id="optionkey" name="filter.[v_=_0_,].option1" value="${selectMap.option1}" type="text" class="date form-control"/>
		  </div>
		</div>
	
		
	</div>
	</form>
	<div class="click_list row" style="margin:0;">
	  <a href="<c:url value='add'/>">增加</a>
	  <a onclick="selectALitel()">搜索</a>
	</div>
<div class="dataTables_wrapper form-inline dt-bootstrap no-footer">
 <div class="dataTables_scroll">
  <div class="dataTables_scrollHead">
   <div class="dataTables_scrollHeadInner" role="grid">
		<table class="table table-bordered table-striped">
			<thead>
				<tr class='THD_width'>
				    <th width="150">操作</th>
					<th width="150">编号(唯一)</th>
					<th width="150">中文名称</th>
					<th width="150">选项1</th>
					<th width="150">选项2</th>
					<th width="150">选项3</th>
					<th width="150">选项4</th>
					<th width="150">选项5</th>
					<th width="150">选项6</th>
					<th width="150">选项7</th>
					<th width="150">选项8</th>
					<th width="150">选项9</th>
					<th width="150">选项10</th>
					<th width="150">选项11</th>
					<th width="150">选项12</th>
					<th width="150">选项13</th>
					<th width="150">选项14</th>
					<th width="150">选项15</th>
					<th width="150">选项16</th>
					<th width="150">选项17</th>
					<th width="150">选项18</th>
					<th width="150">选项19</th>
					<th width="150">选项20</th>
					<th width="150">选项21</th>
					<th width="150">选项22</th>
					<th width="150">选项23</th>
					<th width="150">备注</th>
				</tr>
			</thead>
			
				<c:forEach items="${pluginConfigs}" var="pluginConfig">
					<tr id="${pluginConfig.code} }">
					    <td><a href="edit?id=${pluginConfig.code}">[编辑]</a>
						    <a href="javascript:;" name="delete" val="${pluginConfig.code}">[删除]</a>
						</td>
						<td>${pluginConfig.code} </td>
						<td>${pluginConfig.name}</td>
						<td>${pluginConfig.option1}</td>
                        <td>${pluginConfig.option2}</td>
						<td>${pluginConfig.option3}</td>
						<td>${pluginConfig.option4}</td>
						<td>${pluginConfig.option5}</td>
						<td>${pluginConfig.option6}</td>
						<td>${pluginConfig.option7}</td>
						<td>${pluginConfig.option8}</td>
						<td>${pluginConfig.option9}</td>
						<td>${pluginConfig.option10}</td>
						<td>${pluginConfig.option11}</td>
						<td>${pluginConfig.option12}</td>
						<td>${pluginConfig.option13}</td>
						<td>${pluginConfig.option14}</td>
						<td>${pluginConfig.option15}</td>
						<td>${pluginConfig.option16}</td>
						<td>${pluginConfig.option17}</td>
						<td>${pluginConfig.option18}</td>
						<td>${pluginConfig.option19}</td>
						<td>${pluginConfig.i_option1}</td>
						<td>${pluginConfig.i_option2}</td>
						<td>${pluginConfig.n_option1}</td>
						<td>${pluginConfig.n_option2}</td>
						<td>${pluginConfig.mome}</td>
					</tr>
				</c:forEach>
			
		</table>
		
	</div>
   </div>
  </div>
 </div>
<div style="float:left"><portal:page url='admin/sysconfig/list'/></div>
</div>
</body>
<script type="text/javascript">
	$().ready(function(){
		var $delete = $("#delete");
		
		$delete.click(function() {
			var $this = $(this);
			$.dialog({
				type: "warn",
				content: "确认删除？",
				onOk: function() {
					$.ajax({
						url: "delete.ajax",
						type: "POST",
						data: {id: $this.attr("val")},
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								$this.closest("tr").remove();
							}
						}
					});
				}
			});
			return false;
		});
	});
	
	function selectALitel(){
		var selectform = $("#selectForm")
		pageFormSubmit();
	}
</script>
<script type="text/javascript" src='<c:url value="/resources/js/table.js"/>'></script>
</html>