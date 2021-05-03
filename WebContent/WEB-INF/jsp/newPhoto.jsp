<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>圖片管理</title>
<link rel='stylesheet' href='css/member.css' type='text/css'>
</head>
<body>
    <div class='rightPanel'>
        <a href='logout'>登出 ${sessionScope.login}</a>
        <br><a href='member'>回上一頁</a>
    </div>

    <c:if test = "${requestScope.errors != null}">
    	<ul style = 'color: rgb(255, 0, 0);'>
    	<c:forEach var='error' items='${requestScope.errors}'>
    		<li>${error}</li>
    	</c:forEach>
    	</ul>
    </c:if>
    
	<h1>新增留言與圖片</h1><br>
     <form method='post' action='new_photo' enctype='multipart/form-data'>
        Title：<input type='text' name='title'><br>
        Message：<textarea id='message' name='message' cols='50' rows='6'></textarea><br>
        <input type='file' name='photos' multiple='multiple' accept='.jpg'><br>
        <button type='submit'>送出</button>
    </form>
</body>
</html>