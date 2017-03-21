<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<c:url value='/resources/css/richText.css'/>">
<script type="text/javascript" src="<c:url value='/resources/hibo/js/hibo.js'/>"></script>
<script src="<c:url value='/resources/js/commMore.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/resources/css/header.css'/> "/>
<title>${bean.title}</title>
</head>
<body>

	<!-- 只有内容 -->
	<c:if test="${bean.attr1 eq 'NO_CONCONT'}">
		<div class="comPlate comBlock">
		<div class="info base allComBox" style="margin-top: 0px;">
		<div class="posi">
			${bean.content}
		</div>
		</div>
		</div>
	</c:if>
	<!--有工具条只有内容  -->
	<c:if test="${bean.attr1 eq 'YES_CONCONT'}">
		<div class="comPlate comBlock">
		<header> <a href="javascript:history.go(-1);"><span
				class="back"></span></a>
				<h3 id="tit">${bean.title}</h3>
			</header>
			<div class="info base allComBox">
			<div class="posi">
			${bean.content}
			</div>
			
			</div>
		</div>
	</c:if>
	<!-- 有工具条有标题（bean进来的内容) -->
	<c:if test="${bean.attr1 eq 'YES_TITCON'}">
		<div class="comPlate comBlock">
			<header> <a href="javascript:history.go(-1);"><span
				class="back"></span></a>
			<h3 id="tit">${bean.title}</h3>
			</header>
			<div class="info base allComBox">
				<div class="posi">
					<div class="topmain" style="width: 96%; padding: 0 2% 0 2%;margin-top: 0.3rem;">
						<h3 style='text-align: center; font-size: 0.32rem'>${bean.title }</h3>
						<div class="time" style='text-align: center; font-size: 0.32rem'>
							时间:
							<fmt:formatDate value="${bean.effectiveDate }"
								pattern="yyyy-MM-dd " />
							编辑:${bean.author }
						</div>
						<hr style="margin: 10px 0 20px 0">
						<div class="content">
								${bean.content}
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>

		</div>
	</c:if>
	<!-- 无工具条有标题（bean进来的内容) -->
	<c:if test="${bean.attr1 eq 'NO_TITCON'}">
		<div class="comPlate comBlock">
			<div class="info base allComBox" style="margin-top: 0px;">
				<div class="left ">
					<div class="topmain" style="width: 96%; padding: 0 2% 0 2%;margin-top: 0.3rem;">
						<h3 style='text-align: center; font-size: 0.32rem'>${bean.title }</h3>
						<div class="time" style='text-align: center; font-size: 0.32rem'>
							时间:
							<fmt:formatDate value="${bean.effectiveDate }"
								pattern="yyyy-MM-dd " />
							编辑:${bean.author }
						</div>
						<hr style="margin: 10px 0 20px 0">
						<div class="content">
								${bean.content}
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>

		</div>
	</c:if>
	<!-- 有工具条+iframe(可以是文本网页，图片网页，和720°看房的链接地址) -->
	<c:if test="${bean.attr1 eq 'YES_IFM'}">
		<div class="comPlate comBlock">
			<header> <a href="javascript:history.go(-1);"><span
				class="back"></span></a>
			<h3 id="tit">${bean.title}</h3>
			</header>
			<div class="allComBox">
			<div class="posi">
				<iframe class="iframe" width="100%" height="100%" style="border: none;"> </iframe>
			</div>
			</div>
		</div>
	</c:if>
	<!-- 无工具条+iframe(可以是文本网页，图片网页，和720°看房的链接地址) -->
	<c:if test="${bean.attr1 eq 'NO_IFM'}">
		<div class="comPlate comBlock">
			<div class="allComBox" style="margin-top: 0px;>
			<div class="posi">
				<iframe class="iframe" width="100%"
					height="100%" style="overflow: scroll; border: none;"> </iframe>
			</div>
			</div>
		</div>
	</c:if>
	<!-- 有工具条+图片 -->
	<c:if test="${bean.attr1 eq 'YES_CONIMG'}">
		<div class="comPlate comBlock">
			<header> <a href="javascript:history.go(-1);"><span
				class="back"></span></a>
				<h3 id="tit">${bean.title}</h3>
			</header>
			<div class="allComBox"> 
				<div class="posi">
					${bean.content}
				</div>
			</div>
		</div>
	</c:if>
	<!-- 无工具条+图片 -->
	<c:if test="${bean.attr1 eq 'NO_CONIMG'}">
		<div class="comPlate comBlock">
			<div class="allComBox" style="margin-top: 0px;">
				<div class="posi">
					${bean.content}
				</div>
	</div>
	</c:if>
	
	
	
	<!-- 有工具条+iframe+一个按钮 -->
	<c:if test="${bean.attr1 eq 'YES_IFM1BTN'}">
	<div class="comPlate comBlock">
		<header> <a href="javascript:history.go(-1);"><span
				class="back"></span></a>
			<h3 id="tit">${bean.title}</h3>
		</header>
		<div class="allComBox">
			<div class="posi">
				<!-- 按钮的内容需要根据点击哪个页面传进来（注意：此按钮会根据进入的页面而改变样式：btnOne（购房有奖），btnTwo（惠民包）、btnThree（帮你麦房）） -->
				<span class="btnOne" onclick="btnClick('${bean.btnUrl01}')">${bean.btnName01}</span>
				<!-- 此连接进来的内容会被按钮覆盖 -->
				<iframe class="iframe" width="100%" height="100%"
					style="overflow: scroll; border: none;"> </iframe>
			<div style="display: none;">
					${bean.content}
			</div>
			</div>
			<script type="text/javascript">
				var src = $("img").attr('src');
				if(src){
					$('.posi').css({'background':'url('+src+') no-repeat center center','background-size':'cover'})
				}
			</script>
		</div>
	</div>
	</c:if>
	<!-- 无工具条+iframe+一个按钮 -->
	<c:if test="${bean.attr1 eq 'NO_IFM1BTN'}">
	<div class="comPlate comBlock">
		<div class="allComBox" style="margin-top: 0px;">
			<div class="posi">
				<!-- 按钮的内容需要根据点击哪个页面传进来（注意：此按钮会根据进入的页面而改变样式：btnOne（购房有奖），btnTwo（惠民包）、btnThree（帮你麦房）） -->
				<span class="btnOne" onclick="btnClick('${bean.btnUrl01}')">${bean.btnName01}</span>
				<!-- 此连接进来的内容会被按钮覆盖 -->
				<iframe class="iframe" width="100%" height="100%"
					style="overflow: scroll; border: none;"> </iframe>
			<div style="display: none;">
					${bean.content}
			</div>
			</div>
			<script type="text/javascript">
				var src = $("img").attr('src');
				if(src){
					$('.posi').css({'background':'url('+src+') no-repeat center center','background-size':'cover'})
				}
			</script>
		</div>
	</div>
	</c:if>
	<!-- 有工具条+iframe+二个按钮（全山美少年跳转板块） -->
	<c:if test="${bean.attr1 eq 'YES_IFM2BTN'}">
	<div class="comPlate comBlock">
		<header> <a href="javascript:history.go(-1);"><span
				class="back"></span></a>
			<h3 id="tit">${bean.title}</h3>
		</header>
		<div class="allComBox">
			<div class="posi">
				<!-- 按钮的内容需要根据点击哪个页面传进来（注意：此按钮会根据进入的页面而改变样式：btnOne（购房有奖），btnTwo（惠民包）、btnThree（帮你麦房）） -->
				<!-- <span class="btnThree"></span> <span class="btnThree" style="bottom: 0.55rem;"></span> -->
				<span class="btnOne" onclick="btnClick('${bean.btnUrl01}')">${bean.btnName01}</span>
				<span class="btnOne"style="bottom: 0.55rem;" onclick="btnClick('${bean.btnUrl02}')">${bean.btnName02}</span>
				<iframe class="iframe" width="100%" height="100%"
					style="overflow: scroll; border: none;"> </iframe>
			<div style="display: none;">
					${bean.content}
				</div>
			</div>
			<script type="text/javascript">
				var src = $("img").attr('src');
				if(src){
					$('.posi').css({'background':'url('+src+') no-repeat center center','background-size':'cover'})
				}
			</script>
		</div>
	</div>
	</c:if>
	<!-- 无工具条+iframe+二个按钮（全山美少年跳转板块） -->
	<c:if test="${bean.attr1 eq 'NO_IFM2BTN'}">
	<div class="comPlate comBlock">
		<div class="allComBox" style="margin-top: 0px;">
			<div class="posi">
				<!-- 按钮的内容需要根据点击哪个页面传进来（注意：此按钮会根据进入的页面而改变样式：btnOne（购房有奖），btnTwo（惠民包）、btnThree（帮你麦房）） -->
				<!-- <span class="btnThree"></span> <span class="btnThree" style="bottom: 0.55rem;"></span> -->
				<span class="btnOne" onclick="btnClick('${bean.btnUrl01}')">${bean.btnName01}</span>
				<span class="btnOne"style="bottom: 0.55rem;" onclick="btnClick('${bean.btnUrl02}')">${bean.btnName02}</span>
				<iframe class="iframe" width="100%" height="100%" style="overflow: scroll; border: none;"> </iframe>
			<div style="display: none;">
					${bean.content}
				</div>
			</div>
			<script type="text/javascript">
				var src = $("img").attr('src');
				if(src){
					$('.posi').css({'background':'url('+src+') no-repeat center center','background-size':'cover'})
				}
			</script>
		</div>
	</div>
	</c:if>
	
	<!-- 有工具条+图+一个钮 -->
	<c:if test="${bean.attr1 eq 'YES_IMG1BTN'}">
	<div class="comPlate comBlock">
		<header> <a href="javascript:history.go(-1);"><span
				class="back"></span></a>
			<h3 id="tit">${bean.title}</h3>
		</header>
		<div class="allComBox">
			<div class="posi">
				<!-- 按钮的内容需要根据点击哪个页面传进来（注意：此按钮会根据进入的页面而改变样式：btnOne（购房有奖），btnTwo（惠民包）、btnThree（帮你麦房）） -->
				<span class="btnOne" onclick="btnClick('${bean.btnUrl01}')">${bean.btnName01}</span>
				${bean.content}
			</div>
		</div>
	</div>
	</c:if>
	<!-- 无工具条+图+一个钮 -->
	<c:if test="${bean.attr1 eq 'NO_IMG1BTN'}">
	<div class="comPlate comBlock">
		<div class="allComBox" style="margin-top: 0px;">
			<div class="posi">
				<!-- 按钮的内容需要根据点击哪个页面传进来（注意：此按钮会根据进入的页面而改变样式：btnOne（购房有奖），btnTwo（惠民包）、btnThree（帮你麦房）） -->
				<span class="btnOne" onclick="btnClick('${bean.btnUrl01}')">${bean.btnName01}</span>
				${bean.content}
			</div>
		</div>
	</div>
	</c:if>
	<!-- 有工具条+图+二个钮 -->
	<c:if test="${bean.attr1 eq 'YES_IMG2BTN'}">
	<div class="comPlate comBlock">
		<header> <a href="javascript:history.go(-1);"><span
				class="back"></span></a>
			<h3 id="tit">${bean.title}</h3>
		</header>
		<div class="allComBox">
			<div class="posi">
				<!-- 按钮的内容需要根据点击哪个页面传进来（注意：此按钮会根据进入的页面而改变样式：btnOne（购房有奖），btnTwo（惠民包）、btnThree（帮你麦房）） -->
				<span class="btnOne" onclick="btnClick('${bean.btnUrl01}')">${bean.btnName01}</span>
				<span class="btnOne"style="bottom: 0.55rem;" onclick="btnClick('${bean.btnUrl02}')">${bean.btnName02}</span>
				${bean.content}
		</div>
	</div>
	</div>
	</c:if>
	<!-- 无工具条+图+二个钮 -->
	<c:if test="${bean.attr1 eq 'NO_IMG2BTN'}">
	<div class="comPlate comBlock">
		<div class="allComBox" style="margin-top: 0px;">
			<div class="posi">
				<!-- 按钮的内容需要根据点击哪个页面传进来（注意：此按钮会根据进入的页面而改变样式：btnOne（购房有奖），btnTwo（惠民包）、btnThree（帮你麦房）） -->
				<span class="btnOne" onclick="btnClick('${bean.btnUrl01}')">${bean.btnName01}</span>
				<span class="btnOne"style="bottom: 0.55rem;" onclick="btnClick('${bean.btnUrl02}')">${bean.btnName02}</span>
				${bean.content}
		</div>
	</div>
	</div>
	</c:if>

