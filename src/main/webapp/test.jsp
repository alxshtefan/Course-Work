<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ru'}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources"/>  

<html>
	
	<head>
		<title><fmt:message key="header.label"/></title>
		<link rel="icon" href="images/logo.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/myStyle.css">
		<style type="text/css">
			.timer {
				position: absolute;
				top: 9.7%;
				left: 90%;
				color: #505050;
				font-size: 150%;
			}
		</style>
		<script type="text/javascript" src="js/timerScript.js"></script>		
		<script type="text/javascript">
			window.onload = startTimer(${test.getTime()});
		</script>
		
	</head>
	
	<body>
		<c:if test="${sessionScope.user == null }">
			<script type="text/javascript">
				document.location.replace("welcome.jsp")
			</script>
		</c:if>
		<!-- ================================= HEADER =================================== -->
		<table class="header">
			<tr>
				<td width="5%">
					<img src="images/logo.png" width="100%">
				</td>
				<td class="logo">
					<fmt:message key="header.label" />
				</td>
			</tr>
		</table>
		<!-- ============================================================================ -->	

		<!-- ================================== BODY ==================================== -->
		<p id="demo" class="timer"></p>
		<div style="text-align: center; font-size: 120%;">
			<p>Название теста: ${test.getTitle() }</p>
			<p>Тема теста: ${test.getSubject() }</p>	
			<form id="submitTest" action="Controller" method="POST" style="text-align: left; width: 30%; padding-left: 30%">
				<input type="hidden" value="endTest" name="command">
				<c:forEach items="${test.getQuestions() }" var="question" varStatus="qloop">
					<div style="width: 600px">${qloop.index + 1 }) ${question.getQuest() }</div><br>
					<c:forEach begin="1" end="${question.getAnswers().size() }" var="answer" >
						<input  type="checkbox" name="ranswer${qloop.index + 1}" value="${answer }"> ${question.getAnswers().get(answer-1).getAnswer() }<br>
					</c:forEach>
					<br><br>
				</c:forEach>
				
				
				<input type="hidden" value="${test.getTitle() }" name="testName">
				<input style="font-size: 100%" type="submit" value="Завершить тест">
			</form>
			<br>
		</div>
		<!-- ============================================================================ -->	
	
	</body>
		
</html>