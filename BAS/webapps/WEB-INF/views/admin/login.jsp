<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" import="com.hibo.bas.util.DataConfig" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<% 
	request.setAttribute("title", DataConfig.getSysTitle()); 
	request.setAttribute("dbSelectMap", DataConfig.getWorkSpaceMap(0)); 
	request.setAttribute("sysSelectMap", DataConfig.getSysIdMap());
%>
<head>
	<TITLE>${title}</TITLE>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<c:url value='/resources/hibo/js/hibo.js'/>"></script>
<script type="text/javascript"	src="<c:url value='/resources/bootstrap/js/bootstrap.js'/>"></script>
    <link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>"  type="text/css">
    <title>Document</title>
    <style>
        .box{width: 890px;height: 574px;padding: 20px 55px;margin:30px auto;}
        .box_top{height: 60px;width: 100%;border-bottom: 1px solid #898989;}
        .box_top a{background: url(<c:url value='/resources/image/logo.fw.png'/>) left center no-repeat;display: inline-block;width:186px;height: 57px;margin-left: -5px;}
        .content_t{margin-top:8px;height: 446px;border-bottom:1px solid #898989;width: 100%;}
        .pic{width: 376px;height: 267px;margin-top: 95px;margin-left: 5px;margin-right: 46px;}
        .System{width: 446px;height: 394px;}
        .System h3{height: 70px;font: bold 33px/70px '微软雅黑';text-align: center;}
        .information{background: url(<c:url value='/resources/image/yin.png'/>) left center;width: 100%;height: 323px;}
        .information ul{padding:30px 65px;width: 340px;height:243px;}
        ul li{height: 30px;margin-bottom:16px;vertical-align: middle;}
        ul .left span{font:20px/28px '微软雅黑';color:#fff;}
        ul .right{position: relative;}
        ul img{vertical-align: middle;}
        .right strong{background: url(<c:url value='/resources/image/suo.png'/>) left center no-repeat;width: 18px;height:20px;position: absolute;left:5px;top:3px;display: block;}
        ul li input{background: #fff;width: 226px;height: 28px;vertical-align: middle;}
        .gt{padding-left: 4px;}
        .gt a{width: 92px;height: 28px;background: #fff;display: inline-block;text-align: center;margin-left: 16px;padding-top: 2px;font-family: "微软雅黑";}
        ul .botm{margin-bottom: 14px;}
        ul .botm input{width: 134px;height: 30px;}
         .suo strong{background: url(<c:url value='/resources/image/ys.png'/>) left center no-repeat;width: 12px;height: 26px;top:1px;display: block;}
        ul .btn{width: 242px;height: 34px;background: #fff;margin-left: 50px;box-shadow:0px 3px 15px #615555;}
        .btn a{background: url(<c:url value='/resources/image/system.png'/>) center center no-repeat;display: inline-block;width: 97px;height: 30px;margin-left: 0px;}
        .content_b p{font:16px/46px '';text-align: center;}

        .right select{width:226px;height: 28px;background: #fff;}
    </style>
    <script type="text/javascript" charset="UTF-8">
    window.onload = function(){
    	$.App.login(Envparam.loginToUri);
    	setTimeout(function(){
    		$('body').show();
    	}, 200);
        var oUl = document.getElementById('set');
        var oInput = document.getElementsByTagName('input');
        var oStrong = document.getElementsByTagName('strong');
        move( oInput[1] );
        move( oInput[2] );
            function move( obj){
                if( obj ==  oInput[1] ){
                        obj.onfocus = function(){
                                oStrong[0].style.display = 'none';
                            };
                        obj.onblur = function(){
                        	if( oInput[1].value ){
                                oStrong[0].style.display = 'none';
                            }else{
                                 oStrong[0].style.display = 'block';
                            }
                        };
                };

                  if( obj ==  oInput[2] ){
                        obj.onfocus = function(){
                                oStrong[1].style.display = 'none';
                            };
                            obj.onblur = function(){
                                if( oInput[2].value ){
                                       oStrong[1].style.display = 'none';
                                   }else{
                                        oStrong[1].style.display = 'block';
                                   }
                               
                           };

                }
            };

};
		var _captcha_id = "#img_captcha";
		function refreshCaptcha() {
			var url = "${pageContext.request.contextPath}/resources/captchaCode?t="+ Math.random();
			$(_captcha_id).attr("src",url);
		}
    </script>
</head>
<body style="display: none;">
<div class="box">
    <div class="box_top"><!-- 标题 -->
        <h3><a href=""></a></h3>
    </div>
    <div class="content"><!-- 下面内容 -->
        <div class="content_t"><!-- 中间内容 -->
            <div class="pic fl">
                    <a href=""><img src="<c:url value='/resources/image/img.png'/>"width="376" alt=""></a>
            </div>
            <div class="System fl">
                    <h3>${title}</h3>
                    <div class="information" style="position: relative;"><!-- 登陆信息 -->
            <c:if test="${not empty error}">
				<div class="" style="color:red;position: absolute;width:90%;font-size: 20px;margin: 0 5%;">${error }</div>
			</c:if>
                    <form method="post" action="" style="font-size: 0px">
                    	<input type="hidden" name="isToMain" value="true"/>
                        <ul id="set">
                            <li>
                                <div class="left fl"><span>登录账套：</span></div>
                                <div class="right fl">
                                    <select name="db" id="db">
                                    <c:forEach items="${dbSelectMap }" var="dbSelect">
                                    	<option value="${dbSelect.key }">${dbSelect.key }:${dbSelect.value }</option>
                                    </c:forEach>
			</select>
                                   </div>
                            </li> 
                             <li>
                                <div class="left fl"><span>登录系统：</span></div>
                                <div class="right fl">
                                    <select id="sys" name="sys">
										<c:forEach items="${sysSelectMap }" var="sysSelect">
                                    	<option value="${sysSelect.key }">${sysSelect.key }:${sysSelect.value }</option>
                                    </c:forEach>
                                    </select></div>
                            </li>
								<!-- <li><div class="left fl"><span>登录系统：</span></div><div class="right fl"><input type="text"><em></em></div></li> -->
								<li><div class="left fl">
										<span>用&nbsp;户&nbsp;名&nbsp;：</span>
									</div>
									<div class="right fl">
										<input type="text" name="username" >
									</div></li>
								<li><div class="left fl">
										<span>口&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;令：</span>
									</div>
									<div class="right fl suo">
										<input type="password" name="password">
									</div></li>
								<li class="botm"><div class="left fl">
										<span>验&nbsp;证&nbsp;码&nbsp;：</span>
									</div>
									<div class="right fl gt">
										<input type="text" name="captcha">
										<img title="点击更换" id="img_captcha" onclick="javascript:refreshCaptcha();" src="<c:url value='/resources/captchaCode'/>"/>
										<!-- (看不清<a href="javascript:void(0)" onclick="javascript:refreshCaptcha()">换一张</a>) -->
									</div></li>
								<button class="btn" type="submit">
									<a type="submit"></a>
								</button>
							</ul>
                        </form>
                    </div>
            </div>
        </div>
        <div class="content_b"><!-- 结束 -->
            <p>Copyright © 2015-2015 EP Inc, All Rights Reserved. 瀚铂科技 版权所有</p>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
$("#db").val("${loginInfo.work}");
$("#sys").val("${loginInfo.sysid}");
$("input[name=username]").val("${loginInfo.username}");
</script>
</html>