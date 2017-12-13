<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>404 Error</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
</head>
<style type="text/css">
	a { 
		text-decoration: none
	}
	.backup{
		font-size: 16px;
		border-radius:35px;
		border: 1px solid #eee; 
		background-color: #E9BF00; 
		margin-left: 80px;
		width: 172px;
		height: 44px;
		color: #ffffff;
		line-height: 46px;
   		text-align: center;
	}
	.backup:hover{
       box-shadow:
       12px 0 12px #fbf2cc, /*右边阴影*/  
       0 10px 10px #fbf2cc; /*底边阴影*/  
	}
</style>
<body>
	<div class="item">
		<img src="/resource/error/404.jpg" alt="404页面" />
		<div class="input-button" style="position:relative; top:-256px; left:517px;">
			<a href="/index.html"><div class="backup">返回首页 <img src="/resource/error/jt.png" alt="返回首页" /></div></a>
		</div>
	</div>
<script>
		setTimeout(function() {
			location.href = "/index.html";
		}, 3000);
</script>		
</body>			
</html>