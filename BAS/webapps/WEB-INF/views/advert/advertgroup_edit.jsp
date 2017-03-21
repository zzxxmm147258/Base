<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/common/head.jsp"%>
<script type="text/javascript" src="<c:url value='/resources/js/choseModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/My97DatePicker/WdatePicker.js'/>"></script>
<title>广告分组编辑</title>
<body>
    <%-- <c:if test="${not empty addSuccess }">
		<h3 style="text-align:center;color:red">添加成功！现在可以继续添加。</h3>
	</c:if> --%>
<div class="modal" id="myModal" role="dialog" aria-labelledby="myModalLabel">
	<form:form id="Form" modelAttribute="bean">
       	<div class="modal-dialog modal-lg">
      	<div class="modal-content">
      	<div class="modal-header">
         
            <h4 class="modal-title" id="myModalLabel">
                                   广告分组编辑
            </h4>
            <c:if test="${not empty addSuccess }">
		     <h3 style="text-align:center;color:red">添加成功！现在可以继续添加。</h3>
	       </c:if>
         </div>
         
        <div class="modal-body" >
        <div class="panel-body">
			<div class="row">
    			<div class="col-md-4">
    				<label>位置</label><form:errors path="adPosition" style="color:red;"/>
    				<form:select id="adPosition" path="adPosition" cssClass="form-control">
    					<c:forEach items="${adPositions }" var="position">
    						<form:option value="${position.code }">${position.cname }</form:option>
    					</c:forEach>
    				</form:select>
    			</div>
    			<div class="col-md-4">
    				<label>分组名称</label><form:errors path="name" style="color:red;"/>
					 <form:input path="name" cssClass="form-control" /> 
    			</div>
    			<div class="col-md-4">
    				<label>开始时间</label><form:errors path="begin" style="color:red;"/>
					  <input name="begin"  type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${bean.begin}"  readonly="true"></input>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-4">
    				<label>结束时间</label><form:errors path="end" style="color:red;"/>
					  <input name="end"  type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${bean.end}"  readonly="true"></input>
    			</div>
    			<div class="col-md-4">
    				<label>是否启用</label><form:errors path="name" style="color:red;"/>
					 <form:select path="available" cssClass="form-control">
					 	<form:option value="1">是</form:option>
					 	<form:option value="0">否</form:option>
					 </form:select> 
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-12">
    				<label>备注</label><form:errors path="remark" style="color:red;"/>
					 <form:input path="remark" cssClass="form-control" /> 
    			</div>
    		</div>
    		<form:input type="hidden" id="adPositionName" path="adPositionName" cssClass="form-control" /> 
				<!-- <input type="submit" class="btn btn-primary" name="submit" id="submit" onclick="return Info()" value="提交返回" /> -->
				<input class="btn btn-primary" onclick="toSubmit()" value="提交" />
				 <a href="<c:url value='/main/advertgroup/list?parentId=${bean.id }'/>" class="btn btn-success">返回</a>
		</div>   
		</div>  
		</div>
		</div>   
   </form:form>
</div>

		
</body>
<script type="text/javascript">

$('#myModal').modal('show');

function toSubmit(){
	var position = $("#adPosition option:selected").text();
	$("#adPositionName").val(position);
	$("#Form").submit();
}

</script>
</html>