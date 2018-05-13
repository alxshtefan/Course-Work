<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ru'}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources"/>

<html>

	<head>
		<title><fmt:message key="error.title"/></title>
		<link rel="icon" href="../images/error.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="../css/secondaryStyle.css">
		<style type="text/css">
			body {
				text-align: center;
			}
		</style>
	</head>
	
	<body>
		<div class="error">
			${error }
		</div>
		<div>
			<img alt="error" src="../images/error.png">
		</div>
		<div class="errorBack">
			<c:if test="${sessionScope.user.getLogin() == 'admin' }">
				<a href="../admin.jsp">Back home</a>
			</c:if>
			<c:if test="${sessionScope.user.getLogin() != 'admin' }">
				<a href="../welcome.jsp">Back home</a>
			</c:if>
		</div>
	</body>
	
</html>