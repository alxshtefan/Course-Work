<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<html>

	<head>
		<title>Правильные ответы</title>
		<link rel="icon" href="images/logo.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/myStyle.css">
		<link rel="stylesheet" type="text/css" href="css/adminStyle.css">
	</head>
	
	<body>
	
		<c:if test="${sessionScope.user == null }">
			<script type="text/javascript">
				document.location.replace("welcome.jsp");
			</script>
		</c:if>
	
		<!-- ================================= HEADER =================================== -->
		<table class="header">
			<tr>
				<td width="5%">
					<img src="images/logo.png" width="100%">
				</td>
				<td class="logo">
					Тестирование
				</td>
			</tr>
		</table>
		<!-- ============================================================================ -->
		
		<!-- ================================== BODY ==================================== -->
		<div class="addQuestions">
			<form method="POST" action="Controller">
				<c:forEach items="${sessionScope.correctTest.getQuestions() }" var="question" varStatus="loop">
					<p>Вопрос №${loop.index + 1} <br><hr>
					<input class="inQuest" type="text" name="question${loop.index + 1 }" value="${question.getQuest() }" autocomplete="off" placeholder="Вопрос №${loop.index + 1}" disabled><hr>
					<c:forEach items="${question.getAnswers() }" var="answer" varStatus="loop2">
						<c:choose>
							<c:when test="${answer.isCorrect() }">
								<input name="ranswer${loop.index + 1}" value="${loop2.index + 1}" type="checkbox" checked="checked" disabled>
								<input class="inQuest" type="text" style="background: lightgreen"
								value="${answer.getAnswer() }" name="answer${loop.index + 1}text${loop2.index + 1}" disabled><br>
							</c:when>
							<c:otherwise>
								<input name="ranswer${loop.index + 1}" value="${loop2.index + 1}" type="checkbox" disabled>
								<input class="inQuest" type="text" value="${answer.getAnswer() }" name="answer${loop.index + 1}text${loop2.index + 1}" disabled><br>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:forEach>
				<br><hr>
				<input type="button" onclick="document.location.replace('index.jsp');" value="В главное меню">
			</form>
		</div>
		<!-- ============================================================================ -->
		
	</body>
	
</html>