<script type="text/javascript">
var titleStr = $.GetUrlParam('title');
if(titleStr){
	$('#tit').text(titleStr);
	$('title').text(titleStr);
}
(function (doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            getThisBox();
        };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

var attr1 = '${bean.attr1}';
var intp = attr1.indexOf('IFM')
var href='${bean.contentUrl}';
var pi = href.substring(0,4);
if(!('http'==pi||'www.'==pi)){
	href=$.Url(href);
}
href=href.indexOf('?')>0?(href+'&isHead=${isHead}'):(href+'?isHead=${isHead}');
if(intp>0){
	$('.iframe').attr('src',href);
}else{
	if(${bean.isUrl}){
		window.location.href=href;
	}
}
function btnClick(url){
	var pi = url.substring(0,5);
	if(!('http:'==pi||'www.'==pi)){
		url=$.Url(url);
	}
	url=url.indexOf('?')>0?(url+'&isHead=${isHead}'):(url+'?isHead=${isHead}');
	window.location.href=url;
}

/* 获取当前显示的下面的allComBox */
function getThisBox(){
	$('.allComBox').each(function(){
			var w = $(this).innerWidth();
			var h = $(this).innerHeight();
			var ifr = $('.iframe')[0];
			if(ifr){
				$(ifr).css({'width':w+'px','height':h+'px'});
			}
			var img = $("img");
			img.removeAttr("style");
			img.attr('align','left');
			img.css('width','100%');
	});
}

</script>
</body>
</html>