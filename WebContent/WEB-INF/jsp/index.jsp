<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
        <title>圖片管理</title>
        <link rel="stylesheet" href="css/index.css" type="text/css">
	</head>
	<body>
		<div id="login">
			<c:if test="${requestScope.errors != null}">
            	<ul style='color: rgb(255, 0, 0);'>
            	<c:forEach var="error" items="${requestScope.errors}">
                	<li>${error}</li>
            	</c:forEach>
            	</ul>
        	</c:if>
        	
			<form method='post' action='login'>
				<table>
					<tr>
						<td colspan='2'>會員登入</td>
					<tr>
						<td>名稱：</td>
						<td><input type='text' name='username'></td>
					</tr>
					<tr>
						<td>密碼：</td>
						<td><input type='password' name='password'></td>
					</tr>
					<tr>
						<td colspan='2' align='center'><input type='submit' value='登入'></td>
					</tr>
					<tr>
						<td colspan='2'><a href='forgot.html'>忘記密碼？(重設密碼)</a></td>
					</tr>
					<tr>
						<td colspan='2'><a href='register'>還不是會員？</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div>
            <h1>最新圖片</h1>
            歡迎上傳圖片至圖片管理系統~
		    <table style='background-color:#ffffff;'> 
		        <thead>
		            <tr>
		                <th><hr></th>
		            </tr>
		        </thead>
		        <tbody>            
                <c:forEach var="photo" items="${requestScope.photos}">
		            <tr>
		                <td style='vertical-align: top;'>${photo.username}<br>
		                    <img src='Photos/${photo.photoPath}.jpg' alt='${photo.photoPath}' width='100' height='100'/>
		                    <br> ${photo.localDateTime}
		                    <hr>
		                </td>
		            </tr>        
		        </c:forEach>
		        </tbody>
             </table>
        </div>
	</body>
</html>