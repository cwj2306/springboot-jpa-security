<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>로그인 페이지</h1>
	<form action="/user/loginProcess" method="post">
		<input type="text" name="username" /><br/>
		<input type="password" name="password" /><br/>
		<input type="submit" name="로그인" /><br/>
	</form>

</body>
</html>