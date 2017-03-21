<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<body>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form method="post" modelAttribute="readexceldef">
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
    				<label>编码</label><form:errors path="rxcode" style="color:red;"/>
					<form:input path="rxcode" cssClass="form-control"/>
    			</div>
    			<div class="col-md-6">
    				<label>名字</label>
					<form:input path="rxname" cssClass="form-control"/>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-6">
    				<label>表名</label>
					<form:input path="tblname" cssClass="form-control" />
    			</div>
    			<div class="col-md-6">
    				<label>数据模型</label>
					<form:input path="model" cssClass="form-control"/>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-6">
    				<label>读入Excel表单名</label>
					<form:input path="excelsheetname" cssClass="form-control" />
    			</div>
    			<div class="col-md-6">
    				<label>Excel起始读入行</label>
					<form:input path="excelfromrow" cssClass="form-control" />
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-6">
    				<label>Excel每行循环执行次数</label>
					<form:input path="fortimes" cssClass="form-control" />
    			</div>
    			<div class="col-md-6">
    				<label>groovy</label>
					<form:input path="groovy" cssClass="form-control" />
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-6">
    				<label>选项</label>
					<form:input path="flags" cssClass="form-control" />
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