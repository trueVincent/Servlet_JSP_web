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
        <br>目前線上人數：${requestScope.counter}
    </div>

	${sessionScope.login}的圖片庫
	<br><a href="new_photo">新增圖片</a>
    <table border='0' cellpadding='2' cellspacing='2'>
        <thead>
            <tr>
                <th><hr></th>
            </tr>
        </thead>
        <tbody>

		<c:forEach var="message" items="${requestScope.messages}">
            <tr>
                <td style='horizontal-align: top;'>
                    Title：${message.title}<br>
                    Date：${message.localDateTime}<br>
                    <form method='post' action='showPhoto'>
                        <input type='hidden' name='millis' value='${message.millis}'>
                        <input type='hidden' name='title' value='${message.title}'>
                        <button type='submit'>查詢</button>
                    </form>
                    <form method='post' action='del_message'>
                        <input type='hidden' name='millis' value='${message.millis}'>
                        <input type='hidden' name='title' value='${message.title}'>
                        <button type='submit'>刪除</button>
                    </form>
                    <hr>
                </td>
            </tr>            
        </c:forEach>
        </tbody>
    </table>
</body>
</html>