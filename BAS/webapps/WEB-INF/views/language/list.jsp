<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link rel="stylesheet" href='<c:url value="/resources/css/ifram.css"/>'>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>

<body>
<div class="container" style="width:100%;padding:0 15px;margin:0;"> 
	   <form id="selectForm" action='<c:url value="/admin/language/list"/>' method="post" >
          <div class="row" style="margin-bottom:10px;">
             <label class="control-label col-sm-1 col_padding">编码：</label>
             <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].code" value="${selectMap.code}" type="text" class="form-control">
                </div>
             </div>  
            <label class="control-label col-sm-1 col_padding">中文：</label>
             <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].zh" value="${selectMap.zh}" type="text" class="form-control">
                </div>
             </div> 
             
             <label class="control-label col-sm-1 col_padding">英文：</label>
             <div class="col-sm-3">
                <div class="input-group">
                    <input name="filter.[v_like_3_,].en" value="${selectMap.en}" type="text" class="form-control">
                </div>
             </div>
             
          </div>
          
          
             
       </form>
       <div class="click_list row" style="margin:0;">
            <a id="ButFind" name="find" onclick="onButFind()">查询</a>
            <a style="margin-left: 20px;" href='<c:url value="/admin/language/add"/>' >添加</a>
        </div>

    <div class="dataTables_wrapper form-inline dt-bootstrap no-footer">
            <div class="dataTables_scroll">
                <div class="dataTables_scrollHead">
                    <div class="dataTables_scrollHeadInner" role="grid">
                        <table class="table table-bordered table-striped">
                           <thead>
                              <tr class='THD_width'>
                              	<th width="80">序号</th>
                              	<th width="120">操作</th>
                                <th width="200">编码</th>
                         		<th width="200">中文</th>
                                <th width="200">英文</th>
							</tr>
                           </thead>
                           <c:forEach items="${list }" var="p" varStatus="i">
							<tr id="${p.id }">
								<td style="text-align:center;">${i.count }</td>
								<td class="text-center">
									<a  menuname="修改" href='<c:url value="/admin/language/add?id=${p.id }"/>'><span class="glyphicon glyphicon-edit" style="font-size:16px" title="编辑"/></a>&nbsp&nbsp
									<a name="remove"><span class="glyphicon glyphicon-remove" style="color:red;font-size:16px" title="删除"/></a>
								</td>
								<td>${p.code }</td>
								<td>${p.zh }</td>
								<td>${p.en }</td>
							</tr>
						</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${empty select}">
        	<div style="float:left"><portal:page url='<c:url value="/admin/language/list"/>'/></div>
        </c:if>
</div>
</body>
<script type="text/javascript">

	$("#selectForm input").attr("onkeydown","if(event.keyCode==13){$('#selectForm').submit();}"); //添加回车事件
	function onButFind(){
		$("#selectForm").submit();
	}

	//删除按钮事件
	$("a[name=remove]").click(function(){
	if(confirm("是否确定要删除？")){
		var tr = this.parentNode.parentNode;
		var id = tr.getAttribute("id");
		$.ajax({
			url:'<c:url value="/admin/language/del.ajax" />',
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
	});


	
</script>
<script type="text/javascript" src='<c:url value="/resources/js/table.js"/>'></script>
</html>