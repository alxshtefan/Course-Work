<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<html>

	<head>
		<title>500</title>
		<link rel="icon" href="images/error.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/secondary.css">
		<style>
			body {
				text-align: center;
				align-content: center;
				background: #FAFAFA;
			}
			img {
				padding-top: 9%;
			}
		</style>
	</head>
	
	<body>
	
		<c:if test="${sessionScope.user.getLogin() != 'admin' }">
			<c:set var="test" value="null" scope="session"/>
		</c:if>
	
		<div>
			<img width="70%" alt="error" src="images/500.png">
		</div>
		<div class="errorBack">
			<a href="index.jsp">Back home</a>
		</div>
	</body>
	
</html>