<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<html>

	<head>
		<title>Редактирование теста</title>
		<link rel="icon" href="../images/logo.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="../css/myStyle.css">
		<link rel="stylesheet" type="text/css" href="../css/adminStyle.css">
	</head>
	
	<body>
	
		<c:if test="${sessionScope.user == null }">
			<script type="text/javascript">
				document.location.replace("../welcome.jsp");
			</script>
		</c:if>
		<c:if test="${sessionScope.user.getLogin() != 'admin' }">
			<script type="text/javascript">
				document.location.replace("../welcome.jsp");
			</script>
		</c:if>
	
		<!-- ================================= HEADER =================================== -->
		<table class="header">
			<tr>
				<td width="5%">
					<img src="../images/logo.png" width="100%">
				</td>
				<td class="logo">
					Тестирование
				</td>
			</tr>
		</table>
		<!-- ============================================================================ -->
		
		<!-- ================================== BODY ==================================== -->
		<div class="addQuestions">
			<form action="../Controller" method="POST">
				<input type="hidden" name="command" value="updateTest" /> 
				<p>Сложность теста: <input type="text" name="difficult" placeholder="2" pattern="[1-5]" title="from 1 up to 5" value="${sessionScope.test.getDifficult()}" autocomplete="off" required></p>
			  	<p>Время на прохождение теста: <input type="text" name="time" placeholder="180" pattern="[0-9]+" title="in seconds" value="${sessionScope.test.getTime()}" autocomplete="off" required></p>
				<c:forEach items="${sessionScope.test.getQuestions() }" var="question" varStatus="loop">
					<p>Вопрос №${loop.index + 1} <br><hr>
					<input class="inQuest" type="text" name="question${loop.index + 1 }" value="${question.getQuest() }" maxlength="150" autocomplete="off" placeholder="Вопрос №${loop.index + 1}"><hr>
					<c:forEach items="${question.getAnswers() }" var="answer" varStatus="loop2">
						<c:choose>
							<c:when test="${answer.isCorrect() }">
								<input name="ranswer${loop.index + 1}" value="${loop2.index + 1}" type="checkbox" checked="checked"><input class="inQuest" type="text" autocomplete="off" maxlength="75" value="${answer.getAnswer() }" name="answer${loop.index + 1}text${loop2.index + 1}"><br>
							</c:when>
							<c:otherwise>
								<input name="ranswer${loop.index + 1}" value="${loop2.index + 1}" type="checkbox"><input class="inQuest" type="text" autocomplete="off" maxlength="75" value="${answer.getAnswer() }" name="answer${loop.index + 1}text${loop2.index + 1}"><br>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:forEach>
				<br><hr>
				<input type="submit" value="Обновить содержимое теста">
			</form>
		</div>
		<!-- ============================================================================ -->
		
	</body>
	
</html>