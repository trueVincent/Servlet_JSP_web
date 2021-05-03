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
        <a href='member'>回會員中心</a>
    </div>

	${sessionScope.login}的圖片庫
	<br>Title：${requestScope.message.title}  
	<br>Time：${requestScope.message.localDateTime}
	<br>Message：<textarea>${requestScope.message.message}</textarea><br>
       <c:forEach var="photo" items="${requestScope.photos}">
                    <img src='Photos/${photo.photoPath}.jpg' alt='${photo.photoPath}'/>
                    <form method='post' action='del_photo'>
                        <input type='hidden' name='millis' value='${photo.millis}'>
                        <input type='hidden' name='photoPath' value='${photo.photoPath}'>
                        <input type='hidden' name='title' value='${photo.title}'>
                        <button type='submit'>刪除</button>
                    </form>
                    <hr>     
        </c:forEach>
</body>
</html>