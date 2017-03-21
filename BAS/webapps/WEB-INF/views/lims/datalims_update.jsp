<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<link href='<c:url value="/resources/bootstrap_datetimepicker/css/bootstrap-datetimepicker.min.css"/>' rel="stylesheet" media="screen">
<body>
	<strong>
		<h1 class="text-center" style="margin-top: 5%;">
		员工发放工资修改
		</h1>
	</strong>
	<div class="container" style="margin-top: 5%;">
		<form class="form-horizontal"  action="<c:url value="/main/datalimm/update"/>" method="post" >
			
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="limid">权限编码:</label>
				</div>
				<div class="col-md-4">
					<input id="limid" name="limid"  class="form-control" value="${datalimm.limid }" path="limid" />
				</div>
			</div>
			
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="limnm">权限名称:</label>
				</div>
				<div class="col-md-4">
					<input id="limnm" name="limnm"  class="form-control" value="${datalimm.limnm }" path="limnm" />
				</div>
			</div>
			
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="flags">标记:</label>
				</div>
				<div class="col-md-4">
					<input id="flags" name="flags"  class="form-control" value="${datalimm.flags }" path="flags" />
				</div>
			</div>
			
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="modifier">修改人:</label>
				</div>
				<div class="col-md-4">
					<input id="modifier" name="modifier"  class="form-control" value="${datalimm.modifier }" path="modifier" />
				</div>
			</div>
			
			<div class="form-group">
                <div style="text-align: right;" class="col-md-4">
					<label for="modifydate">修改日期:  </label>
				</div>
				<div class="col-md-4">
                <div class="input-group date form_datetime" data-date="" data-date-format="yyyy-mm-dd" data-link-field="modifydate" data-link-format="yyyy-mm-dd">
                     <input class="form-control"  type="text" value="${modifydate }"  readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
                </div>
				<input id="modifydate" name="modifydate" type="hidden"  path="modifydate"  /><br/>
            </div>
            
            <div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="startflags">启用标记:</label>
				</div>
				<div class="col-md-4">
					<input id="startflags" name="startflags"  class="form-control" value="${datalimm.startflags }" path="modifier" />
				</div>
			</div>
            
            <div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label for="ord">序号:</label>
				</div>
				<div class="col-md-4">
					<input id="ord" name="ord"  class="form-control" value="${datalimm.ord }" path="ord" />
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<input id="submit" class="btn btn-primary col-md-12" type="submit"
						class="btn btn-default" value="提交">
				</div>
			</div>
		</form>
	</div>	
	
	
	
</body>
<script type="text/javascript" src='<c:url value="/resources/bootstrap_datetimepicker/js/bootstrap-datetimepicker.js"/>' charset="UTF-8"></script>
<script type="text/javascript" src='<c:url value="/resources/bootstrap_datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"/>' charset="UTF-8"></script>
<script type="text/javascript">

	

$('.form_datetime').datetimepicker({
  language:  'zh-CN',
  format: "yyyy-mm-dd",
  weekStart: 1,
  todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0
}); 
$('.form_date').datetimepicker({
	language:  'zh-CN',
	format: "yyyy-mm-dd hh:ii:ss",
    autoclose: true,
    todayBtn: true,
    todayHighlight: 1,
    pickerPosition: "bottom-left"
});
/* 	$('.form_time').datetimepicker({
        language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 1,
		forceParse: 0
    }); */
</script>
</html>