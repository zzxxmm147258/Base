<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>404</title>
    <style>
        *{margin:0;padding:0;}
        body{background: #F1EEE9;}
        .cont_center{width:500px;height: 180px;position:absolute;left:50%;top:50%;margin-left: -250px;  margin-top: calc(-180px/2); margin-left: calc(500/2);}
        .face{float: left;width:167px;}
        .face_word{margin-top: 60px;float: left;}
        .face_word p{font:20px/45px '微软雅黑';}
    </style>
</head>
<body>
    <div class="cont_center">
        <div class="face">
            <img src="<c:url value='/resources/image/sysimg/jinz.png'/>" height="170" width="167" alt="">
        </div>
        <div class="face_word">
            <p>对不起，您要访问的页面不存在</p>
        </div>
    </div>
</body>
</html>