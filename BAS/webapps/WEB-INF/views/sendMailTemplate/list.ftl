<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${name}</title>
<link href="/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="/resources/admin/js/common.js"></script>
<script type="text/javascript" src="/resources/admin/js/list.js"></script>
<script type="text/javascript">
	function support(){
	}
</script>
</head>
<body>
	<!-- JiaThis Button BEGIN -->
	<div class="jiathis_style_32x32">
		<a class="jiathis_button_qzone"></a>
		<a class="jiathis_button_tsina"></a>
		<a class="jiathis_button_tqq"></a>
		<a class="jiathis_button_weixin"></a>
		<a class="jiathis_button_renren"></a>
		<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
	</div>
	<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
	<!-- JiaThis Button END -->
	${detail}
	
	<a id="support" href="javascript:void(0);" onclick="support()">点赞</a>    <a id="oppose" href="javascript:void(0);" onclick="oppose()">反对</a>
	<form action="${url}insertComment" id="commentForm" method="post">
		<input type="hidden" name="id" value="${id}"/>
		<textarea rows="3" cols="20" name="comment"></textarea>
		<input type="submit" value="提交"/>
	</form>
</body>
</html>