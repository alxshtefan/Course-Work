<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<html>

	<head>
		<meta charset="UTF-8">
		<title>Тест</title>
		<link rel="icon" href="images/logo.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/secondaryStyle.css">
		<style>
			body {
				text-align: center;
				align-content: center;
			}
			img {
				padding-top:2%;
			}
		</style>
		
		<script type="text/javascript">
			setTimeout(
			function () {
				if (${sessionScope.score} > 60) {
					document.location.replace("result.jsp");
				} else {
					document.location.replace("index.jsp");
				}
			}, 2000);
		</script>		
	</head>
	
	<body>
		<div style="color: #505050; font-size: 220%" >
			В данный момент идет обработка ваших ответов.<br>
			<form id="account" action="Controller" method="GET">
				<input type="hidden" value="openAccountPage" name="command">
					C результатами вы сможете ознакомится в своём
					<a href="#" onclick="document.getElementById('account').submit()"><u>аккаунте</u></a>.
			</form>
		</div>
		<div>
			<img width="40%" alt="work" src="images/work.gif">
		</div>
		<div class="errorBack">
			<a href="index.jsp"><u>Главная</u></a>
		</div>
	</body>

</html>