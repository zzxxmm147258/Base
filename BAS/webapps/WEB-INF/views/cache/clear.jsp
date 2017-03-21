<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
<div class="container" style="margin-top: 5%;">
		<form class="form-horizontal" method="post" action='<c:url value="/admin/cache/clear"/>'>		
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>name:</label>
				</div>
				<div class="col-md-4">
					<input class="form-control" name="name" value="${name}">
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>key:</label>
				</div>
				<div class="col-md-4">
					<input class="form-control" name="key" value="${key}">
				</div>
			</div>
			<div class="form-group">
				<div style="text-align: right;" class="col-md-4">
					<label>清除系统选项缓存</label>
				</div>
				<div class="col-md-1">
					<input type="checkbox" name="isConfig">
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-4">
					<div style="padding:1px" class="col-md-5">
						<input class="btn btn-primary col-md-12" type="submit" value="提交">
					</div><div class="col-md-2"></div>
					<div style="padding:1px" class="col-md-5">
						<a class="btn btn-default col-md-12" id="close" >取消</a>
					</div>
				</div>
			</div>
		</form>
	</div>
	
	<!-- 模态框（Modal） -->
<div class="modal fade" id="msgModal" tabindex="-1" role="dialog" 
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
            ${msg}
         </div>
         <div class="modal-footer">
           <!--  <button type="button" class="btn btn-default" 
               data-dismiss="modal">关闭
            </button -->
            <button type="button" class="btn btn-primary" data-dismiss="modal">
               	确定
            </button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
<script type="text/javascript">
	var msg = "${msg}";
	if(""!=msg){
		$('#msgModal').modal('show')
	}
	
	$('#close').click(function(){
		$.MenuUtil.close();
	});
</script>
</html>