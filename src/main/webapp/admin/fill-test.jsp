<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<html>

	<head>
		<title>Заполнение тестов</title>
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
		<center><div class="addQuestions">
			<form action="../Controller" method="POST">
				<input type="hidden"name="command" value="addTest" /> 
				<c:forEach begin="1" end="${sessionScope.test.getqNumber()}" var="loop">
					<p>Вопрос №${loop } <br><hr>
					<input class="inQuest" type="text" name="question${loop }" autocomplete="off"  maxlength="1500" placeholder="Вопрос №${loop }" required></p><hr>
					<input name="ranswer${loop}" value="1" type="checkbox"><input class="inQuest" type="text" maxlength="1000" autocomplete="off" name="answer${loop}text1" required><br>
					<input name="ranswer${loop}" value="2" type="checkbox"><input class="inQuest" type="text" maxlength="1000" autocomplete="off" name="answer${loop}text2" required><br>
					<input name="ranswer${loop}" value="3" type="checkbox"><input class="inQuest" type="text" maxlength="1000" autocomplete="off" name="answer${loop}text3" required><br>
					<input name="ranswer${loop}" value="4" type="checkbox"><input class="inQuest" type="text" maxlength="1000" autocomplete="off" name="answer${loop}text4" required><br><br>
				</c:forEach>
				<br><hr>
				<input type="submit" value="Завершить создание теста">
			</form>
		</div></center>
		<!-- ============================================================================ -->
		
	</body>
	
</html>