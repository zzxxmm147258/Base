<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form method="post" modelAttribute="readexceldefg">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
            <h4 class="modal-title" id="myModalLabel">
               	Execl导入配置表信息
            </h4>
         </div>
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
    			<div class="col-md-6">
    				<label>字段名</label><form:errors path="fldname" style="color:red;"/>
					<form:input path="fldname" cssClass="form-control"/>
    			</div>
    			<div class="col-md-6">
    				<label>字段显示名</label>
					<form:input path="flddisplayname" cssClass="form-control"/>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-6">
    				<label>该字段对应Excel列号</label>
					<form:input path="excelcol" cssClass="form-control" />
    			</div>
    			<div class="col-md-6">
    				<label>字段类型</label>
					<form:input path="fldtype" cssClass="form-control"/>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-6">
    				<label>大小</label>
					<form:input path="fldsize" cssClass="form-control" />
    			</div>
    			<div class="col-md-6">
    				<label>小数</label>
					<form:input path="flddecimal" cssClass="form-control" />
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-6">
    				<label>缺省值</label>
					<form:input path="flddefault" cssClass="form-control" />
    			</div>
    			<div class="col-md-6">
    				<label>分组描述</label>
					<form:input path="groupdesc" cssClass="form-control" />
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-6">
    				<label>选项</label>
					<form:input path="flags" cssClass="form-control" />
    			</div>
    			<div class="col-md-6">
    				<label>排序号</label>
					<form:input path="idx" cssClass="form-control" />
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-6">
    				<label>groovy</label>
					<form:input path="groovy" cssClass="form-control" />
    			</div>
    		</div>
    		
			<input type="submit" class="btn btn-primary" value="提交" /> <a
				href="<c:url value='/admin/readexceldef/list'/>" class="btn btn-success">返回</a>
		</div>   
		</div>  
		</div>
		</div>   
   </form:form>
</div>
</body>
<script type="text/javascript">
$('#myModal').modal('show');
</script>
</html>